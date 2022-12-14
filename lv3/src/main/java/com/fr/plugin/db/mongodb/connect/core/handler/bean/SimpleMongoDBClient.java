package com.fr.plugin.db.mongodb.connect.core.handler.bean;

import com.fr.plugin.db.mongodb.connect.core.handler.bean.emb.MongoDB;
import com.fr.plugin.db.mongodb.table.data.DataWarp;
import com.fr.plugin.db.mongodb.table.util.MongoDBDataDealUtils;
import com.fr.plugin.db.mongodb.table.util.MongoDBUtils;
import com.fr.stable.AssistUtils;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;

public class SimpleMongoDBClient implements MongoDB{
    //连接到mongodb
    MongoClient mongoClient;
    //指定数据库
    MongoDatabase mongoDatabase;

    public void noPwd(String host,String port,String database){
        try{
            System.out.println("======SimpleMongoDBClient nopwd Create Start==========");
            System.out.println("host:"+host);
            System.out.println("port:"+port);
            System.out.println("database:"+database);
            mongoClient=new MongoClient(host,Integer.parseInt(port));

            mongoDatabase=mongoClient.getDatabase(database);

            System.out.println("======SimpleMongoDBClient nopwd Create End==========\n");


        }catch (Exception e){
            System.out.println(e);
            System.out.println("======SimpleMongoDBClient nopwd Create End==========\n");

        }

    }

    public void usePwd(String host, String port, String username, String password, String database) {
        System.out.println("======SimpleMongoDBClient Create whitpwd Start==========");
        System.out.println("host:"+host);
        System.out.println("port:"+port);
        System.out.println("host:"+host);
        System.out.println("port:"+port);
        System.out.println("username:"+username);
        System.out.println("password:"+password);
        System.out.println("database:"+database);

        ServerAddress serverAddress = new ServerAddress(host, Integer.parseInt(port));
        MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());
        mongoClient = new MongoClient(serverAddress, Arrays.asList(credential));
        mongoDatabase = mongoClient.getDatabase(database);
    }


    @Override
    public String test() {
        try {
            String s = mongoClient.getConnectPoint();
            return s;
        }catch (Exception e){

            return "false";
        }

    }

    @Override
    public DataWarp find(String query) {
        mongoDatabase=mongoClient.getDatabase(MongoDBUtils.database);
        String[] strings=query.split("\\.");
        //db.t1.find();
        String doc=strings[1];
        String action=strings[2];
        MongoCollection<Document> collection = mongoDatabase.getCollection(doc);
        MongoCursor<Document> results = collection.find().iterator();

        //处理结果集合,得到列名
        DataWarp<Object> dataWarpper = MongoDBDataDealUtils.getDataWarpper(results);

        return dataWarpper;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SimpleMongoDBClient
                && AssistUtils.equals(this.mongoClient, ((SimpleMongoDBClient)obj).mongoClient)
                && AssistUtils.equals(this.mongoDatabase, ((SimpleMongoDBClient)obj).mongoDatabase);
    }

    @Override
    public int hashCode() {
        return AssistUtils.hashCode(mongoClient);
    }

    @Override
    public void close() {
        //mongoClient.close();
    }

}
