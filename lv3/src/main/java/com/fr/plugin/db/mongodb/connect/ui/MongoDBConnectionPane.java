package com.fr.plugin.db.mongodb.connect.ui;

import com.fanruan.api.design.DesignKit;
import com.fanruan.api.design.ui.component.*;
import com.fanruan.api.design.ui.layout.TableLayoutKit;
import com.fanruan.api.design.util.GUICoreKit;
import com.fanruan.api.design.work.DatabaseConnectionPane;
import com.fanruan.api.util.IOKit;
import com.fr.plugin.db.mongodb.connect.core.MongoDBDatabaseConnection;
import com.fr.plugin.db.mongodb.table.util.MongoDBUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class MongoDBConnectionPane extends DatabaseConnectionPane<MongoDBDatabaseConnection> {

    //数据库地址
    private UITextField hostTextField;
    //端口
    private UITextField portNumberField;
    //用户名
    private UITextField usernameField;
    //密码
    private UIPasswordField passwordTextField;

    private UITextField databaseField;



    //配置连接的那个界面
    @Override
    protected JPanel mainPanel() {
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        hostTextField = new UITextField();
        portNumberField = new UITextField();
        usernameField = new UITextField();
        passwordTextField = new UIPasswordField();
        databaseField = new UITextField();


        JPanel globalConfigPane = new JPanel();
        GridLayout gridLayout = new GridLayout(1, 2);
        globalConfigPane.setLayout(gridLayout);


        //数据库地址旁边的帮助问号
        UIButton helpButton = new UIButton();
        helpButton.setIcon(IOKit.readIcon("/com/fr/plugin/db/mongodb/images/help.png"));
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(MongoDBConnectionPane.this),
                        DesignKit.i18nText("Plugin-MongoDB_Cluster_Config_Description"),
                        DesignKit.i18nText("Plugin-MongoDB_Connect_Cluster_Description"),
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        //5行输入框组件
        Component[][] components = new Component[][]{
                {new UILabel(DesignKit.i18nText("Plugin-MongoDB_Host") + ":"),
                        GUICoreKit.createBorderLayoutPane(hostTextField, BorderLayout.CENTER,
                                GUICoreKit.createBorderLayoutPane(
                                        helpButton, BorderLayout.EAST
                                ), BorderLayout.EAST)},
                {new UILabel(DesignKit.i18nText("Plugin-MongoDB_Port") + ":"), portNumberField},
                {new UILabel(DesignKit.i18nText("Plugin-MongoDB_User_Name") + ":"), usernameField},
                {new UILabel(DesignKit.i18nText("Plugin-MongoDB_Password") + ":"), passwordTextField},
                {new UILabel(DesignKit.i18nText("Plugin-MongoDB_DataBase_Name") + ":"), databaseField},
                {null, globalConfigPane}
        };


        double p = TableLayoutKit.PREFERRED;

        double[] rowSize = new double[]{p, p, p, p, p};
        double[] columnSize = new double[]{p, 400};

        JPanel settingsUI = TableLayoutKit.createTableLayoutPane(components, rowSize, columnSize);
        //标题
        settingsUI.setBorder(UITitledBorder.createBorderWithTitle("MongoDB"));

        JPanel centerPane = GUICoreKit.createNormalFlowInnerContainerPane();

        centerPane.add(settingsUI);

        pane.add(centerPane, BorderLayout.CENTER);
        return pane;
    }

    @Override
    protected void populateSubDatabaseConnectionBean(MongoDBDatabaseConnection ob) {
        hostTextField.setText(ob.getHost());
        portNumberField.setText(ob.getPort());
        usernameField.setText(ob.getUsername());
        passwordTextField.setText(ob.getPassword());
        databaseField.setText(ob.getDatabase());


    }

    @Override
    protected MongoDBDatabaseConnection updateSubDatabaseConnectionBean() {
        MongoDBDatabaseConnection connection = new MongoDBDatabaseConnection();
        connection.setHost(hostTextField.getText());
        connection.setPort(portNumberField.getText());
        connection.setUsername(usernameField.getText());
        connection.setPassword(passwordTextField.getText());
        connection.setDatabase(databaseField.getText());

        //设置选择的数据据，查询时要用
        MongoDBUtils.database=databaseField.getText();

        return connection;
    }

    @Override
    protected String title4PopupWindow() {
        return "MongoDB";
    }
}