package com.shiguo.user.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class JsonUtil {

    /**
     * 将json数据转成bean
     *
     * @author --ZhangJiQiang
     * @date 2014-12-3
     * @param jsonString
     * @param cls
     * @return T
     */
    public static <T> T getEntityFromJson(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 解析类型为List<Map<String,Object>>的对象
     *
     * @author --ZhangJiQiang
     * @date 2014-12-5
     * @param jsonString
     * @return List<Map<String,Object>>
     */
    public static List<Map<String, Object>> getListKeyMaps(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<Map<String, Object>>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static <T> List<T> getList(String json, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(json, new TypeToken<List<T>>() {
            }.getType());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 将json串转换成list对象
     *
     * @author --ZhangJiQiang
     * @date 2016-3-7
     * @param json
     * @param cls
     * @return ArrayList<T>
     */
    public static <T> ArrayList<T> fromJsonList(String json, Class<T> cls) {
        Gson gson = new Gson();
        ArrayList<T> mList = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();

        for (final JsonElement elem : array) {
            mList.add(gson.fromJson(elem, cls));
        }

        return mList;
    }

    public static String xml2JSON(String xml) {
        JSONObject obj = new JSONObject();
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(is);
            Element root = doc.getRootElement();
            String rootName = root.getName();
            obj.put(root.getName(), iterateElement(root));
            return obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Map iterateElement(Element element) {
        List jiedian = element.getChildren();
        Element et = null;
        Map obj = new HashMap();
        List list = null;
        for (int i = 0; i < jiedian.size(); i++) {
            list = new LinkedList();
            et = (Element) jiedian.get(i);
            if (et.getTextTrim().equals("")) {
                if (et.getChildren().size() == 0) {
                    continue;
                }
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(iterateElement(et));
                if (list.size() == 1) {
                    obj.put(et.getName(), list.get(0));
                } else {
                    obj.put(et.getName(), list);
                }
            } else {
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(et.getTextTrim());
                if (list.size() == 1) {
                    obj.put(et.getName(), list.get(0));
                } else {
                    obj.put(et.getName(), list);
                }
            }
        }
        return obj;
    }
}
