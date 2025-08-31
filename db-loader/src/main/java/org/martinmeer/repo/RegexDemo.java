package org.martinmeer.repo;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.swing.*;
import java.awt.*;

public class RegexDemo extends JFrame {
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JButton processButton;

    public RegexDemo() {
        setTitle("Java Regex Demo - Character Removal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Create input area
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Text"));
        inputArea = new JTextArea(5, 40);
        inputArea.setText("Example text with \"quotes\", [brackets], and:colons.");
        inputPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);

        // Create output area
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output (Removed: \", :, [, ])"));
        outputArea = new JTextArea(5, 40);
        outputArea.setEditable(false);
        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Create button
        processButton = new JButton("Process Text");
        processButton.addActionListener(e -> processText());

        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);
        add(processButton, BorderLayout.SOUTH);
    }

    private void processText() {
        String input = inputArea.getText();

        // Create the pattern to match: ", :, [, or ]
        Pattern pattern = Pattern.compile("[\":\\[\\]]");

        // Use matcher to replace all matches with empty string
        Matcher matcher = pattern.matcher(input);
        String result = matcher.replaceAll("");

        outputArea.setText(result);

        // Also show matches in console
        System.out.println("Original text: " + input);

        matcher = pattern.matcher(input);
        while (matcher.find()) {
            System.out.println("Found match: '" + matcher.group() + "' at position " + matcher.start());
        }

        System.out.println("Processed text: " + result);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegexDemo demo = new RegexDemo();
            demo.setVisible(true);
        });
    }
}