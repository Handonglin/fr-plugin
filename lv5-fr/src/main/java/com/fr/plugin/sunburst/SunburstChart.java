package com.fr.plugin.sunburst;

import com.fanruan.api.cal.FormulaKit;
import com.fanruan.api.log.LogKit;
import com.fanruan.api.report.chart.BaseChartWithData;
import com.fanruan.api.script.FineCanvas;
import com.fanruan.api.util.AssistKit;
import com.fanruan.api.util.IOKit;
import com.fanruan.api.util.StringKit;
import com.fr.base.BaseFormula;
import com.fr.base.chart.cross.FormulaProcessor;
import com.fr.chart.ChartWebParaProvider;
import com.fr.extended.chart.HyperLinkPara;
import com.fr.json.JSON;
import com.fr.json.JSONFactory;
import com.fr.json.JSONObject;
import com.fr.plugin.sunburst.type.SunburstType;
import com.fr.plugin.sunburst.util.*;
import com.fr.plugin.transform.FunctionRecorder;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;

import java.awt.*;
import java.awt.image.BufferedImage;



/**
 * @author Handonglin
 * @create 2022-12-08 18:44
 */
//2.定义图表属性类，继承BaseChartWithData，实现对应的方法。
//
//并且这个类型要加上插件记录点功能注解@FunctionRecorder
@FunctionRecorder
public class SunburstChart extends BaseChartWithData {

    private static final String ID = "SUNBURST_CHART";
    private BaseFormula titleFormula = FormulaKit.newFormula(StringKit.EMPTY);
    private SunburstType sunburstType = SunburstType.getType();
    private String legendPosition = "left";


    public SunburstType getSunburstType() {
        return sunburstType;
    }

    public void setSunburstType(SunburstType sunburstType) {
        this.sunburstType = sunburstType;
    }

    public BaseFormula getTitleFormula() {
        return titleFormula;
    }

    public void setTitleFormula(BaseFormula titleFormula) {
        this.titleFormula = titleFormula;
    }

    public String getLegendPosition() {
        return legendPosition;
    }

    public void setLegendPosition(String legendPosition) {
        this.legendPosition = legendPosition;
    }

    /*
     * 通过判断图表的子类型在设计器中展示不同的图片,
     * 只有一个simple图。。。
     */
    @Override
    protected Image designImage(int width, int height, int resolution, ChartWebParaProvider chartWebPara) {
        return IOKit.readImageWithCache("com/fr/plugin/images/icon.png");
    }

    /*
     * 通过使用辅助类FineCanvas导出图表
     */
    @Override
    protected Image exportImage(int width, int height, int resolution, ChartWebParaProvider chartWebPara) {
        BufferedImage bufferedImage = new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);
        try {
            FineCanvas canvas = new FineCanvas(
                    "/com/fr/plugin/sunburst/echarts-adapter.js",
                    "/com/fr/plugin/sunburst/echarts.js");
            canvas.loadText("canvas.height = " + height, "canvas.width = " + width);
            canvas.loadText("var myChart = echarts.init(canvas)");
            canvas.loadText("option = " + createAttributeConfig(chartWebPara).toString());
            canvas.loadText("myChart.setOption(option);");
            bufferedImage = canvas.paint();
        } catch (Exception ex) {
            LogKit.error(ex.getMessage(), ex);
        }
        return bufferedImage;
    }

    /*
     * 创建Echarts饼图需要的options，根据自身的属性来定义options中的配置项
     * 调用前，已经对FieldCollection中的字段做了对应的汇总计算，可以直接通过getFieldCollection获取到字段集合，从而获取其中的计算结果
     * @param chartWebPara
     * @return JSONObject
     */
    @Override
    public JSONObject createAttributeConfig(ChartWebParaProvider chartWebPara) {
        JSONObject jsonObject = super.createAttributeConfig(chartWebPara);
        jsonObject.put("title", JSONFactory.createJSON(JSON.OBJECT).put("text", getTitleFormula().getResult()).put("x", "center"));
        jsonObject.put("tooltip", JSONFactory.createJSON(JSON.OBJECT).put("trigger", "item").put("formatter", "{b}: {c} ({d}%)"));

        //从数据源得到所需的三个字段 id fid value
        SunburstColumnFieldCollection columnFieldCollection = getFieldCollection(SunburstColumnFieldCollection.class);

        JSONObject series = SeriesUtils.createSeries(columnFieldCollection);

        jsonObject.put("series", series);
        return jsonObject;
    }


    /*
     * 处理对象的公式类型的属性，这里处理了图表标题
     * @param formulaProcessor
     */
    @Override
    public void dealFormula(FormulaProcessor formulaProcessor) {
        if (titleFormula != null) {
            formulaProcessor.dealWith(titleFormula);
        }
        super.dealFormula(formulaProcessor);
    }

    @Override
    public String getID() {
        return ID;
    }

    /*
     * 从xml中读取对象的属性
     * @param xmLableReader
     */
    @Override
    public void readAttr(XMLableReader xmLableReader) {
        super.readAttr(xmLableReader);

        setSunburstType(SunburstType.getType());
        setTitleFormula(FormulaKit.newFormula(xmLableReader.getAttrAsString("title", "新建标题")));
        setLegendPosition(xmLableReader.getAttrAsString("legendPosition", StringKit.EMPTY));
    }

    /*
     * 将对象的属性写入xml中
     * @param xmlPrintWriter
     */
    @Override
    public void writeAttr(XMLPrintWriter xmlPrintWriter) {
        super.writeAttr(xmlPrintWriter);
        xmlPrintWriter.attr("sunburstType", 0)
                .attr("title", titleFormula.toString())
                .attr("legendPosition", legendPosition);
    }

    private static final HyperLinkPara CATEGORY = new HyperLinkPara() {
        @Override
        public String getName() {
            return "分类";
        }

        @Override
        public String getFormulaContent() {
            return "Category";
        }

        @Override
        public String[] getProps() {
            return new String[]{"data", "name"};
        }
    };

    @Override
    protected HyperLinkPara[] hyperLinkParas() {
        return new HyperLinkPara[]{
                CATEGORY,
        };
    }
    @Override
    public SunburstChart clone() throws CloneNotSupportedException {
        SunburstChart result = (SunburstChart) super.clone();
        if (getTitleFormula() != null) {
            result.setTitleFormula(this.getTitleFormula().clone());
        }
        result.setSunburstType(this.getSunburstType());
        return result;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + AssistKit.hashCode(this.getTitleFormula(), this.getSunburstType());
    }

    @Override
    public boolean equals(Object ob) {
        return super.equals(ob)
                && ob instanceof SunburstChart
                && AssistKit.equals(this.getTitleFormula(), ((SunburstChart) ob).getTitleFormula())
                && AssistKit.equals(this.getSunburstType(), ((SunburstChart) ob).getSunburstType())
                && AssistKit.equals(this.getLegendPosition(), ((SunburstChart) ob).getLegendPosition());
    }
}