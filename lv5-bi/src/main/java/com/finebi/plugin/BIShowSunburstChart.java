package com.finebi.plugin;

import com.finebi.foundation.api.web.component.AssembleComponentFactory;
import com.fr.decision.fun.impl.AbstractWebResourceProvider;
import com.fr.intelli.record.Focus;
import com.fr.intelli.record.Original;
import com.fr.record.analyzer.EnableMetrics;
import com.fr.web.struct.Atom;

@EnableMetrics
public class BIShowSunburstChart extends AbstractWebResourceProvider {

    @Override
    public Atom attach() {
		return AssembleComponentFactory.getShowComponent();
    }

    @Override
    @Focus(id = "com.finebi.plugin.BIShowSunburstChart", text = "旭日图", source = Original.PLUGIN)
    public Atom client() {
        return BISunburstChartComponent.KEY;
    }
}