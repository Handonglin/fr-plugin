package com.fr.plugin.sunburst.util;


import com.fr.json.JSON;
import com.fr.json.JSONArray;
import com.fr.json.JSONFactory;
import com.fr.json.JSONObject;
import com.fr.plugin.sunburst.SunburstColumnFieldCollection;

/*
 * 数据处理工具类
 * @author Handonglin
 * @create 2022-12-08 10:17
 */
public class SeriesUtils {

    public static JSONObject createSeries(SunburstColumnFieldCollection collection) {
        JSONObject series = JSONFactory.createJSON(JSON.OBJECT);

        //处理表中的data数据
        JSONArray data = DataProcessUtils.makeJsonData(collection);

        //圆角旭日图属性配置,直接这么写吧,但不够优雅。。。有时间再看看要不要写到配置文件里
        //radius数组
        JSONArray radius = JSONFactory.createJSON(JSON.ARRAY);
        radius.add(0);
        radius.add("90");
        //itemStyle 对象
        JSONObject itemStyle = JSONFactory.createJSON(JSON.OBJECT);
        itemStyle.put("borderRadius",7);
        itemStyle.put("borderWidth",2);

        //汇总，依次将这几个都写入series，和官网一样的顺序
        series.put("type", "sunburst");
        series.put("data", data);
        series.put("radius", radius);
        series.put("itemStyle",itemStyle);

        return series;
    }
}
