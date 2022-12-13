package com.fr.plugin.db.mongodb.table.ui;

import com.fanruan.api.data.ConnectionKit;
import com.fanruan.api.design.ui.component.UIIntNumberField;
import com.fanruan.api.design.ui.component.UIPlaceholderTextField;
import com.fanruan.api.design.ui.container.BasicPane;
import com.fanruan.api.design.work.ConnectionComboBoxPanel;
import com.fanruan.api.log.LogKit;
import com.fanruan.api.util.ArrayKit;
import com.fanruan.api.util.StringKit;
import com.fr.data.impl.Connection;
import com.fr.data.operator.DataOperator;
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
    private UIPlaceholderTextField keysPatternTextField;
    private UIIntNumberField dbIndexNumberField;

    public MongoDBConnectionChosePane() {
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
                    listMatchedPatternKeys(connection);
                }
            }
        });
        add(connectionComboBoxPanel, BorderLayout.NORTH);

    }


    private void clearList() {
        listModel.clear();
    }

    private void listMatchedPatternKeys(final MongoDBDatabaseConnection connection) {
        clearList();
        new SwingWorker<String[], Void>() {

            @Override
            protected String[] doInBackground() throws Exception {
                String keysPattern = "";
                if (StringKit.isEmpty(keysPattern)) {
                    return ArrayKit.EMPTY_STRING_ARRAY;
                } else {
                    String dbIndex = String.valueOf(dbIndexNumberField.getInt());
                    return DataOperator.getInstance().getTableSummary(connection, keysPattern, dbIndex);
                }
            }

            @Override
            protected void done() {
                try {
                    String[] keys = get();
                    for (String name : keys) {
                        listModel.addElement(name);
                    }
                    fireDataLoaded(keys);
                } catch (Exception e) {
                    LogKit.error(e.getMessage(), e);
                }
            }
        }.execute();
    }

    public String getSelectMongoDBConnectionName() {
        return connectionComboBoxPanel.getSelectedItem();
    }

    public void populateConnection(Connection connection) {
        connectionComboBoxPanel.populate(connection);
    }

    public void addDataLoadedListener(DataLoadedListener listener) {
        listeners.add(listener);
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