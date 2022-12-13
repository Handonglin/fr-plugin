package com.fr.plugin.sunburst.util;

import com.fr.json.JSONArray;
import com.fr.json.JSONFactory;
import com.fr.plugin.sunburst.type.Node;

import java.util.List;

public class TurnToJsonUtils {
    //node节点转化为json
    public static JSONArray nodesToJson(List<Node> nodes) {

        JSONArray dataArray = JSONFactory.createJSON(nodes);

        return dataArray;
    }

}
