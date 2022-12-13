package com.fr.plugin.parameter.slider;

import com.fr.stable.fun.impl.AbstractJavaScriptPlaceHolder;

/**
 * @author Hangdonglin
 * @version 10.0
 * Created by Hangdonglin on 2022/12/6
 */
public class SliderJavaScriptHolderBridge extends AbstractJavaScriptPlaceHolder {

    @Override
    public ScriptTag[] holderScripts() {
        return new ScriptTag[]{
                ScriptTag.build().type("text/javascript").src("http://fanruan.design/fineui/2.0/fineui.min.js")
        };
    }
}
