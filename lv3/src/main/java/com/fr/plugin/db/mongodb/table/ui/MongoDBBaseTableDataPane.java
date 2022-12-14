package com.fr.plugin.db.mongodb.table.ui;

import com.fanruan.api.cal.ParameterKit;
import com.fanruan.api.design.DesignKit;
import com.fanruan.api.design.ui.component.table.UITableEditorPane;
import com.fanruan.api.design.ui.component.table.action.UITableEditAction;
import com.fanruan.api.design.ui.component.table.model.ParameterTableModel;
import com.fanruan.api.design.work.AbstractTableDataPane;
import com.fanruan.api.util.ArrayKit;
import com.fanruan.api.util.IOKit;
import com.fr.base.TableData;
import com.fr.stable.ParameterProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Handonglin
 * @version 10.0
 * Created by Handonglin on 2022-12-11
 */
public abstract class MongoDBBaseTableDataPane<T extends TableData> extends AbstractTableDataPane<T> {

    private static final String REFRESH_BUTTON = DesignKit.i18nText("Plugin-MongoDB_Refresh");

    protected MongoDBConnectionChosePane chosePane;

    protected UITableEditorPane<ParameterProvider> editorPane;


    public MongoDBBaseTableDataPane() {
        init();
    }
    private void init(){
        this.setLayout(new BorderLayout(4, 4));

        Box box = new Box(BoxLayout.Y_AXIS);

        ParameterTableModel model = new ParameterTableModel() {
            @Override
            public UITableEditAction[] createAction() {
                return new UITableEditAction[]{
                        new MoveUpAction(),
                        new MoveDownAction(),
                        new RefreshAction()
                };
            }
        };
        editorPane = new UITableEditorPane<ParameterProvider>(model);


        //数据库编号+查询条件 在子类MongoDBTableDataPane实现
        box.add(createQueryPane());

        //参数面板右边的刷新
        box.add(editorPane);

        JPanel sqlSplitPane = new JPanel(new BorderLayout(4, 4));

        //左边的很多选择框,就剩一个数据连接的选择框了
        chosePane = new MongoDBConnectionChosePane();
        chosePane.setPreferredSize(new Dimension(200, 200));

        //整个界面，左边是选择连接，右边是输入数据库和查询语句
        sqlSplitPane.add(chosePane, BorderLayout.WEST);
        sqlSplitPane.add(box, BorderLayout.CENTER);

        this.add(sqlSplitPane, BorderLayout.CENTER);
    }

    @Override
    protected String title4PopupWindow() {
        return DesignKit.i18nText("Plugin-MongoDB_Query");
    }

    protected abstract JComponent createQueryPane();

    protected abstract String[] paramTexts();


    private void refresh() {
        String[] paramTexts = paramTexts();

        ParameterProvider[] parameters = ParameterKit.analyze4Parameters(paramTexts, false);

        editorPane.populate(ArrayKit.addAll(parameters /*, providers*/));
    }


    protected class RefreshAction extends UITableEditAction {
        public RefreshAction() {
            this.setName(REFRESH_BUTTON);
            this.setSmallIcon(IOKit.readIcon("/com/fr/design/images/control/refresh.png"));
        }

        public void actionPerformed(ActionEvent e) {
            refresh();
        }

        @Override
        public void checkEnabled() {
        }
    }


}
