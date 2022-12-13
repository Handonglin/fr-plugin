package com.fr.plugin.db.mongodb;

import com.fr.stable.fun.impl.AbstractLocaleFinder;

public class MongoDBLocaleFinder extends AbstractLocaleFinder {
    @Override
    public String find() {
        return "com/fr/plugin/db/mongodb/locale/mongodb";
    }
}
