package com.fr.plugin.parameter.slider;

import com.fr.stable.fun.impl.AbstractStylePlaceHolder;

/**
 * @author Hangdonglin
 * @version 10.0
 * Created by Hangdonglin on 2022/12/6
 */
public class SliderStyleHolderBridge extends AbstractStylePlaceHolder {
    @Override
    public LinkTag[] holderLinks() {
        return new LinkTag[] {
                LinkTag.build().type("text/css").rel("stylesheet").href("http://fanruan.design/fineui/2.0/fineui.min.css")
        };
    }
}
