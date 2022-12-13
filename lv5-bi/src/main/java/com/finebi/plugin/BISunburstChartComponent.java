package com.finebi.plugin;

import com.fr.web.struct.Component;
import com.fr.web.struct.category.ParserType;
import com.fr.web.struct.category.ScriptPath;
import com.fr.web.struct.category.StylePath;


public class BISunburstChartComponent extends Component {

    public static final BISunburstChartComponent KEY = new BISunburstChartComponent();

    private BISunburstChartComponent() {

    }

    @Override
    public ScriptPath script() {
        return ScriptPath.build("com/finebi/plugin/web/js/chart.js");
    }

    @Override
    public StylePath style() {
        return StylePath.build("com/finebi/plugin/web/css/chart.css", ParserType.DYNAMIC);
    }
}