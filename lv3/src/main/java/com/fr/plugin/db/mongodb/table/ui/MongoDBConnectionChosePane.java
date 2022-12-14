package com.fr.plugin.db.mongodb.table.ui;

import com.fanruan.api.data.ConnectionKit;
import com.fanruan.api.design.ui.container.BasicPane;
import com.fanruan.api.design.work.ConnectionComboBoxPanel;
import com.fanruan.api.util.ArrayKit;
import com.fanruan.api.util.StringKit;
import com.fr.data.impl.Connection;
import com.fr.plugin.db.mongodb.connect.core.MongoDBDatabaseConnection;
import com.fr.plugin.db.mongodb.connect.ui.event.DataLoadedListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class MongoDBConnectionChosePane extends BasicPane {

    private ConnectionComboBoxPanel connectionComboBoxPanel;
    private DefaultListModel listModel = new DefaultListModel();
    private List<DataLoadedListener> listeners = new ArrayList<DataLoadedListener>();

    public MongoDBConnectionChosePane() {
        init();
    }
    public void init(){
        setLayout(new BorderLayout(4, 4));
        //选择连接
        connectionComboBoxPanel = new ConnectionComboBoxPanel(Connection.class) {

            protected void filterConnection(Connection connection, String conName, List<String> nameList) {
                connection.addConnection(nameList, conName, new Class[]{MongoDBDatabaseConnection.class});
            }
        };

        connectionComboBoxPanel.addComboBoxActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = getSelectMongoDBConnectionName();
                if (StringKit.isEmpty(name)) {
                    clearList();
                    fireDataLoaded(ArrayKit.EMPTY_STRING_ARRAY);
                    return;
                }
                MongoDBDatabaseConnection connection = ConnectionKit.getConnection(name, MongoDBDatabaseConnection.class);
                if (connection != null) {
                    listMatchedPatternKeys();
                }
            }
        });
        add(connectionComboBoxPanel, BorderLayout.NORTH);
    }


    private void clearList() {
        listModel.clear();
    }

    private void listMatchedPatternKeys() {
        clearList();
    }

    public String getSelectMongoDBConnectionName() {
        return connectionComboBoxPanel.getSelectedItem();
    }

    public void populateConnection(Connection connection) {
        connectionComboBoxPanel.populate(connection);
    }

    private void fireDataLoaded(String[] data) {
        for (DataLoadedListener listener : listeners) {
            listener.fireEvent(data);
        }
    }

    @Override
    protected String title4PopupWindow() {
        return "Choose";
    }
}