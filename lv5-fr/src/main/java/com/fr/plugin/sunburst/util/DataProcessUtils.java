package com.fr.plugin.sunburst.util;

import com.fr.general.ComparatorUtils;
import com.fr.json.JSONArray;
import com.fr.plugin.sunburst.SunburstColumnFieldCollection;
import com.fr.plugin.sunburst.type.Node;
import com.fr.stable.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DataProcessUtils {
    public static JSONArray makeJsonData(SunburstColumnFieldCollection collection){
        //这个当作纯粹的存放表数据的根节点，其child才是真正的root
        Node tmproots = new Node();
        List<Object> ids = collection.getId().getValues();
        List<Object> fids = collection.getFid().getValues();
        List<Object> names = collection.getName().getValues();
        List<Object> values = collection.getValue().getValues();
        int count = ids.size();
        List<Node> nodes = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            Node node = new Node(ids.get(i), fids.get(i),names.get(i), values.get(i));
            nodes.add(node);
        }

        //没有fid的为根节点
        for (Node node : nodes) {
            String parent = (String) node.getFid();
            if (ComparatorUtils.equals(StringUtils.EMPTY, parent)) {
                tmproots.addChildren(node);
            }
        }

        //以root为根，遍历所有的节点，以root为第一父节点的节点作为root的子节点
        for (Node root : tmproots.getChildren()) {
            getChildNodes(nodes, root);
        }

        JSONArray data = TurnToJsonUtils.nodesToJson(tmproots.getChildren());

        return data;

    }
    private static void getChildNodes(List<Node> nodes, Node root) {
        for (Node node : nodes) {
            String parent = (String) node.getFid();
            if (parent != null && ComparatorUtils.equals(parent, root.getId())) {
                root.addChildren(node);
            }
        }
        //将root的子节点
        // 的子节点也构建出来
        for (Node node : root.getChildren()) {
            getChildNodes(nodes, node);
        }
    }
}
