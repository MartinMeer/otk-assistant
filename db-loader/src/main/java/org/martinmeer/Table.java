package org.martinmeer;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Table {
    private String tableName;
    private final List<String> columns = new ArrayList<>();


    public Table() {};
    public Table(String tableName, String... columnNames) {
        this.tableName = tableName;
        columns.addAll(Arrays.asList(columnNames));
    }
}
