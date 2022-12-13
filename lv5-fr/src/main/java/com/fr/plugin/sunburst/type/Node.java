package com.fr.plugin.sunburst.type;


import com.fr.stable.AssistUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Handonglin
 * @create 2022-12-08 18:57
 */
public class Node {
    private Object id;
    private Object fid;
    private Object name;
    private Object value;
    private List<Node> children = new LinkedList<>();

    public Node() {
    }

    public Node(Object id, Object fid,Object name, Object value) {
        this.id = id;
        this.fid = fid;
        this.name=name;
        this.value = value;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getFid() {
        return fid;
    }

    public void setFid(Object fid) {
        this.fid = fid;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }



    @Override
    public boolean equals(Object obj) {
        return obj instanceof Node
                && AssistUtils.equals(this.id, ((Node)obj).getId())
                && AssistUtils.equals(this.fid, ((Node)obj).getFid())
                && AssistUtils.equals(this.name, ((Node) obj).name)
                && AssistUtils.equals(this.value, ((Node) obj).getValue());
    }

    @Override
    public int hashCode() {
        return AssistUtils.hashCode(getId(), getFid(), getName(), getValue(), getChildren());
    }

    @Override
    public String toString() {
        return "{" +
                "name:" + name +
                ", value:" + value +
                ", children:" + children +
                '}';
    }
}
