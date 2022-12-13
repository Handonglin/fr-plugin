package com.fr.plugin.db.mongodb.table;

import com.fanruan.api.design.work.AbstractTableDataPane;
import com.fanruan.api.i18n.I18nKit;
import com.fr.base.TableData;
import com.fr.design.fun.ServerTableDataDefineProvider;
import com.fr.design.fun.impl.AbstractTableDataDefineProvider;
import com.fr.plugin.db.mongodb.table.core.MongoDBTableDatas;
import com.fr.plugin.db.mongodb.table.ui.MongoDBTableDataPane;


public class MongoDBTableDataDefiner extends AbstractTableDataDefineProvider implements ServerTableDataDefineProvider {

    @Override
    public int currentAPILevel() {
        return CURRENT_LEVEL;
    }
    //数据集类
    @Override
    public Class<? extends TableData> classForTableData() {
        return MongoDBTableDatas.class;
    }

    @Override
    public Class<? extends TableData> classForInitTableData() {
        return MongoDBTableDatas.class;
    }
    //数据集UI类
    @Override
    public Class<? extends AbstractTableDataPane> appearanceForTableData() {
        return MongoDBTableDataPane.class;
    }

    @Override
    public String nameForTableData() {
        return I18nKit.getLocText("Plugin-MongoDB_Table_Data");
    }

    @Override
    public String prefixForTableData() {
        return "MongoDB";
    }

    @Override
    public String iconPathForTableData() {
        return "/com/fr/plugin/db/mongodb/images/mongoData.png";
    }
}