/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.inhand.common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.omg.CORBA.portable.ResponseHandler;

/**
 *
 * @author lenovo
 */
public class GenerateMap {

    public static Map xmlToMap(String xml) {
        InputStream in = null;
        try {
            in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(in);
            Element root = document.getRootElement();
            List<Element> list = root.getChildren();
            return iterateElement(list);
        }  catch (Exception ex) {
            Logger.getLogger(ResponseHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(ResponseHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static Map iterateElement(List<Element> list) {
        Map map = new HashMap();
        for (Element e : list) {
            if (e.getParentElement().getName().equals("request") && e.getName().equals("param")) {
                if (e.getChildren().size() > 0) {
                    map.put(e.getName(), iterateElement(e.getChildren()));
                } else {
                    map.put(((Attribute) e.getAttributes().get(0)).getValue(), e.getValue());
                }
            } else {
                if (e.getChildren().size() > 0) {
                    map.put(e.getName(), iterateElement(e.getChildren()));
                } else {
                    map.put(e.getName(), e.getValue());
                }
            }
        }
        return map;
    }
}
