package com.fr.plugin.db.mongodb.table.util;

import com.fanruan.api.design.macro.UIConstants;
import com.fanruan.api.design.ui.component.UIRoundedBorder;
import com.fanruan.api.design.ui.component.code.UISyntaxTextArea;
import com.fanruan.api.design.ui.component.code.UISyntaxTextScrollPane;

import java.awt.*;

/**
 * @author Handonglin
 * @version 10.0
 * Created by Handonglin on 2022-12-11
 */
public class MongoDbDesignUtils {
    public static UISyntaxTextScrollPane createConditionTextPane(UISyntaxTextArea editorPane, String type, int height) {
        editorPane.setSyntaxEditingStyle(type);
        UISyntaxTextScrollPane sqlTextScrollPane = new UISyntaxTextScrollPane(editorPane);
        sqlTextScrollPane.setBorder(new UIRoundedBorder(UIConstants.LINE_COLOR, 1, UIConstants.ARC));
        sqlTextScrollPane.setPreferredSize(new Dimension(680, height));
        sqlTextScrollPane.setLineNumbersEnabled(false);
        return sqlTextScrollPane;
    }
}
