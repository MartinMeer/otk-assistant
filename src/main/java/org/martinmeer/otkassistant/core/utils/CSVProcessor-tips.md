### Important gotchas and best practices ###

1. Regex vs literal:
- Use String.replace for literal sequences (no regex overhead or escaping issues).
- Use Pattern/Matcher for regex; wrap replacement with Matcher.quoteReplacement if it can contain $ or .
2. Performance:
- BufferedReader/Writer with a reasonable buffer (e.g., 64 KiB).
- Precompile Pattern(s) once if using regex.
- Avoid Files.lines for simple transformations unless you need stream APIs; BufferedReader is faster and more explicit.
3. Line separators:
- readLine strips separators; decide what to write. Use writer.newLine() for platform-specific line endings. If you must preserve exact line endings, you need a different approach (read raw and detect).
4. CSV semantics:
- If CSV can contain quoted fields with embedded newlines or commas and you need CSV-aware replacement (e.g., only certain columns), use a CSV library (Apache Commons CSV or OpenCSV) rather than raw line processing.
5. Safety/OWASP:
- Avoid following symlinks if paths are untrusted.
- Do not log file contents or paths verbosely if sensitive.
- Handle permissions explicitly if needed and avoid executing filenames from user input.

### Common tips ###
1. Literal or simple regex replacements within a single physical line.
UTF-8.
2. Overwrite destination safely.
3. CSV rows don’t span multiple physical lines (or you’re okay with that limitation).
4. We’ll proceed with a streaming, line-by-line solution that’s safe, efficient, and simple.

### A better step-by-step plan ###

**Validate inputs:**
1. Source exists and is a regular file, readable.
2. Destination directory exists and is writable.
3. Prevent “in-place” write into the same path without a temp+move flow.
4. Choose charset explicitly (UTF-8).

- Create a temp file in the same directory as the destination (crucial for atomic move).

**Stream process:**

1. Open BufferedReader for the source.
2. Open BufferedWriter for the temp.
3. For each line: apply replacement(s) and write the transformed line, followed by a newline via writer.newLine().
4. Close streams automatically using try-with-resources.

Atomically move the temp file to the final destination with Files.move(..., ATOMIC_MOVE, REPLACE_EXISTING). If ATOMIC_MOVE isn’t supported, fall back to a non-atomic move but still in a single rename step.

**Cleanup:**

1. On any failure, attempt to delete the temp file.

### Important gotchas and best practices

- Regex vs literal:
Use String.replace for literal sequences (no regex overhead or escaping issues).
Use Pattern/Matcher for regex; wrap replacement with Matcher.quoteReplacement if it can contain $ or .
- Performance:
BufferedReader/Writer with a reasonable buffer (e.g., 64 KiB).
Precompile Pattern(s) once if using regex.
Avoid Files.lines for simple transformations unless you need stream APIs; BufferedReader is faster and more explicit.
- Line separators:
readLine strips separators; decide what to write. Use writer.newLine() for platform-specific line endings. If you must preserve exact line endings, you need a different approach (read raw and detect).
- CSV semantics:
If CSV can contain quoted fields with embedded newlines or commas and you need CSV-aware replacement (e.g., only certain columns), use a CSV library (Apache Commons CSV or OpenCSV) rather than raw line processing.
- Safety/OWASP:
Avoid following symlinks if paths are untrusted.
Do not log file contents or paths verbosely if sensitive.
Handle permissions explicitly if needed and avoid executing filenames from user input.
Reference implementation (literal or regex)
This separates the I/O pipeline from the transformation logic so you can swap replacement strategies.
```
java
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CsvTextRewriter {

    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final int BUFFER_SIZE = 64 * 1024; // 64 KiB

    public static void rewrite(Path source, Path destination, Function<String, String> transform) throws IOException {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("source/destination must not be null");
        }
        if (!Files.isRegularFile(source, LinkOption.NOFOLLOW_LINKS)) {
            throw new NoSuchFileException("Source is not a regular file: " + source);
        }
        if (!Files.isReadable(source)) {
            throw new AccessDeniedException("Source is not readable: " + source);
        }
        Path destDir = destination.getParent() != null ? destination.getParent() : Path.of(".");
        if (!Files.exists(destDir)) {
            throw new NoSuchFileException("Destination directory does not exist: " + destDir);
        }
        if (!Files.isWritable(destDir)) {
            throw new AccessDeniedException("Destination directory not writable: " + destDir);
        }

        // temp in the same dir for atomic move
        String destName = destination.getFileName().toString();
        String tempPrefix = (destName.length() >= 3 ? destName : "out") + ".";
        Path temp = Files.createTempFile(destDir, tempPrefix, ".tmp");

        boolean success = false;
        try (
            BufferedReader in = Files.newBufferedReader(source, CHARSET);
            BufferedWriter out = new BufferedWriter(
                Files.newBufferedWriter(temp, CHARSET, StandardOpenOption.TRUNCATE_EXISTING),
                BUFFER_SIZE
            )
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                String replaced = transform.apply(line);
                out.write(replaced);
                out.newLine(); // platform-specific EOL
            }
            out.flush();
            success = true;
        } finally {
            if (!success) {
                try { Files.deleteIfExists(temp); } catch (IOException ignore) {}
            }
        }

        // atomic move to destination
        try {
            Files.move(temp, destination, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (AtomicMoveNotSupportedException e) {
            // fallback to regular move (still a rename within same filesystem/dir)
            Files.move(temp, destination, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    // Literal replacement strategy: replaces exact substrings
    public static Function<String, String> literalReplacer(Map<String, String> replacements) {
        // Optional: order keys by descending length to avoid cascading overlaps
        final List<Map.Entry<String, String>> ordered = replacements.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getKey().length(), a.getKey().length()))
                .toList();

        return line -> {
            String out = line;
            for (var e : ordered) {
                out = out.replace(e.getKey(), e.getValue()); // literal replace
            }
            return out;
        };
    }

    // Regex replacement strategy: one combined alternation Pattern with mapping
    public static Function<String, String> regexReplacer(Map<String, String> regexToReplacement) {
        String alternation = regexToReplacement.keySet().stream()
                .map(p -> "(?:" + p + ")")
                .reduce((a, b) -> a + "|" + b)
                .orElse(""); // empty means no-op
        if (alternation.isEmpty()) return Function.identity();

        Pattern pattern = Pattern.compile(alternation);
        return line -> {
            Matcher m = pattern.matcher(line);
            StringBuffer sb = new StringBuffer();
            while (m.find()) {
                String matched = m.group();
                String replacement = regexToReplacement.get(matched);
                if (replacement == null) {
                    // If keys are patterns, we can't lookup by matched literal.
                    // In that case, you need group indices or a list of Patterns.
                    // For simplicity, quote the whole replacement and use the same text.
                    replacement = matched;
                }
                m.appendReplacement(sb, Matcher.quoteReplacement(replacement));
            }
            m.appendTail(sb);
            return sb.toString();
        };
    }

    // Overload for explicit single regex replacement
    public static Function<String, String> singleRegex(String regex, String replacement) {
        Pattern pattern = Pattern.compile(regex);
        String safeReplacement = Matcher.quoteReplacement(replacement);
        return line -> pattern.matcher(line).replaceAll(safeReplacement);
    }

    private CsvTextRewriter() {}
}
```

### Usage examples ###

- Literal replacements:

```
java
Map<String, String> map = Map.of(
"foo", "bar",
"abc", "XYZ"
);
CsvTextRewriter.rewrite(Path.of("in.csv"), Path.of("out.csv"), CsvTextRewriter.literalReplacer(map));
```
- Single regex:
```
java
CsvTextRewriter.rewrite(
Path.of("in.csv"),
Path.of("out.csv"),
CsvTextRewriter.singleRegex("\\s+", " ") // collapse whitespace
);
```
- CSV-aware alternative (when records can span lines or you only want to alter specific columns)
If your CSV is “real” CSV with quotes, commas, and potential embedded newlines, use a CSV parser. Example with Apache Commons CSV:

```
java
// <dependency>
//   <groupId>org.apache.commons</groupId>
//   <artifactId>commons-csv</artifactId>
//   <version>1.11.0</version>
// </dependency>
```

```
import org.apache.commons.csv.*;

public static void rewriteCsvColumns(Path source, Path destination,
Function<String, String> transform, int... columns) throws IOException {
Path destDir = destination.getParent() != null ? destination.getParent() : Path.of(".");
Path temp = Files.createTempFile(destDir, "csv.", ".tmp");

    CSVFormat inFmt = CSVFormat.DEFAULT.builder()
            .setQuote('"')
            .setRecordSeparator(null) // auto-detect; Commons CSV handles embedded newlines
            .build();

    CSVFormat outFmt = CSVFormat.DEFAULT.builder()
            .setQuote('"')
            .setRecordSeparator(System.lineSeparator())
            .build();

    boolean success = false;
    try (
        Reader reader = Files.newBufferedReader(source, StandardCharsets.UTF_8);
        CSVParser parser = new CSVParser(reader, inFmt);
        Writer writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8);
        CSVPrinter printer = new CSVPrinter(writer, outFmt)
    ) {
        for (CSVRecord rec : parser) {
            int size = rec.size();
            String[] row = new String[size];
            for (int i = 0; i < size; i++) {
                boolean toTransform = false;
                for (int c : columns) if (i == c) { toTransform = true; break; }
                row[i] = toTransform ? transform.apply(rec.get(i)) : rec.get(i);
            }
            printer.printRecord((Object[]) row);
        }
        success = true;
    } finally {
        if (!success) try { Files.deleteIfExists(temp); } catch (IOException ignore) {}
    }

    try {
        Files.move(temp, destination, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
    } catch (AtomicMoveNotSupportedException e) {
        Files.move(temp, destination, StandardCopyOption.REPLACE_EXISTING);
    }
}
```


### Training tasks (with guidance)

- Implement the basic literal replacer:

Write a program that takes args: input.csv, output.csv.
Replace "N/A" with empty string and "—" (em-dash) with "-" (hyphen).
Verify the output file is created via an atomic move. Add logs for each phase.
Edge cases: empty file, file with only one very long line.

- Write JUnit tests for failure paths:

Simulate an exception during processing (e.g., throw after N lines). Assert the temp file is deleted and destination not modified.
Use TemporaryFolder/TempDir to isolate filesystem.
Check behavior when destination dir is not writable.
- Regex correctness and safety:

Replace any sequence of 2+ commas with a single comma: regex "(,){2,}" -> "," using singleRegex.
Add a test where replacement contains "$1" and "" characters; ensure you use Matcher.quoteReplacement to avoid surprises.
Measure performance vs literal replacement on a 200 MB file.
- Line separator handling:

Create a file with CRLF line endings. Confirm your output uses platform line endings.
Optional: extend the I/O method to preserve the original line endings by detecting "\r\n" vs "\n" (advanced).
- CSV semantics:

Build a CSV sample where a single quoted field contains a newline.
Show that naive line-by-line breaks the record.
Switch to the Commons CSV approach to handle it correctly, transforming only column 2.
- Atomic move behavior:

Create temp and destination on different drives to see AtomicMoveNotSupportedException.
Ensure your code gracefully falls back to non-atomic move without data loss.
Performance tuning:

Benchmark BUFFER_SIZE values (8 KiB, 64 KiB, 256 KiB) on a large file.
Compare BufferedReader loop vs Files.lines().forEach and note GC/throughput.
- Robust CLI:

Add options: --charset, --regex, --columns=1,3,5, --in-place (implement as temp+atomic rename of source).
Validate arguments and print clear usage. Adhere to KISS: don’t add options you don’t need (YAGNI).
Security checks:

If paths are user-provided, reject absolute paths outside an allowed base dir.
Refuse to follow symlinks: if Files.isSymbolicLink(source), abort.
Avoid logging sensitive file paths or contents.
- Observability:

Add minimal progress logging: every N lines processed.
Return an exit code and brief summary (lines processed, duration, throughput).