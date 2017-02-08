/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.user.controller;

import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author lenovo
 */
@Controller
@RequestMapping("wapi/wechat")
public class WechatController {

    Logger logger = LoggerFactory.getLogger(WechatHandShakeController.class);
    private String webhost = "http://shiguo.bjstwh.com";
    private final String appId = "wx93bb5b9d65937271";
    private final String appSecret = "6b8ae25929fdac0488e1aa4391359af7";

    /**
     * 外卖点餐
     * @param request
     * @param response
     * @throws Exception 
     */
    @RequestMapping(value = "/homepage", method = RequestMethod.GET)
    @ResponseBody
    public void getHomePage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String redirect_uri = webhost + "/wapi/wechat/oauth?pageName=HomePage";
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
                + appId
                + "&redirect_uri="
                + URLEncoder.encode(redirect_uri, "gbk")
                + "&response_type=code&scope=snsapi_base#wechat_redirect";
        response.sendRedirect(url);
    }
    /**
     * 外卖订单
     * @param request
     * @param response
     * @throws Exception 
     */
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    @ResponseBody
    public void getMyOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String redirect_uri = webhost + "/wapi/wechat/oauth?pageName=OrderListPage";
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
                + appId
                + "&redirect_uri="
                + URLEncoder.encode(redirect_uri, "gbk")
                + "&response_type=code&scope=snsapi_base#wechat_redirect";
        response.sendRedirect(url);
    }
    /**
     * 会员中心
     * @param request
     * @param response
     * @throws Exception 
     */
    @RequestMapping(value = "/vip", method = RequestMethod.GET)
    @ResponseBody
    public void getMyVip(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String redirect_uri = webhost + "/wapi/wechat/oauth?pageName=VipPage";
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
                + appId
                + "&redirect_uri="
                + URLEncoder.encode(redirect_uri, "gbk")
                + "&response_type=code&scope=snsapi_base#wechat_redirect";
        response.sendRedirect(url);
    }
    
    

    @RequestMapping(value = "/oauth", method = RequestMethod.GET)
    @ResponseBody
    public void wechatOauth(
            @RequestParam(value = "pageName", required = false) String pageName,
            @RequestParam(value = "code", required = false) String code,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Debug at WechatController pageName is ["+pageName+"] code is ["+code+"]");
        String tokenResult = this.getWechatToken(code);
        JSONObject tokenObj = JSONObject.fromObject(tokenResult);
        String openid = tokenObj.has("openid")?tokenObj.getString("openid"):"";
        if(pageName != null && pageName.equals("HomePage")){
            String redirectUrl = webhost+"/SGWechatSys/takeOutOrder.html?openId="+openid;
            response.sendRedirect(redirectUrl);
        }else if(pageName != null && pageName.equals("OrderListPage")){
            String redirectUrl = webhost+"/SGWechatSys/orderList.html?openId="+openid;
            response.sendRedirect(redirectUrl);
        }else if(pageName != null && pageName.equals("VipPage")){
            String redirectUrl = webhost+"/SGWechatSys/memberCenter.html?openId="+openid;
            response.sendRedirect(redirectUrl);
        }
    }

    public String getWechatToken(String code) {
        RestTemplate template = new RestTemplate();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + appId + "&secret=" + appSecret + "&code=" + code
                + "&grant_type=authorization_code";
        return template.getForObject(url, String.class);
    }
}
