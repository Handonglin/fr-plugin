package com.fr.plugin.sunburst.ui;

import com.fanruan.api.design.DesignKit;
import com.fanruan.api.design.chart.DefaultTypePane;
import com.fanruan.api.design.ui.component.UIButtonGroup;
import com.fanruan.api.util.StringKit;
import com.fr.plugin.sunburst.SunburstChart;
import com.fr.plugin.sunburst.type.SunburstType;

import javax.swing.*;
import java.awt.*;


/*
 * 5定义图表类型配置界面
 * @author Handonglin
 * @create 2022-12-08 20:32
 */
public class SunburstTypePane extends DefaultTypePane<SunburstChart> {
    private UIButtonGroup buttonGroup = new UIButtonGroup(new String[]{DesignKit.i18nText("Fine-Plugin_Legend_Right"), DesignKit.i18nText("Fine-Plugin_Legend_Left")});
    /*
     * 配置界面类型选择时子类型的图片路径，默认使用插入图表时的子类型图片，这里重新使用了尺寸不同的图片
     * @return String[]
     */
    @Override
    protected String[] getTypeIconPath() {
        return new String[]{
                "com/fr/plugin/sunburst/images/icon.png",
        };
    }

    /*
     * 根据chart的属性确定选择的是哪一个子类型，目前只实现了一种类型，留下待扩展
     * @param chart
     * @return int
     */
    @Override
    protected int getSelectIndexInChart(SunburstChart chart) {
        return 0;
    }

    /*
     * 根据选择的子类型的索引，来还原chart对象的属性。因为只实现了一种类型，该方法可以不实现，先留下，以后考虑扩展
     * @param chart
     * @param index
     */
    @Override
    protected void setSelectIndexInChart(SunburstChart chart, int index) {
        chart.setSunburstType(SunburstType.getType());
    }

    /*
     * 定义类型选择界面的组件集合，typePane代表子类型选择的组件，这里又定义了一个图例位置选择的组件。顺序决定了组件的上下位置
     * @param typePane
     * @return
     */
    @Override
    protected Component[][] getPaneComponents(JPanel typePane) {
        return new Component[][]{
                new Component[]{typePane},
                new Component[]{buttonGroup}
        };
    }

    /*
     * 根据对象属性还原界面的配置选项，先调用父类的方法实现子类型的还原，再完成本类中图例选项的还原
     * @param ob
     */
    @Override
    public void populateBean(SunburstChart ob) {
        super.populateBean(ob);
        buttonGroup.setSelectedIndex(StringKit.equals("left", ob.getLegendPosition()) ? 0 : 1);
    }

    /*
     * 根据界面的配置选项设置对象的属性，先调用父类的方法设置了图表子类型的属性，再完成本类中图例属性的设置
     * @param ob
     */
    @Override
    public void updateBean(SunburstChart ob) {
        super.updateBean(ob);
        ob.setLegendPosition(buttonGroup.getSelectedIndex() == 0 ? "left" : "right");
    }
}
