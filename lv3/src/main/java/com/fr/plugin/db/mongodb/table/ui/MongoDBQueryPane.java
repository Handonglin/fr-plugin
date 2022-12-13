package com.fr.plugin.db.mongodb.table.ui;

import com.fanruan.api.design.DesignKit;
import com.fanruan.api.design.ui.component.UILabel;
import com.fanruan.api.design.ui.component.code.SyntaxConstants;
import com.fanruan.api.design.ui.component.code.UISyntaxTextArea;
import com.fanruan.api.design.ui.container.BasicPane;
import com.fanruan.api.design.ui.layout.TableLayoutKit;
import com.fanruan.api.design.util.GUICoreKit;
import com.fr.plugin.db.mongodb.table.util.MongoDbDesignUtils;

import java.awt.*;


public class MongoDBQueryPane extends BasicPane {
    //数据库编号：
    //private ValueEditorPane dbIndexEditor;
    //查询条件：
    private UISyntaxTextArea sqlTextPane;

    private UISyntaxTextArea scriptTextPane;

    public MongoDBQueryPane() {
        setLayout(new BorderLayout());

        sqlTextPane = new UISyntaxTextArea();

        scriptTextPane = new UISyntaxTextArea();


        //数据库编号：删了
        //查询条件：
        Component[][] coms = new Component[][]{
                {GUICoreKit.createBorderLayoutPane(new UILabel(DesignKit.i18nText("Plugin-MongoDB_Query_Condition") + ":"), BorderLayout.NORTH),
                        MongoDbDesignUtils.createConditionTextPane(sqlTextPane, SyntaxConstants.SYNTAX_STYLE_NONE, 280)}
        };

        double p = TableLayoutKit.PREFERRED;
        double f = TableLayoutKit.FILL;

        double[] rowSize = {p, p};
        double[] columnSize = {p, f};

        add(TableLayoutKit.createTableLayoutPane(coms, rowSize, columnSize));
    }



    @Override
    protected String title4PopupWindow() {
        return "Query";
    }

    public String getQuery() {
        return sqlTextPane.getText();
    }

    public void setQuery(String query) {
        sqlTextPane.setText(query);
    }

//    public OrderValue getOrderValue() {
//        return (OrderValue) dbIndexEditor.update();
//    }
//
//    public void setOrderValue(OrderValue orderValue) {
//        dbIndexEditor.populate(orderValue);
//    }

    public String getScript() {
        return scriptTextPane.getText();
    }

    public void setScript(String script) {
        scriptTextPane.setText(script);
    }
}