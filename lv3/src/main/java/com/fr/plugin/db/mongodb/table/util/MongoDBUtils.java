package com.fr.plugin.db.mongodb.table.util;

import com.fanruan.api.util.ArrayKit;
import com.fanruan.api.util.GeneralKit;
import com.fanruan.api.util.RenderKit;
import com.fanruan.api.util.StringKit;
import com.fr.stable.ParameterProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Handonglin
 * @version 10.0
 * Created by Handonglin on 2022-12-11
 */
public class MongoDBUtils {



    public static String database;
    public static String calculateQuery(String query, ParameterProvider[] ps) {
        if (ArrayKit.isEmpty(ps)) {
            return query;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        for (ParameterProvider p : ps) {
            map.put(p.getName(), p.getValue());
        }
        try {
            return RenderKit.renderParameter4Tpl(query, map);
        } catch (Exception e) {
            return query;
        }
    }

//    public static void registerJavaMethods(V8Object v8Object, Object target) {
//        Method[] methods = target.getClass().getMethods();
//        for (Method m : methods) {
//            if (m.getAnnotation(ScriptBridge.class) != null) {
//                v8Object.registerJavaMethod(target, m.getName(), m.getName(), m.getParameterTypes());
//            }
//        }
//    }

    public static String getName(Object target) {
        if (target == null) {
            return StringKit.EMPTY;
        }
        try {
            Method method = target.getClass().getMethod("getName");
            return GeneralKit.objectToString(method.invoke(target));
        } catch (NoSuchMethodException e) {
            return StringKit.EMPTY;
        } catch (IllegalAccessException e) {
            return StringKit.EMPTY;
        } catch (InvocationTargetException e) {
            return StringKit.EMPTY;
        }
    }

}
