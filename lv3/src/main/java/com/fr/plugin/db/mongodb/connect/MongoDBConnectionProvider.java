package com.fr.plugin.db.mongodb.connect;

import com.fr.data.impl.Connection;
import com.fr.design.beans.BasicBeanPane;
import com.fr.design.fun.impl.AbstractConnectionProvider;
import com.fr.plugin.db.mongodb.connect.core.MongoDBDatabaseConnection;
import com.fr.plugin.db.mongodb.connect.ui.MongoDBConnectionPane;

public class MongoDBConnectionProvider extends AbstractConnectionProvider {

    @Override
    public int currentAPILevel() {
        return CURRENT_LEVEL;
    }

    @Override
    public String nameForConnection() {
        return "mongodb";
    }

    @Override
    public String iconPathForConnection() {
        return "/com/fr/plugin/db/mongodb/images/mongoData.png";
    }

    @Override
    public Class<? extends Connection> classForConnection() {
        return MongoDBDatabaseConnection.class;
    }

    @Override
    public Class<? extends BasicBeanPane<? extends Connection>> appearanceForConnection() {
        return MongoDBConnectionPane.class;
    }
}