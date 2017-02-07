/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.user.controller;

import cn.com.inhand.common.exception.HandleExceptionController;
import com.shiguo.user.entity.WXUser;
import com.shiguo.user.handler.WechatServerHandler;
import com.shiguo.user.service.WXUserService;
import com.shiguo.user.util.MessageUtil;
import com.shiguo.user.util.SignUtil;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author lenovo
 */
@Controller
@RequestMapping("wapi/wechat_handshake")
public class WechatHandShakeController extends HandleExceptionController {

    Logger logger = LoggerFactory.getLogger(WechatHandShakeController.class);
    @Autowired
    private WechatServerHandler wechatHandler;
    @Autowired
    private WXUserService wuservice;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public void handShake(
            HttpServletRequest request,
            HttpServletResponse response) {

        try {
            // 微信加密签名
            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 随机字符串
            String echostr = request.getParameter("echostr");
            String token = "gLYqh1yYwMkmVeFqTFL0Errn60Yhf6kc";

            PrintWriter out = response.getWriter();
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            SignUtil su = new SignUtil();
            if (su.checkSignature(signature, timestamp, nonce, token)) {
                out.print(echostr);
            }
            out.close();
            out = null;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public final void handlerWechatConfigInfo(
            HttpServletRequest request,
            HttpServletResponse response) {

        PrintWriter out = null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            Map<String, String> requestMap = MessageUtil.parseXml(request);
            logger.info("requestMap toString " + requestMap.toString());
            if (requestMap.get("Event").equals("subscribe")) {
                String openId = requestMap.get("FromUserName");
                logger.info("Debug Wechat HandShanke Controller openId is [" + openId + "]");

                Map<String, String> parames = new HashMap<String, String>();
                parames.put("openId", openId);
                WXUser user = wuservice.findUniqueByParams(parames);
                if (user == null) {
                    JSONObject token = wechatHandler.getWechatAccessToken();
                    if (token.has("access_token")) {
                        String accessToken = token.getString("access_token");
                        JSONObject userInfo = wechatHandler.getWechatUserInfo(openId, accessToken);
                        logger.info("Debug wechat user info " + userInfo.toString());
                        if (!userInfo.has("errcode")) {
                            user = new WXUser();
                            user.setOpenId(openId);
                            user.setNickName(userInfo.getString("nickname"));
                            user.setImage(userInfo.getString("headimgurl"));
                            wuservice.save(user);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
