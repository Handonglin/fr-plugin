package com.fr.plugin.db.mongodb.table.util;

import com.fr.plugin.db.mongodb.table.data.DataWarp;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.*;

public class MongoDBDataDealUtils {
    /*col3=value3
     * col2=value2
     * col1=value1,
     *
     * col1   col2    col3
     *value1  value2  value3
     * col1=[value1, value2, value3], col2=[value11, value22, value33]
     * */

    public static DataWarp<Object> getDataWarpper(MongoCursor<Document> results) {
        Set<String> columNames = new TreeSet<>();
        List<List<Object>> colDatas = new ArrayList<>();
        Class<?> valueClass = null;
        while (results.hasNext()) {//行其实就一行
            Document document = results.next();
            //col2=[value11, value22, value33]
            //col1=[value1, value2, value3],
            //key   -  value
            Set<Map.Entry<String, Object>> entries = document.entrySet();

            List<Object> eachRow = new ArrayList<>();
            //对每一个key-value,每一个key是一个列名，value是这一列的值
            Iterator<Map.Entry<String, Object>> entryIterator = entries.iterator();
            while (entryIterator.hasNext()) {

                Map.Entry<String, Object> next1 = entryIterator.next();
                String key = next1.getKey();
                Object value = next1.getValue();
                if (key.equals("_id")) {
                    continue;
                }
                valueClass = value.getClass();
                columNames.add(key);
                if (value instanceof ArrayList) {
                    colDatas.add((List<Object>) value);
                } else {
                    eachRow.add(value);
                }
            }
            if (valueClass == String.class) {
                colDatas.add(eachRow);
            }
        }
        String[] colNames = new String[columNames.size()];
        columNames.toArray(colNames);

        DataWarp<Object> dataWarp = new DataWarp<>();
        dataWarp.setColumnNames(colNames);
        if (valueClass == String.class) {
            dataWarp.setData(colToRowSingle(colDatas));
        } else {
            dataWarp.setData(colToRowCollection(colDatas));
        }

        return dataWarp;


    }

    //[[v1,v2,v3]]->[[v1],[v2],[v3]]
    private static List colToRowSingle(List<List<Object>> colList) {
        int newrow = colList.size();//1
        int newcol = colList.get(0).size();//3
        List<List<Object>> ret = new ArrayList<>();
        for (int i = 0; i < newcol; i++) {
            ret.add(new ArrayList<>(newcol));
        }

        for (int i = 0; i < newrow; i++) {
            List<Object> oldrow = colList.get(i);
            for (int j = 0; j < newcol; j++) {
                List<Object> strings = ret.get(j);
                strings.add(oldrow.get(j));
            }
        }
        return ret;

    }


    //[[v1,v2,v3],[v11,v22,v33]]->[[v1,v11],[v2,v22],[v3,v33]]
    private static List colToRowCollection(List<List<Object>> realList) {
        int oldrow = realList.size();
        List<List<Object>> curList = new ArrayList<>();

        for (int i = 0; i < oldrow; i++) {
            curList.add(realList.get(i));
        }
        //System.out.println("realList"+realList);
        //System.out.println("curList"+curList);
        return curList;
    }


}
