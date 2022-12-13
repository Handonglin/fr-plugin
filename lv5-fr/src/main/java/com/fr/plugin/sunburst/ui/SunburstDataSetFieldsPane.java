package com.fr.plugin.sunburst.ui;

import com.fanruan.api.design.DesignKit;
import com.fanruan.api.design.chart.field.BaseDataSetFieldsPane;
import com.fanruan.api.design.ui.component.UIComboBox;
import com.fanruan.api.design.ui.component.chart.CalculateComboBox;
import com.fr.plugin.sunburst.SunburstColumnFieldCollection;
import java.awt.*;

/*
 * 3.2数据集数据源配置面板: 使用下拉选择框UIComboBox类作为数据集数据源的数据配置框，可以选择数据集中对应的字段
 * @author Handonglin
 * @create 2022-12-08 19:51
 */
public class SunburstDataSetFieldsPane extends BaseDataSetFieldsPane<SunburstColumnFieldCollection> {

    private UIComboBox idPane;
    private UIComboBox fidPane;
    private UIComboBox namePane;
    private UIComboBox valuePane;
    private CalculateComboBox calculateComboBox;

    @Override
    public void initComponents() {
        idPane = new UIComboBox<>();
        fidPane = new UIComboBox<>();
        namePane = new UIComboBox<>();
        valuePane = new UIComboBox<>();
        calculateComboBox = new CalculateComboBox();
        super.initComponents();
    }

    /*
     * 定义的选择框的名称，这里使用国际化的写法
     * @return String[]
     */
    @Override
    protected String[] fieldLabels() {
        return new String[]{
                DesignKit.i18nText("Fine-Plugin_Sunburst_ID"),
                DesignKit.i18nText("Fine-Plugin_Sunburst_FID"),
                DesignKit.i18nText("Fine-Plugin_Sunburst_Name"),
                DesignKit.i18nText("Fine-Plugin_Sunburst_Value"),
                DesignKit.i18nText("Fine-Plugin_Sunburst_Summary_Method")
        };
    }

    /*
     * 定义所有的组件，包含分类和值两个组件
     * @return Component[]
     */
    @Override
    protected Component[] fieldComponents() {
        return new Component[]{
                idPane,
                fidPane,
                namePane,
                valuePane,
                calculateComboBox
        };
    }

    /*
     * /定义公式下拉框组件
     * @return UIComboBox[]
     */
    @Override
    protected UIComboBox[] filedComboBoxes() {
        return new UIComboBox[] {
                idPane,
                fidPane,
                namePane,
                valuePane,
        };
    }

    /*
     * 通过SunburstColumnFieldCollection对象的属性，对面板进行了还原，普通下拉框使用了父类定义的populateField方法
     * @param dataConf
     */
    @Override
    public void populateBean(SunburstColumnFieldCollection dataConf) {
        populateField(idPane, dataConf.getId());
        populateField(fidPane, dataConf.getFid());
        populateField(namePane, dataConf.getName());
        populateFunctionField(valuePane, calculateComboBox, dataConf.getValue());
    }

    /*
     * 通过面板的值，生成SunburstColumnFieldCollection对象，
     * 普通下拉框使用了父类定义的updateField方法
     * @return
     */
    @Override
    public SunburstColumnFieldCollection updateBean() {
        SunburstColumnFieldCollection dataConfig = new SunburstColumnFieldCollection();
        updateField(idPane, dataConfig.getId());
        updateField(fidPane, dataConfig.getFid());
        updateField(namePane, dataConfig.getName());
        updateFunctionField(valuePane, calculateComboBox, dataConfig.getValue());
        return dataConfig;
    }
}
