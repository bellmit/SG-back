package com.shiguo.user.controller;

import com.shiguo.user.util.MapUtil;
import java.awt.geom.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("wapi/map")
public class BaiduMapController {

    private final static Logger logger = LoggerFactory.getLogger(BaiduMapController.class);

    @Autowired
    RestTemplate restTemplate;
    
    @RequestMapping(value = "/IsPtInPoly", method = RequestMethod.GET)
    public @ResponseBody
    boolean IsPtInPoly(
            @RequestParam(value = "lng", required = true) double lng,
            @RequestParam(value = "lat", required = true) double lat) throws IOException {
         
         List<Point2D.Double> pts = new ArrayList<Point2D.Double>();
         pts.add(new Point2D.Double(116.360338,40.105302));//龙禧医院
         pts.add(new Point2D.Double(116.333172,40.096798));//北京昌平向上学校
         pts.add(new Point2D.Double(116.318227,40.086762));//佰嘉城小区
         pts.add(new Point2D.Double(116.354082,40.07282));//龙旗购物中心
         pts.add(new Point2D.Double(116.362961,40.078128));//龙跃苑东五区
         pts.add(new Point2D.Double(116.396583,40.091608));//回南家园
         pts.add(new Point2D.Double(116.385082,40.104757));//平西王府总站
         
         Point2D.Double point = new Point2D.Double(lng, lat);
         
         boolean result  = IsPtInPoly(point, pts);
         return result;
    }
    
    
    @RequestMapping(value = "/baidu/getLocation", method = RequestMethod.GET)
    public @ResponseBody
    Object getPlace(
            @RequestParam(value = "address", required = true) String address) throws IOException {
        String url="http://api.map.baidu.com/geocoder?address="+address+"&output=json&key=5rCA4tslqZE5Ip5ew5pudaSb&city=北京市";
        String result = restTemplate.getForObject(url, String.class);
        return result;
    }
    
    @RequestMapping(value = "/google/place/queryautocomplete", method = RequestMethod.GET)
    public @ResponseBody
    Object getGooglePlace(
            @RequestParam(value = "inputValue", required = true) String inputValue) throws IOException {
        inputValue = inputValue.replace(" ", "");
        String url = "https://maps.googleapis.com/maps/api/place/queryautocomplete/json?input=" + inputValue + "&key=AIzaSyCgq6NoiFjJuYl6fdHs527LUdxMM-A5clw&sensor=false&radius=1000";
        DefaultHttpClient client = new DefaultHttpClient();
        MapUtil.enableSSL(client);
        HttpGet get = new HttpGet(url);
        StringBuffer sb = new StringBuffer();
        try {
            HttpResponse res = client.execute(get);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                byte[] data = EntityUtils.toByteArray(res.getEntity());
                // InputStream is = new FileInputStream(new
                // File("D:\\report.xml"));
                InputStream is = new ByteArrayInputStream(data);
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");// 设置读取文件的流为UTF-8的形式
                // 为了提高效率，将字符串进行缓冲区技术高效操作，使用BufferedReader。
                BufferedReader br = new BufferedReader(isr);
                String s = null;
                while ((s = br.readLine()) != null) {
                    sb.append(s);
                }
                is.close();
                isr.close();

            }
        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            // 关闭连接 ,释放资源
            client.getConnectionManager().shutdown();
        }
        return sb.toString();
    }
    
    @RequestMapping(value = "/google/geocode", method = RequestMethod.GET)
    public @ResponseBody
    Object getGoogleGecode(
            @RequestParam(value = "address", required = true) String address) throws IOException {
        address = address.replace(" ", "");
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+address;
        DefaultHttpClient client = new DefaultHttpClient();
        MapUtil.enableSSL(client);
        HttpGet get = new HttpGet(url);
        StringBuffer sb = new StringBuffer();
        try {
            HttpResponse res = client.execute(get);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                byte[] data = EntityUtils.toByteArray(res.getEntity());
                // InputStream is = new FileInputStream(new
                // File("D:\\report.xml"));
                InputStream is = new ByteArrayInputStream(data);
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");// 设置读取文件的流为UTF-8的形式
                // 为了提高效率，将字符串进行缓冲区技术高效操作，使用BufferedReader。
                BufferedReader br = new BufferedReader(isr);
                String s = null;
                while ((s = br.readLine()) != null) {
                    sb.append(s);
                }
                is.close();
                isr.close();

            }
        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            // 关闭连接 ,释放资源
            client.getConnectionManager().shutdown();
        }
        return sb.toString();
    }
    
    public  void main(String[] args) throws IOException {
    
    Point2D.Double point = new Point2D.Double(116.404072, 39.916605);
    
    List<Point2D.Double> pts = new ArrayList<Point2D.Double>();
    pts.add(new Point2D.Double(116.360338,40.105302));//龙禧医院
    pts.add(new Point2D.Double(116.333172,40.096798));//北京昌平向上学校
    pts.add(new Point2D.Double(116.318227,40.086762));//佰嘉城小区
    pts.add(new Point2D.Double(116.354082,40.07282));//龙旗购物中心
    pts.add(new Point2D.Double(116.362961,40.078128));//龙跃苑东五区
    pts.add(new Point2D.Double(116.396583,40.091608));//回南家园
    pts.add(new Point2D.Double(116.385082,40.104757));//平西王府总站
   /* boolean result  = IsPtInPoly(point, pts);
    if(result){
        System.out.println("点在多边形内");
    }else{
        System.out.println("点在多边形外");
    }*/
}
public static boolean IsPtInPoly(Point2D.Double point, List<Point2D.Double> pts){
    
    int N = pts.size();
    boolean boundOrVertex = true; //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
    int intersectCount = 0;//cross points count of x 
    double precision = 2e-10; //浮点类型计算时候与0比较时候的容差
    Point2D.Double p1, p2;//neighbour bound vertices
    Point2D.Double p = point; //当前点
    
    p1 = pts.get(0);//left vertex        
    for(int i = 1; i <= N; ++i){//check all rays            
        if(p.equals(p1)){
            return boundOrVertex;//p is an vertex
        }
        
        p2 = pts.get(i % N);//right vertex            
        if(p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)){//ray is outside of our interests                
            p1 = p2; 
            continue;//next ray left point
        }
        
        if(p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)){//ray is crossing over by the algorithm (common part of)
            if(p.y <= Math.max(p1.y, p2.y)){//x is before of ray                    
                if(p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)){//overlies on a horizontal ray
                    return boundOrVertex;
                }
                
                if(p1.y == p2.y){//ray is vertical                        
                    if(p1.y == p.y){//overlies on a vertical ray
                        return boundOrVertex;
                    }else{//before ray
                        ++intersectCount;
                    } 
                }else{//cross point on the left side                        
                    double xinters = (p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;//cross point of y                        
                    if(Math.abs(p.y - xinters) < precision){//overlies on a ray
                        return boundOrVertex;
                    }
                    
                    if(p.y < xinters){//before ray
                        ++intersectCount;
                    } 
                }
            }
        }else{//special case when ray is crossing through the vertex                
            if(p.x == p2.x && p.y <= p2.y){//p crossing over p2                    
                Point2D.Double p3 = pts.get((i+1) % N); //next vertex                    
                if(p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)){//p.x lies between p1.x & p3.x
                    ++intersectCount;
                }else{
                    intersectCount += 2;
                }
            }
        }            
        p1 = p2;//next ray left point
    }
    
    if(intersectCount % 2 == 0){//偶数在多边形外
        return false;
    } else { //奇数在多边形内
        return true;
    }
    
}
}
