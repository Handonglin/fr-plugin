package com.fr.plugin.db.mongodb.table.core;

import com.fanruan.api.conf.HolderKit;
import com.fanruan.api.data.ConnectionKit;
import com.fanruan.api.data.open.BaseTableData;
import com.fanruan.api.util.StringKit;
import com.fanruan.api.xml.XmlKit;
import com.fr.base.TableData;
import com.fr.config.holder.Conf;
import com.fr.data.impl.Connection;
import com.fr.general.data.DataModel;
import com.fr.intelli.record.Focus;
import com.fr.intelli.record.Original;
import com.fr.plugin.db.mongodb.connect.core.MongoDBDatabaseConnection;
import com.fr.plugin.db.mongodb.table.util.MongoDBUtils;
import com.fr.plugin.db.mongodb.table.data.MongoDBConstants;
import com.fr.record.analyzer.EnableMetrics;
import com.fr.script.Calculator;
import com.fr.stable.ParameterProvider;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;


@EnableMetrics
public class MongoDBTableDatas extends BaseTableData {

    private static final long serialVersionUID = 7017455818551800001L;
    private Conf<Connection> database = HolderKit.obj(null, Connection.class);
    private Conf<String> query = HolderKit.simple(StringKit.EMPTY);

    public void setDatabase(Connection c) {
        this.database.set(c);
    }

    public Connection getDatabase() {
        return database.get();
    }



    public String getQuery() {
        return query.get();
    }

    public void setQuery(String query) {
        this.query.set(query);
    }


    public void setParameters(ParameterProvider[] providers) {
        super.setDefaultParameters(providers);
    }

    @Override
    public DataModel createDataModel(Calculator calculator) {
        return createDataModel(calculator, TableData.RESULT_ALL);
    }

    @Override
    @Focus(id = MongoDBConstants.PLUGIN_ID, text = "Plugin-MongoDB_DB", source = Original.PLUGIN)
    public DataModel createDataModel(Calculator calculator, int rowCount) {
        ParameterProvider[] providers = getParameters(calculator);
        ParameterProvider[] ps = Calculator.processParameters(calculator, providers);
        Connection connection = database.get();
        String name = MongoDBUtils.getName(connection);

        if (StringKit.isNotEmpty(name)) {
            MongoDBDatabaseConnection mongoConnect=ConnectionKit.getConnection(name, MongoDBDatabaseConnection.class);
            if (mongoConnect!=null){
                DataModel model= null;
                try {
                    model = new MongoDBTableDataModel(
                            calculator,
                            ps,
                            mongoConnect,
                            MongoDBUtils.calculateQuery(query.get(),ps),
                            rowCount
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return model;

            }
        }
        return null;
    }


    public void readXML(XMLableReader reader) {
        super.readXML(reader);

        if (reader.isChildNode()) {
            String tmpName = reader.getTagName();
            String tmpVal;

            if (Connection.XML_TAG.equals(tmpName)) {
                if (reader.getAttrAsString("class", null) != null) {
                    Connection con = XmlKit.readXMLConnection(reader);
                    this.setDatabase(con);
                }
            } else if ("Query".equals(tmpName)) {
                tmpVal = reader.getElementValue();
                if (isNotNullValue(tmpVal)) {
                    this.setQuery(tmpVal);
                }
            }
        }
    }

    @Override
    public void writeXML(XMLPrintWriter writer) {
        super.writeXML(writer);

        if (this.database.get() != null) {
            XmlKit.writeXMLConnection(writer, this.database.get());
        }
        writer.startTAG("Query").textNode(getQuery()).end();
    }

    private boolean isNotNullValue(String value) {
        return value != null && !"null".equals(value);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        MongoDBTableDatas cloned = (MongoDBTableDatas) super.clone();
        cloned.database = (Conf<Connection>) database.clone();
        cloned.query = (Conf<String>) query.clone();
        return cloned;
    }
}