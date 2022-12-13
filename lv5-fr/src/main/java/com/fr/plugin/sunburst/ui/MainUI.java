package com.fr.plugin.sunburst.ui;

import com.fanruan.api.design.DesignKit;
import com.fanruan.api.design.chart.BaseChartTypeUI;
import com.fanruan.api.design.chart.BaseDataPane;
import com.fanruan.api.design.chart.DefaultTypePane;
import com.fr.design.gui.frpane.AttributeChangeListener;
import com.fanruan.api.design.chart.*;

/*
 * 7定义图表的所有界面
 * 图表名称、图表子名称、图表示例图片路径，图表Icon路径等，继承BaseChartTypeUI
 * @author Handonglin
 * @create 2022-12-08 9:48
 */
public class MainUI extends BaseChartTypeUI {

    /*
     * 图表配置界面类型选择面板
     * @return DefaultTypePane
     */
    @Override
    public DefaultTypePane getPlotTypePane() {
        return new SunburstTypePane();
    }

    /*
     * 图表配置界面数据配置面板，组合了我们定义的单元格数据源配置面板和数据集数据源配置面板
     * @param listener
     * @return BaseDataPane
     */
    @Override
    public BaseDataPane getChartDataPane(AttributeChangeListener listener) {
        return new BaseDataPane(listener) {
            @Override
            protected SingleDataPane createSingleDataPane() {
                return new SingleDataPane(new SunburstDataSetFieldsPane(), new SunburstCellDataFieldsPane());
            }
        };
    }

    /*
     * 图表配置界面其他面板的集合，这里定义了一个标题配置的面板和一个包含自动刷新和超链的面板
     * @param listener
     * @return BaseOtherPane[]
     */
    @Override
    public BaseOtherPane[] getAttrPaneArray(AttributeChangeListener listener) {
        return new BaseOtherPane[]{new SunburstTitlePane(), new DefaultOtherPane()};
    }

    /*
     * 图表Icon的路径
     * @return
     */
    @Override
    public String getIconPath() {
        return "com/fr/plugin/sunburst/images/sun.png";
    }

    /*
     * 选择插入图表时，图表的名称
     * @return
     */
    @Override
    public String getName() {
        return DesignKit.i18nText("Fine-Plugin_Sunburst_Chart");
    }

    /*
     * 选择插入图表时，图表子类型的名称
     * @return
     */
    @Override
    public String[] getSubName() {
        return new String[]{
                DesignKit.i18nText("Fine-Plugin_Simple_Sunburst"),
        };
    }

    /*
     * 选择插入图表时，图表子类型的图片路径
     * @return
     */
    @Override
    public String[] getDemoImagePath() {
        return new String[]{
                "com/fr/plugin/sunburst/images/icon.png",
        };
    }
}