package com.fr.plugin.db.mongodb.connect.core.handler;


import com.fr.plugin.db.mongodb.connect.core.handler.bean.SimpleMongoDBClient;
import com.fr.plugin.db.mongodb.connect.core.handler.bean.emb.MongoDB;
import com.fr.plugin.db.mongodb.table.util.MongoDBUtils;

import java.util.HashMap;
import java.util.Map;

public class CreateCollectionHandler {

    static private Map<String, MongoDB> clientMap = new HashMap<>();

    static public MongoDB createCollection(String host, String port, String username, String password, String database) {
        String uid = host + ":"+port+"@"+username+password+database;
        if (clientMap.containsKey(uid)) {
            return clientMap.get(uid);
        } else {
            //没设置用户名和密码
            if (username.equals("") || password.equals("")){
                MongoDB mongoDB = noPwd(host, port, database);
                MongoDBUtils.database=database;
                clientMap.put(uid,mongoDB);

                return mongoDB;
            }else {
                MongoDB mongoDB = usePwd(host, port, username, password, "admin");
                clientMap.put(uid,mongoDB);

                return mongoDB;

            }
        }
    }

    static private MongoDB noPwd(String host, String port, String database) {
        SimpleMongoDBClient simpleMongoDBClient = new SimpleMongoDBClient();

        simpleMongoDBClient.noPwd(host, port, database);

        return simpleMongoDBClient;
    }

    static private MongoDB usePwd(String host, String port, String username, String password, String database) {
        SimpleMongoDBClient simpleMongoDBClient = new SimpleMongoDBClient();

        simpleMongoDBClient.usePwd(host, port, username, password, database);
        return simpleMongoDBClient;
    }


}