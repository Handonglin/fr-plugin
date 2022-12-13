package com.fr.plugin.parameter.slider;

import com.fanruan.api.i18n.I18nKit;
import com.fr.design.fun.impl.AbstractParameterWidgetOptionProvider;
import com.fr.form.ui.Widget;
import com.fr.plugin.parameter.slider.design.XSlider;
import com.fr.plugin.parameter.slider.fun.Slider;

/**
 * @author Hangdonglin
 * @version 10.0
 * Created by Hangdonglin on 2022/12/6
 */
public class SliderBridge extends AbstractParameterWidgetOptionProvider {

    /*
     * 自定义参数控件的实际类，该类需要继承自com.fr.form.ui.Widget
     * 如果有控件值属性，该类需要实现DataControl接口，否则可能有部分功能无法使用，比如：值编辑器选择控件的时候无法找到插件里的控件
     * @return 控件类
     */
    @Override
    public Class<? extends Widget> classForWidget() {
        return Slider.class;
    }

    /*
     * 自定义参数控件的设计界面类，该类需要继承自com.fr.form.designer.creator.XWidgetCreator
     * @return 控件设计界面类
     */
    @Override
    public Class<?> appearanceForWidget() {
        return XSlider.class;
    }

    /**
     * 自定义参数控件在设计器界面上的图标路径
     * @return 图标所在的路径
     */
    @Override
    public String iconPathForWidget() {
        return "/com/fr/plugin/parameter/slider/images/slider.png";
    }

    /**
     * 自定义参数控件的名字
     * @return 控件名字
     */
    @Override
    public String nameForWidget() {
        return I18nKit.getLocText("Plugin-Slider_Name");
    }
}
