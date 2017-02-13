package com.shiguo.user.controller;

import com.shiguo.user.util.MapUtil;
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
    
}
