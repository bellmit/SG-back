/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.wechat.factory.WeChatRefundFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import net.sf.json.JSONObject;

/**
 *
 * @author shixj
 */
public class JSONUtil {
    public static Object unserializedJson(String json,Class beanClass){
            JSONObject object = JSONObject.fromObject(json);
            return JSONObject.toBean(object, beanClass);
    }

    public static Object unserialized(String json, Class beanClass, Map map)
                    throws Exception {
            JSONObject jsonObject = JSONObject.fromObject(json);
            return JSONObject.toBean(jsonObject, beanClass, map);
    }
    public static String serialized(Object obj) {
            JSONObject jsonObject = JSONObject.fromObject(obj);
            String json = jsonObject.toString();
            jsonObject.clear();
            return json;
    }
    
    public static void main(String args[]){
         SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String createTime = format.format(new Date());
        System.out.println(createTime);
    }
}

