package com.fr.plugin.sunburst;

import com.fanruan.api.report.chart.field.BaseColumnFieldCollection;
import com.fr.chartx.data.annotations.KeyField;
import com.fr.chartx.data.field.ColumnField;

/**
 * @author Handonglin
 * @create 2022-12-08 18:51
 */
//1.定义图表数据的属性集合，
/* 继承自类型BaseColumnFieldCollection，对于作为分组汇总条件的字段加上注解@KeyField
  作用于选数据后，需要传入的值

  *
  */
public class SunburstColumnFieldCollection extends BaseColumnFieldCollection {
    @KeyField
    private ColumnField id = new ColumnField();
    private ColumnField fid = new ColumnField();
    private ColumnField name = new ColumnField();
    private ColumnField value = new ColumnField();

    public ColumnField getId() {
        return id;
    }

    public void setId(ColumnField id) {
        this.id = id;
    }

    public ColumnField getFid() {
        return fid;
    }

    public void setFid(ColumnField fid) {
        this.fid = fid;
    }

    public ColumnField getValue() {
        return value;
    }

    public void setValue(ColumnField value) {
        this.value = value;
    }

    public ColumnField getName() {
        return name;
    }

    public void setName(ColumnField name) {
        this.name = name;
    }
}
