package com.fr.plugin.parameter.slider.design;

import com.fanruan.api.design.DesignKit;
import com.fanruan.api.design.work.form.creator.Attribute;
import com.fanruan.api.design.work.form.creator.XOpenCreator;
import com.fr.design.editor.editor.IntegerEditor;
import com.fr.design.mainframe.widget.editors.DoubleEditor;
import com.fr.design.mainframe.widget.editors.StringEditor;
import com.fr.design.mainframe.widget.editors.WidgetValueEditor;
import com.fr.plugin.parameter.slider.fun.Slider;

import javax.swing.*;
import java.awt.*;
/**
 * @author Hangdonglin
 * @version 10.0
 * Created by Hangdonglin on 2022/12/6
 */
public class XSlider extends XOpenCreator {

    public XSlider(Slider slider, Dimension size) {
        super(slider,  size);
    }

    @Override
    public Attribute[] attributes() {
        Attribute[] attributes = new Attribute[5];
        Attribute attr1 = Attribute
                .newAttribute("minValue", this.data.getClass())
                .i18n(DesignKit.i18nText("Plugin-Slider-Min-Value"))
                .editorClass(DoubleEditor.class)
                .keyValue("category", "Fine-Design_Basic_Advanced");
        Attribute attr2 = Attribute
                .newAttribute("maxValue", this.data.getClass())
                .i18n(DesignKit.i18nText("Plugin-Slider-Max-Value"))
                .editorClass(DoubleEditor.class)
                .keyValue("category", "Fine-Design_Basic_Advanced");

        Attribute attr3 = Attribute
                .newAttribute("initValue", this.data.getClass())
                .i18n(DesignKit.i18nText("Plugin-Slider_Widget_Value"))
                .editorClass(WidgetValueEditor.class)
                .keyValue("category", "Fine-Design_Basic_Advanced");


        Attribute attr4 = Attribute
                .newAttribute("decimal", this.data.getClass())
                .i18n(DesignKit.i18nText("Plugin-Slider-Decimal-Value"))
                .editorClass(IntegerEditor.class)
                .keyValue("category", "Fine-Design_Basic_Advanced");

        Attribute attr5 = Attribute
                .newAttribute("unit", this.data.getClass())
                .i18n(DesignKit.i18nText("Plugin-Slider-Unit-Value"))
                .editorClass(StringEditor.class)
                .keyValue("category", "Fine-Design_Basic_Advanced");

        attributes[0] = attr1;
        attributes[1] = attr2;
        attributes[2] = attr3;
        attributes[3] = attr4;
        attributes[4] = attr5;
        return attributes;
    }

    @Override
    protected JComponent initEditor() {
        if (editor == null) {
            JSlider slider = new JSlider();
            slider.setMinimum(0);
            slider.setMaximum(100);
            slider.setValue(50);
            editor = slider;
        }
        return editor;
    }
}
