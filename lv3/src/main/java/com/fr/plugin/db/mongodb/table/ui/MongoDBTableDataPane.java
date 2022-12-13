package com.fr.plugin.db.mongodb.table.ui;

import com.fanruan.api.data.ConnectionKit;
import com.fanruan.api.design.DesignKit;
import com.fanruan.api.util.StringKit;
import com.fr.plugin.db.mongodb.table.core.MongoDBTableDatas;
import com.fr.script.Calculator;
import com.fr.stable.ParameterProvider;

import javax.swing.*;
import java.util.List;


public class MongoDBTableDataPane extends MongoDBBaseTableDataPane<MongoDBTableDatas> {

    private MongoDBQueryPane queryPane;


    //模板设计模式，大部分都在父类，只有 数据库编号+查询条件 在子类
    public MongoDBTableDataPane() {
        super();
    }


    @Override
    protected String title4PopupWindow() {
        return DesignKit.i18nText("Plugin-MongoDB_Query_Condition");
    }

    //单例创建queryPane
    protected JComponent createQueryPane() {
        if (queryPane == null) {
            queryPane = new MongoDBQueryPane();
        }
        return queryPane;
    }

    public String[] paramTexts() {
        //只会用到第一个
        return new String[]{queryPane.getQuery(), queryPane.getScript()};
    }


    //前者用于呈现数据，比如从JSONConnection中提取host/username/password等配置信息，展现在界面上
    @Override
    public void populateBean(MongoDBTableDatas tableData) {
        if (tableData == null) {
            return;
        }
        Calculator c = Calculator.createCalculator();

        editorPane.populate(tableData.getParameters(c));

        //哪一个连接
        chosePane.populateConnection(tableData.getDatabase());

        //查询语句
        queryPane.setQuery(tableData.getQuery());

    }


    //后者用于将界面上的配置，保存到J对象里。
    @Override
    public MongoDBTableDatas updateBean() {
        MongoDBTableDatas tableData = new MongoDBTableDatas();

        String connectionName = chosePane.getSelectMongoDBConnectionName();
        if (StringKit.isNotEmpty(connectionName)) {
            tableData.setDatabase(ConnectionKit.createNameConnection(connectionName));
        }

        List<ParameterProvider> parameterList = editorPane.update();
        ParameterProvider[] parameters = parameterList.toArray(new ParameterProvider[parameterList.size()]);

        //设置参数
        tableData.setParameters(parameters);
        //设置查询语句
        tableData.setQuery(queryPane.getQuery());

        return tableData;
    }
}