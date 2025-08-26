package org.martinmeer.repo;

import lombok.Getter;

import java.util.*;

@Getter
public class ElementTypeTable implements Table{
    private String tableName;
    private final Map<String, String> columns = new HashMap<>();


    public ElementTypeTable() {};
    public ElementTypeTable(String tableName, String... columnNames) {
        this.tableName = tableName;
        columns.addAll(Arrays.asList(columnNames));
    }
}
