package com.fr.plugin.sunburst.util;

import com.fr.json.JSON;
import com.fr.json.JSONArray;
import com.fr.json.JSONFactory;
import com.fr.json.JSONObject;
import com.fr.plugin.sunburst.type.Node;

import java.util.List;

public class TurnToJsonUtils {
    //node节点转化为json
    public static JSONArray nodesToJson(List<Node> nodes) {
        if (nodes.isEmpty()) {
            return null;
        }

        JSONArray dataArray = JSONFactory.createJSON(JSON.ARRAY);
        for (Node node : nodes) {
            JSONObject perJsonData = JSONFactory.createJSON(JSON.OBJECT);
            //echarts官网的格式，name value children
            perJsonData.put("name", node.getName());
            perJsonData.put("value", node.getValue());
            perJsonData.put("children", nodesToJson(node.getChildren()));

            dataArray.put(perJsonData);
        }
        return dataArray;
    }

}
