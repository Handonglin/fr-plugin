package com.fr.plugin.sunburst.type;

import com.fanruan.api.report.chart.BaseChartType;
import com.fanruan.api.report.chart.BaseChartWithData;
import com.fr.plugin.sunburst.SunburstChart;

/*这个改动很少
 * 4.定义图表类型
 * @author Handonglin
 * @create 2022-12-08 20:23
 */
public class ChartType extends BaseChartType {

    /**
     * 定义的所有图表子类型的图表属性对象
     * @return
     */
    @Override
    public BaseChartWithData[] getChartTypes() {
        return new BaseChartWithData[]{
                createSunburstChart(SunburstType.getType()),
        };
    }

    /**
     * 图表在web端展现时需要的JS文件，依赖echarts.js和demoWrapper.js
     * @return
     */
    @Override
    public String[] getRequiredJS() {
        return new String[]{
                "com/fr/plugin/sunburst/demoWrapper.js",
                "com/fr/plugin/sunburst/echarts.js"
        };
    }

    /**
     * 图表在web端展现时需要的CSS文件，这里依赖一个demo.css文件
     * @return
     */
    @Override
    public String[] getRequiredCss() {
        return new String[]{
                "com/fr/plugin/sunburst/demo.css"
        };
    }

    /**
     * JS对象名，这里是定义在demoWrapper.js中的对象
     * @return
     */
    @Override
    public String getWrapperName() {
        return "demoWrapper";
    }


    private SunburstChart createSunburstChart(SunburstType sunburstType) {
        SunburstChart sunburstChart = new SunburstChart();
        sunburstChart.setSunburstType(sunburstType);
        return sunburstChart;
    }

}
