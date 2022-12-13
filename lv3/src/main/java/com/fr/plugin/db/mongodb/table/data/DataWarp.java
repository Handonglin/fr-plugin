package com.fr.plugin.db.mongodb.table.data;

import java.util.List;

public class DataWarp<T> {
    //List<T>以列为单位进行组织，
    private List<List<T>> data;
    private String[] columnNames;

    public List<List<T>> getData() {
        return data;
    }

    public void setData(List<List<T>> data) {
        this.data = data;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }
}
