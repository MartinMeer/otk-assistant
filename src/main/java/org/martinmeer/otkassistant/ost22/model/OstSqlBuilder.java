package org.martinmeer.otkassistant.ost22.model;

import lombok.Setter;
import org.martinmeer.otkassistant.core.service.SqlBuilder;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

@Component
@Setter
public class OstSqlBuilder implements SqlBuilder {

    private String definedColumn;
    private String table;
    private String whereColumn;

    @Override
    public String buildSelectSql() {
        StringJoiner sj = new StringJoiner(" ");
        sanitizeIdentifiers(); // Проверка на SQL-инъекции
        sj.add("SELECT")
                .add(definedColumn)
                .add("FROM")
                .add(table)
                .add("WHERE")
                .add(whereColumn)
                .add("@>")
                .add(":value;");
        return sj.toString();
    }

    private void sanitizeIdentifiers() {
        if (!isValidIdentifier(definedColumn) || !isValidIdentifier(table)) {
            throw new IllegalArgumentException("Invalid identifier");
        }
    }

    // Разрешить только буквы и цифры "[a-zA-Z0-9_]+")
    private boolean isValidIdentifier(String identifier) {
        return identifier.matches("^[a-zA-Z0-9_]+$");
    }
}
