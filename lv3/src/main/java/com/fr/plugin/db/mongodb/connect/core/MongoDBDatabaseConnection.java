package com.fr.plugin.db.mongodb.connect.core;

import com.fanruan.api.conf.HolderKit;
import com.fanruan.api.data.open.BaseConnection;
import com.fanruan.api.i18n.I18nKit;
import com.fanruan.api.security.SecurityKit;
import com.fanruan.api.util.StringKit;
import com.fanruan.api.util.TypeKit;
import com.fr.config.holder.Conf;
import com.fr.data.impl.Connection;
import com.fr.plugin.db.mongodb.connect.core.handler.CreateCollectionHandler;
import com.fr.plugin.db.mongodb.connect.core.handler.bean.emb.MongoDB;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;
import com.fr.third.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties({"id", "data", "classInfo", "nameSpace", "driver"})
public class MongoDBDatabaseConnection extends BaseConnection {
    private static final String DEFAULT_MongoDB_HOST = "localhost";
    private static final int DEFAULT_MongoDB_PORT = 27017;


    private Conf<String> host = HolderKit.simple(DEFAULT_MongoDB_HOST);
    private Conf<String> port = HolderKit.simple(String.valueOf(DEFAULT_MongoDB_PORT));
    private Conf<String> username = HolderKit.simple(StringKit.EMPTY);
    private Conf<String> password = HolderKit.simple(StringKit.EMPTY);
    private Conf<String> database = HolderKit.simple(StringKit.EMPTY);


    public MongoDBDatabaseConnection() {

    }
    public String getDatabase() {
        return this.database.get();
    }

    public void setDatabase(String database) {
        this.database.set(database);
    }

    public String getUsername() {
        return this.username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getHost() {
        return host.get();
    }

    public void setHost(String host) {
        this.host.set(host);
    }

    public String getPort() {
        return port.get();
    }

    public void setPort(String port) {
        this.port.set(port);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }


    //测试连接的按钮不用自己写，只需要实现对应的测试方法就行，kms上说mongodb没有
    //正式用来测试连接的，那就直接真正连接，能连上就成功，连不上就失败
    @Override
    public void testConnection() throws Exception{
        System.out.println(getHost());
        System.out.println(getPort());
        System.out.println(getUsername());
        System.out.println(getPassword());
        System.out.println(getDatabase());
        System.out.println("======MongoDBDatabaseConnection testConnection Start==========");
        MongoDB client=null;
        try {
            client = createMongoDBClient();
            String test = client.test();
            if (test.equals("false")){
                System.out.println("======MongoDBDatabaseConnection testConnection End==========\n");
                throw new Exception();
            }
            System.out.println(test);
            System.out.println("======MongoDBDatabaseConnection testConnection End==========\n");

        }
        finally {
            //关闭测试的连接
            if (client!=null){
                client.close();
            }
        }

    }

    public MongoDB createMongoDBClient() throws Exception {
        return CreateCollectionHandler.createCollection(
                getHost(),
                getPort(),
                getUsername(),
                getPassword(),
                getDatabase());
    }




    //不动
    @Override
    public String connectMessage(boolean status) {
        if (status) {
            return I18nKit.getLocText("Plugin-MongoDB_Connection_Successfully") + "!";
        } else {
            return I18nKit.getLocText("Plugin-MongoDB_Connection_Failed") + "!";
        }
    }

    //不动
    @Override
    public void addConnection(List<String> list, String connectionName, Class<? extends Connection>[] acceptTypes) {
        for (Class<? extends Connection> accept : acceptTypes) {
            if (TypeKit.classInstanceOf(getClass(), accept)) {
                list.add(connectionName);
                break;
            }
        }
    }
    //不动
    @Override
    public String getDriver() {
        return null;
    }
    //不动

    @Override
    public String getOriginalCharsetName() {
        return null;
    }
    //不动

    @Override
    public void setOriginalCharsetName(String s) {

    }
    //不动

    @Override
    public String getNewCharsetName() {
        return null;
    }
    //不动

    @Override
    public void setNewCharsetName(String s) {

    }

    //不动
    @Override
    public void readXML(XMLableReader reader) {
        super.readXML(reader);
        if (reader.isChildNode()) {
            String tagName = reader.getTagName();
            if ("Attr".equals(tagName)) {
                setHost(reader.getAttrAsString("host", StringKit.EMPTY));
                setPort(reader.getAttrAsString("port", String.valueOf(DEFAULT_MongoDB_PORT)));
                setUsername(reader.getAttrAsString("username", StringKit.EMPTY));
                String pwd = reader.getAttrAsString("password", StringKit.EMPTY);
                if (StringKit.isNotEmpty(pwd)) {
                    setPassword(SecurityKit.encrypt(pwd));
                }
                setDatabase(reader.getAttrAsString("database", StringKit.EMPTY));

            }
        }
    }

    //不动
    @Override
    public void writeXML(XMLPrintWriter writer) {
        super.writeXML(writer);
        writer.startTAG("Attr");
        writer.attr("host", getHost());
        writer.attr("port", getPort());
        writer.attr("username", getUsername());
        if (StringKit.isNotEmpty(getPassword())) {
            writer.attr("password", SecurityKit.decrypt(getPassword()));
        }
        writer.attr("database", getDatabase());

        writer.end();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        MongoDBDatabaseConnection cloned = (MongoDBDatabaseConnection) super.clone();
        cloned.host = (Conf<String>) host.clone();
        cloned.port = (Conf<String>) port.clone();
        cloned.username = (Conf<String>) username.clone();
        cloned.password = (Conf<String>) password.clone();
        cloned.database = (Conf<String>) database.clone();

        return cloned;
    }
}