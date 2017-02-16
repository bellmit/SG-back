/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.wechat.factory.WeChatRefundFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONObject;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author shixj
 */
public class ResponseHandler {
  /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     *
     * @param strxml
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    public static Map doXMLParse(String strxml) throws JDOMException, IOException {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        if (null == strxml || "".equals(strxml)) {
            return null;
        }
        Map m = new HashMap();
        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }
            m.put(k, v);
        }
        //关闭流
        in.close();
        return m;
    }

    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    /**
     * 获取xml编码字符集
     *
     * @param strxml
     * @return
     * @throws IOException
     * @throws JDOMException
     */
//	public static String getXMLEncoding(String strxml) throws JDOMException, IOException {
//		InputStream in = HttpClientUtil.String2Inputstream(strxml);
//		SAXBuilder builder = new SAXBuilder();
//		Document doc = builder.build(in);
//		in.close();
//		return (String)doc.getProperty("encoding");
//	}
    @SuppressWarnings("unchecked")
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

    public static void main(String args[]) {
//        try {
        String strxml = "<xml><test>1</test></xml>";
        strxml = "<MapSet>"
                + "<MapGroup id='Sheboygan'><test>" + "d</test></MapGroup>"
                + "<ddd>" + "33333333" + "</ddd>"
                + "</MapSet>";
//            Map test = ResponseHandler.doXMLParse(strxml);
////            Iterator iterator = test.iterator();
//            Set<Map.Entry<String, String>> entrySet=test.entrySet();  
//            for(Map.Entry<String, String> myEntrySet: entrySet)  
//            {  
//                System.out.println(myEntrySet.getKey()+"................."+myEntrySet.getValue());  
//            }  
//            XMLSerializer xmlSerializer = new XMLSerializer();  
//            JSON json = xmlSerializer.read(strxml);  
        System.out.println(strxml);
        System.out.println(ResponseHandler.xml2JSON(strxml));
//        } catch (JDOMException ex) {
//            Logger.getLogger(ResponseHandler.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ResponseHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public static Map alipayIterateElement(List<Element> list) {
        Map map = new HashMap();
        for (Element e : list) {
            if (e.getParentElement().getName().equals("request") && e.getName().equals("param")) {
                if (e.getChildren().size() > 0) {
                    map.put(e.getName(), alipayIterateElement(e.getChildren()));
                } else {
                    map.put(((Attribute) e.getAttributes().get(0)).getValue(), e.getValue());
                }
            } else {
                if (e.getChildren().size() > 0) {
                    map.put(e.getName(), alipayIterateElement(e.getChildren()));
                } else {
                    map.put(e.getName(), e.getValue());
                }
            }
        }
        return map;
    }

    public static Map alipayXmlToMap(String xml) {
        InputStream in = null;
        try {
            in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(in);
            Element root = document.getRootElement();
            List<Element> list = root.getChildren();
            return alipayIterateElement(list);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ResponseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDOMException ex) {
            Logger.getLogger(ResponseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ResponseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(ResponseHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}

