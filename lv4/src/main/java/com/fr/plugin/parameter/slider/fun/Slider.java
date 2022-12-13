package com.fr.plugin.parameter.slider.fun;

import com.fanruan.api.report.form.category.ValueWidget;
import com.fanruan.api.report.form.value.InitializerKit;
import com.fr.form.ui.DataControl;
import com.fr.form.ui.WidgetValue;
import com.fr.form.ui.concept.data.ValueInitializer;
import com.fr.intelli.record.Focus;
import com.fr.json.JSONObject;
import com.fr.record.analyzer.EnableMetrics;
import com.fr.stable.StringUtils;
import com.fr.stable.UtilEvalError;
import com.fr.stable.script.CalculatorProvider;
import com.fr.stable.web.Repository;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;

/**
 * @author Hangdonglin
 * @version 10.0
 * Created by Hangdonglin on 2022/12/6
 */
@EnableMetrics
public class Slider extends ValueWidget implements DataControl {

    private double minValue;
    private double maxValue;
    private WidgetValue initValue;
    private int decimal;
    private String unit;
    private final String TAG = "slider";

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public WidgetValue getInitValue() {
        return initValue;
    }

    public void setInitValue(WidgetValue initValue) {
        this.initValue = initValue;
    }

    public int getDecimal() {
        return decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Slider() {
        super();
    }

    @Override
    @Focus(id = "com.fr.plugin.parameter.slider", text = "Plugin-Slider_Name")
    public void mixinJSON(Repository repository, CalculatorProvider calculatorProvider, JSONObject jsonObject) {
        try {
            jsonObject.put("minValue", minValue);
            jsonObject.put("maxValue", maxValue);
            jsonObject.put("initValue", calculatorProvider.evalValue(initValue.getDisplayValue()));
        } catch (UtilEvalError utilEvalError) {
            utilEvalError.printStackTrace();
        }
        jsonObject.put("decimal", decimal);
        jsonObject.put("unit", unit);

    }

    @Override
    public String getXType() {
        return "parameter.slider";
    }

    @Override
    public String[] supportedEvents() {
        return new String[0];
    }

    @Override
    public int[] getValueType() {
        return new int[]{
                TYPE_NUMBER,
                TYPE_STRING,
                TYPE_DATABINDING,
                TYPE_FORMULA};
    }

    @Override
    public void createValueResult(CalculatorProvider calculator, JSONObject widgetResult) {
        ValueInitializer initializer = getWidgetValue();
        if (initializer == null) {
            initializer = InitializerKit.newValue();
            setWidgetValue(initializer);
        }
        Object result = initializer.executeResult(calculator);
        widgetResult.put(widgetName.toUpperCase(), result == null ? StringUtils.EMPTY : result);
    }

    @Override
    public void readXML(XMLableReader reader) {
        super.readXML(reader);
        if (reader.isChildNode()) {
            String nodeName = reader.getTagName();
            if (TAG.equals(nodeName)) {
                minValue = reader.getAttrAsDouble("minValue", 0);
                maxValue = reader.getAttrAsDouble("maxValue", 100);
                initValue = new WidgetValue(reader.getAttrAsString("initValue","50"));
                decimal = reader.getAttrAsInt("decimal", 0);
                unit = reader.getAttrAsString("unit", "");
            }
        }
    }

    @Override
    public void writeXML(XMLPrintWriter writer) {
        super.writeXML(writer);
        writer.startTAG(TAG);
        writer.attr("minValue", minValue);
        writer.attr("maxValue", maxValue);
        writer.attr("initValue",initValue.getDisplayValue());
        writer.attr("decimal", decimal);
        writer.attr("unit", unit);
        writer.end();
    }

    @Override
    public String getDataBindDefaultValue(CalculatorProvider calculator) {
        return super.getDataBindDefaultValue(calculator);
    }
}
