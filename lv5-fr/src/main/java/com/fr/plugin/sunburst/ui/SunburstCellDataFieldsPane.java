package com.fr.plugin.sunburst.ui;

import com.fanruan.api.design.DesignKit;
import com.fanruan.api.design.chart.field.BaseCellDataFieldsPane;
import com.fanruan.api.design.ui.component.formula.UIFormulaTextField;
import com.fr.plugin.sunburst.SunburstColumnFieldCollection;
import java.awt.*;

/**
 * 3.1定义数据配置界面
 * @author Handonglin
 * @create 2022-12-08 19:44
 */

//3.1定义数据配置界面，单元格数据源配置界面继承BaseCellDataFieldsPane，
// 数据集数据源配置界面继承BaseDataSetFieldsPane。
// 需要添加DemoColumnFieldCollection作为泛型使用。
public class SunburstCellDataFieldsPane extends BaseCellDataFieldsPane<SunburstColumnFieldCollection> {

    //其实就是选择框罢了 依次选中id fid value
    private UIFormulaTextField idPane;
    private UIFormulaTextField fidPane;
    private UIFormulaTextField namePane;
    private UIFormulaTextField valuePane;

    @Override
    public void initComponents() {
        idPane = new UIFormulaTextField();
        fidPane = new UIFormulaTextField();
        namePane = new UIFormulaTextField();
        valuePane = new UIFormulaTextField();
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
                DesignKit.i18nText("Fine-Plugin_Sunburst_Value")
        };
    }

    /*
     * 定义所有的组件，包含分类和值两个输入框
     * @return Component[]
     */
    @Override
    protected Component[] fieldComponents() {
        return new Component[]{
                idPane,
                fidPane,
                namePane,
                valuePane
        };
    }

    /*
     * 定义公式组件，包含分类和值两个输入框
     * @return UIFormulaTextField[]
     */
    @Override
    protected UIFormulaTextField[] formulaPanes() {
        return new UIFormulaTextField[]{
                idPane,
                fidPane,
                namePane,
                valuePane
        };
    }

    /*
     * 通过SunburstColumnFieldCollection对象的属性，对面板进行了还原，这里使用了父类定义的populateField方法
     * @param dataConf
     */
    @Override
    public void populateBean(SunburstColumnFieldCollection dataConf) {
        populateField(idPane, dataConf.getId());
        populateField(fidPane, dataConf.getFid());
        populateField(namePane, dataConf.getName());
        populateField(valuePane, dataConf.getValue());
    }

    /*
     * 通过面板的值，生成SunburstColumnFieldCollection对象，这里使用了父类定义的updateField方法
     * @return SunburstColumnFieldCollection
     */
    @Override
    public SunburstColumnFieldCollection updateBean() {
        SunburstColumnFieldCollection dataConfig = new SunburstColumnFieldCollection();
        updateField(idPane, dataConfig.getId());
        updateField(fidPane, dataConfig.getFid());
        updateField(namePane, dataConfig.getName());
        updateField(valuePane, dataConfig.getValue());
        return dataConfig;
    }
}
