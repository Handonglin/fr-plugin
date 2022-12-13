package com.fr.plugin.db.mongodb.connect.core.handler.bean.emb;

import com.fr.plugin.db.mongodb.table.data.DataWarp;

public interface MongoDB {
    String test() ;

    DataWarp find(String query);

    void close();
}
