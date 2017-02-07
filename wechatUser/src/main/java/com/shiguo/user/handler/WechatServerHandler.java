/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.user.handler;

import java.io.UnsupportedEncodingException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author lenovo
 */
@Component
public class WechatServerHandler {
    
    public JSONObject getWechatAccessToken(){
        RestTemplate rt = new RestTemplate();
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx93bb5b9d65937271&secret=6b8ae25929fdac0488e1aa4391359af7";
        String accessToken = rt.getForObject(url, String.class);
        return JSONObject.fromObject(accessToken);
    }
    
    public JSONObject getWechatUserInfo(String openId,String access_token) throws UnsupportedEncodingException{
        RestTemplate rt = new RestTemplate();
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+openId+"&lang=zh_CN";
        String userInfo = rt.getForObject(url, String.class);
        userInfo = new String(userInfo.getBytes("ISO-8859-1"), "UTF-8");
        return JSONObject.fromObject(userInfo);
    }
    
}
