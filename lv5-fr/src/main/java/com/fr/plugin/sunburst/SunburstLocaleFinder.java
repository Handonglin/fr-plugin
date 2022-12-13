package com.fr.plugin.sunburst;

import com.fr.plugin.transform.ExecuteFunctionRecord;
import com.fr.plugin.transform.FunctionRecorder;
import com.fr.stable.fun.impl.AbstractLocaleFinder;

@FunctionRecorder
public class SunburstLocaleFinder extends AbstractLocaleFinder {
    @Override
    @ExecuteFunctionRecord
    public String find() {
        return "com/fr/plugin/locale/sunburst";
    }
}
