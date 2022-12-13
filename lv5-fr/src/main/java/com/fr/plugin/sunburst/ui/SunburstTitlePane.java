package com.fr.plugin.sunburst.ui;

import com.fanruan.api.design.DesignKit;
import com.fanruan.api.design.chart.BaseOtherPane;
import com.fanruan.api.design.ui.component.formula.UIFormulaTextField;
import com.fr.plugin.sunburst.SunburstChart;
import javax.swing.*;
import java.awt.*;
/**
 * 6定义其他的面板
 * 如果要实现一个完全自定义的面板，则继承BaseOtherPane类，添加面板组件，并实现createContentPane方法来创建面板
 * @author Handonglin
 * @create 2022-12-08 9:32
 */
public class SunburstTitlePane extends BaseOtherPane<SunburstChart> {

    /*
     * 公式输入框
     */
    private UIFormulaTextField title;

    /*
     * 从对象属性中获取标题并更新到面板中
     * @param ob
     */
    @Override
    public void populate(SunburstChart ob) {
        title.populateBean(ob.getTitleFormula().toString());
    }

    /*
     * 将面板中配置的标题还原到对象属性
     * @param ob
     */
    @Override
    public void update(SunburstChart ob) {
        ob.getTitleFormula().setContent(title.updateBean());
    }

    /*
     * 该面板仅包含一个标题公式输入框
     * @return JPanel
     */
    @Override
    protected JPanel createContentPane() {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        title = new UIFormulaTextField();
        panel.add(title, BorderLayout.CENTER);
        return panel;
    }

    /*
     * 面板名称为标题
     * @return String
     */
    @Override
    public String title4PopupWindow() {
        return DesignKit.i18nText("Fine-Plugin_Sunburst_Title");
    }

}
