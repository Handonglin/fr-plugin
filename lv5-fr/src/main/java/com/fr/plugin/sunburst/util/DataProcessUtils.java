package com.fr.plugin.sunburst.util;

import com.fr.general.ComparatorUtils;
import com.fr.json.JSONArray;
import com.fr.plugin.sunburst.SunburstColumnFieldCollection;
import com.fr.plugin.sunburst.type.Node;
import com.fr.stable.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProcessUtils {
    public static JSONArray makeJsonData(SunburstColumnFieldCollection collection){
        //这个当作纯粹的存放表数据的根节点，其child才是真正的root
        List<Object> ids = collection.getId().getValues();
        List<Object> fids = collection.getFid().getValues();
        List<Object> names = collection.getName().getValues();
        List<Object> values = collection.getValue().getValues();
        int count = ids.size();

        List<Node> nodes = new ArrayList<>(count);
        HashMap<String,Node> idnodeMap=new HashMap<>();
        List<Node> roots=new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Node node = new Node(ids.get(i), fids.get(i),names.get(i), values.get(i));
            //先设置一个空的孩子集合
            node.setChildren(new ArrayList<>());
            nodes.add(node);
            idnodeMap.put((String) node.getId(),node);
            //fid为空,说明这个节点是root
            if (ComparatorUtils.equals(StringUtils.EMPTY, node.getFid())) {
                roots.add(node);
            }
        }

        getChildNodes(nodes,idnodeMap);

        return TurnToJsonUtils.nodesToJson(roots);

    }
    private static void getChildNodes(List<Node> nodes, Map<String,Node> idnodeMap) {
        for(Node node:nodes){
            //有父节点，则将node加入fid的children中
            if (!ComparatorUtils.equals(StringUtils.EMPTY, node.getFid())){
                String fid = (String) node.getFid();
                idnodeMap.get(fid).getChildren().add(node);
            }
        }
    }
}
