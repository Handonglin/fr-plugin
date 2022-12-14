package com.fr.plugin.db.mongodb.table.core;

import com.fanruan.api.data.open.BaseDataModel;
import com.fanruan.api.util.StringKit;
import com.fr.plugin.context.PluginContexts;
import com.fr.plugin.db.mongodb.connect.core.MongoDBDatabaseConnection;
import com.fr.plugin.db.mongodb.connect.core.handler.bean.emb.MongoDB;
import com.fr.plugin.db.mongodb.table.data.DataWarp;
import com.fr.stable.ParameterProvider;
import com.fr.stable.script.CalculatorProvider;

import java.util.List;

public class MongoDBTableDataModel extends BaseDataModel {
    private String[] columnNames;
    private List<List<String>> data;

    public MongoDBTableDataModel(CalculatorProvider calculator, ParameterProvider[] ps, MongoDBDatabaseConnection mc, String query, int rowCount) throws Exception {
        if (PluginContexts.currentContext().isAvailable()) {
            initMongoDBData(calculator, ps, mc, query, rowCount);
        } else {
            throw new RuntimeException("MongoDB Plugin License Expired!");
        }
    }

    //核心任务：
    //1.query查询
    //2.构造data数据和columName数据
    private void initMongoDBData(CalculatorProvider calculator, ParameterProvider[] ps, MongoDBDatabaseConnection mc, String query, int rowCount) throws Exception {
        if (StringKit.isEmpty(query)) {
            return;
        }
        System.out.println("======MongoDBTableDataModel=======");
        System.out.println("query:"+query);
        MongoDB mongoDBClient = mc.createMongoDBClient();

        DataWarp<String> dataWarp = mongoDBClient.find(query);

        columnNames=dataWarp.getColumnNames();
        data=dataWarp.getData();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public boolean hasRow(int rowIndex) {
        return data != null && data.get(0).size() > rowIndex;
    }

    @Override
    public int getRowCount() {
        return data == null ? 0 : data.get(0).size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (data != null && data.size() > columnIndex) {
            List<String> columnData = data.get(columnIndex);
            if (columnData != null && columnData.size() > rowIndex) {
                return columnData.get(rowIndex);
            }
        }
        return null;
    }

    @Override
    public void release() throws Exception {

    }
}