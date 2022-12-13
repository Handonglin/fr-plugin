package com.fr.plugin.parameter.slider;

import com.fr.stable.fun.impl.AbstractJavaScriptFileHandler;

/**
 * @author Hangdonglin
 * @version 10.0
 * Created by Hangdonglin on 2022/12/6
 */
public class SliderJavaScriptBridge extends AbstractJavaScriptFileHandler {
    @Override
    public String[] pathsForFiles() {
        return new String[] {
                "/com/fr/plugin/parameter/slider/web/slider.js"
        };
    }
}
