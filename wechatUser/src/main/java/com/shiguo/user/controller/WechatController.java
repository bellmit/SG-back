/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.user.controller;

import cn.com.inhand.common.util.DateUtils;
import com.shiguo.order.entity.OrderStatistics;
import com.shiguo.order.entity.OrderStatisticsM;
import com.shiguo.order.entity.OrderStatisticsY;
import com.shiguo.order.entity.Orders;
import com.shiguo.order.service.OrderStatisticsMService;
import com.shiguo.order.service.OrderStatisticsService;
import com.shiguo.order.service.OrderStatisticsYService;
import com.shiguo.order.service.OrdersService;
import com.shiguo.user.entity.WXUser;
import com.shiguo.user.service.WXUserService;
import com.shiguo.user.util.JsonUtil;
import com.shiguo.user.util.RequestHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

    Logger logger = LoggerFactory.getLogger(WechatController.class);
    private String webhost = "http://www.shizhiguodu.com";
    private final String appId = "wx93bb5b9d65937271";
    private final String appSecret = "6b8ae25929fdac0488e1aa4391359af7";
    private final String mchid = "1438038502";
    private final String clientSecret = "s3M81qfqoPPMCHDgnRuP7dOZI2eRjBjE";
    private String payBackUrl = "http://www.shizhiguodu.com/wapi/wechat/payback";

    @Autowired
    private OrdersService orderService;
   
    @Autowired
    private WXUserService userService;
   
    @Autowired
    private OrderStatisticsService orderStatisticsService;
    @Autowired
    private OrderStatisticsMService orderMService;
    @Autowired
    private OrderStatisticsYService orderYService;
    
    
    /**
     * 外卖点餐
     *
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
     *
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
     *
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
        logger.info("Debug at WechatController pageName is [" + pageName + "] code is [" + code + "]");
        String tokenResult = this.getWechatToken(code);
        JSONObject tokenObj = JSONObject.fromObject(tokenResult);
        String openid = tokenObj.has("openid") ? tokenObj.getString("openid") : "";
        if (pageName != null && pageName.equals("HomePage")) {
            String redirectUrl = webhost + "/SGWechatSys/takeOutOrder.html?openId=" + openid;
            response.sendRedirect(redirectUrl);
        } else if (pageName != null && pageName.equals("OrderListPage")) {
            String redirectUrl = webhost + "/SGWechatSys/orderList.html?openId=" + openid;
            response.sendRedirect(redirectUrl);
        } else if (pageName != null && pageName.equals("VipPage")) {
            String redirectUrl = webhost + "/SGWechatSys/memberCenter.html?openId=" + openid;
            response.sendRedirect(redirectUrl);
        }
    }

    @RequestMapping(value = "/wechatSign", method = RequestMethod.GET)
    @ResponseBody
    public Object getPrePayId(@RequestParam(value = "appId", required = true) String appid,
            @RequestParam(value = "timeStamp", required = true) String timeStamp,
            @RequestParam(value = "nonceStr", required = true) String nonceStr,
            @RequestParam(value = "packageName", required = true) String packageName,
            HttpServletRequest request, HttpServletResponse response) {

        List params = new ArrayList<String>();
        params.add("appId=" + appId);
        params.add("timeStamp=" + timeStamp);
        params.add("nonceStr=" + nonceStr);
        params.add("package=" + packageName);
        params.add("signType=" + "MD5");
        System.out.println(params.toString());

        RequestHandler requestHandler = new RequestHandler(request, response);
        requestHandler.setParameter("appId", appId);
        requestHandler.setParameter("timeStamp", timeStamp);
        requestHandler.setParameter("nonceStr", nonceStr);
        requestHandler.setParameter("package", packageName);
        requestHandler.setParameter("signType", "MD5");
        String sign = requestHandler.createSign(clientSecret);
        Map map = new HashMap();
        map.put("paySign", sign);
        return map;
    }

    @RequestMapping(value = "/prePayId", method = RequestMethod.GET)
    @ResponseBody
    public Object getPrePayId(
            @RequestParam(value = "orderNo", required = true) String orderNo,
            @RequestParam(value = "openId", required = true) String openId,
            @RequestParam(value = "price", required = true) String price,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String prePayResult = this.getPrePayId(request, response, orderNo, openId, price);
        String prePayJson = JsonUtil.xml2JSON(prePayResult);
        logger.info(prePayJson);
        JSONObject preObj = JSONObject.fromObject(prePayJson);
        Map<String, String> resultMap = new HashMap<String, String>();
        if (preObj.getJSONObject("xml").has("prepay_id")) {
            String prePayId = JSONObject.fromObject(prePayJson).getJSONObject("xml").getString("prepay_id");   //prePayId="+prePayId+"&appId="+appid+"&&nonce_str="+getRandomString(10)+"&orderNo="+orderNo+"&showwxpaytitle=1
            resultMap.put("result", "SUCCESS");
            resultMap.put("appId", appId);
            resultMap.put("prePayId", prePayId);
            resultMap.put("nonce_str", getRandomString(10));
        } else {
            String result_msg = JSONObject.fromObject(prePayJson).getJSONObject("xml").getString("return_msg");
            resultMap.put("result", "FAIL");
            resultMap.put("msg", result_msg);
            System.out.println("prePay result Fail ......");
        }
        return resultMap;
    }

    @RequestMapping(value = "/payback", method = RequestMethod.POST)
    @ResponseBody
    public void payback(
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        java.io.BufferedReader bis = new java.io.BufferedReader(new java.io.InputStreamReader(request.getInputStream()));
        String line = null;
        String result = "";
        while ((line = bis.readLine()) != null) {
            result += line + "\r\n";
        }

        String payBackResult = JsonUtil.xml2JSON(result);
        logger.info(payBackResult);
        JSONObject object = JSONObject.fromObject(payBackResult);

        if (object.getJSONObject("xml").has("result_code") && object.getJSONObject("xml").has("return_code") && object.getJSONObject("xml").getString("result_code").equals("SUCCESS") && object.getJSONObject("xml").getString("return_code").equals("SUCCESS")) {
            String out_trade_no = object.getJSONObject("xml").getString("out_trade_no");
            String endTime = object.getJSONObject("xml").getString("time_end");
            String transaction_id = object.getJSONObject("xml").getString("transaction_id");
            Map<String,String> parames = new HashMap<String,String>();
            parames.put("orderNo", out_trade_no);
            Orders order = orderService.findUniqueByParams(parames);
            if(order != null){
                order.setPayState("3");
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                order.setPayTime(format.parse(endTime).getTime() / 1000);
                order.setTraCreateTime(DateUtils.getUTC());
                order.setState("0");
                order.setTradeNo(transaction_id);
                orderService.modify(order);
                
                //统计数据
                Map<String,String> parames_order = new HashMap<String,String>();
                //String goodsInfo = order.getGoodsInfo();
                //日
                Long dayTime = DateUtils.getTimeByHourByTime(order.getPayTime());
                parames_order.put("statisticTime", dayTime.toString());
                parames_order.put("number", order.getNumber());
                OrderStatistics orderStatic = orderStatisticsService.findUniqueByParams(parames_order);
                if(orderStatic !=null){
                     orderStatic.setPrice(orderStatic.getPrice() + order.getPayPrice());   
                     orderStatic.setCount(orderStatic.getCount() + 1l);
                     orderStatic.setUpdateTime(DateUtils.getUTC());
                     orderStatic.setNumber(order.getNumber());
                     orderStatisticsService.modify(orderStatic);
                }else{
                     orderStatic = new OrderStatistics();
                     orderStatic.setNumber(order.getNumber());
                     orderStatic.setPrice(order.getPayPrice());
                     orderStatic.setCount(1l);
                     orderStatic.setStatisticTime(dayTime);
                     orderStatic.setCreateTime(DateUtils.getUTC());
                     orderStatic.setUpdateTime(DateUtils.getUTC());
                     orderStatisticsService.save(orderStatic);
                }
                //月
                 parames_order.clear();
                 Long monthTime = DateUtils.getTimesMorningByTime(order.getPayTime());
                 parames_order.put("statisticTime", monthTime.toString());
                 parames_order.put("number", order.getNumber());
                 OrderStatisticsM orderMStatic = orderMService.findUniqueByParams(parames_order);
                 if(orderMStatic != null){
                     orderMStatic.setNumber(order.getNumber());
                     orderMStatic.setPrice(orderMStatic.getPrice() + order.getPayPrice());   
                     orderMStatic.setCount(orderMStatic.getCount() + 1l);
                     orderMStatic.setUpdateTime(DateUtils.getUTC());
                     orderMService.modify(orderMStatic);
                }else{
                     orderMStatic = new OrderStatisticsM();
                     orderMStatic.setNumber(order.getNumber());
                     orderMStatic.setPrice(order.getPayPrice());
                     orderMStatic.setCount(1l);
                     orderMStatic.setStatisticTime(monthTime);
                     orderMStatic.setCreateTime(DateUtils.getUTC());
                     orderMStatic.setUpdateTime(DateUtils.getUTC());
                     orderMService.save(orderMStatic);
                }
                //年
                 parames_order.clear();
                 Long yearTime = DateUtils.getOneMonthFirstDate(order.getPayTime());
                 parames_order.put("statisticTime", yearTime.toString());
                 parames_order.put("number", order.getNumber());
                 OrderStatisticsY orderYStatic = orderYService.findUniqueByParams(parames_order);
                 if(orderYStatic != null){
                     orderYStatic.setNumber(order.getNumber());
                     orderYStatic.setPrice(orderYStatic.getPrice() + order.getPayPrice());   
                     orderYStatic.setCount(orderYStatic.getCount() + 1l);
                     orderYStatic.setUpdateTime(DateUtils.getUTC());
                     orderYService.modify(orderYStatic);
                }else{
                     orderYStatic = new OrderStatisticsY();
                     orderYStatic.setNumber(order.getNumber());
                     orderYStatic.setPrice(order.getPayPrice());
                     orderYStatic.setCount(1l);
                     orderYStatic.setStatisticTime(yearTime);
                     orderYStatic.setCreateTime(DateUtils.getUTC());
                     orderYStatic.setUpdateTime(DateUtils.getUTC());
                     orderYService.save(orderYStatic);
                }
                 
                parames.clear();
                parames.put("openId", order.getOpenId());
                logger.info("Debug at WechatController openId is [" + order.getOpenId() + "]");
                //用户的积分 经验值
                WXUser user = userService.findUniqueByParams(parames);
                if(user != null){
                    logger.info("WXUser user empirical is[" + user.getEmpirical() + "]");
                    if(user.getEmpirical() != null){
                       user.setEmpirical(order.getEmpirical()+user.getEmpirical());
                    }else{
                       user.setEmpirical(order.getEmpirical());
                    }
                    if(user.getIntegration() != null){
                        if(order.getIntegral()!=null){//扣除积分
                           user.setIntegration(user.getIntegration()- order.getIntegral()+ Long.parseLong(order.getGetIntegral()));
                        }else{
                           user.setIntegration(user.getIntegration() + Long.parseLong(order.getGetIntegral()));
                        }
                    }else{
                        user.setIntegration(Long.parseLong(order.getGetIntegral()));
                    }
                    userService.modify(user);
                }
                
            }
        }
        String returnMsg = "<xml> <return_code>SUCCESS</return_code></xml>";
        response.getWriter().write(returnMsg);
    }

    public String getPrePayId(HttpServletRequest request, HttpServletResponse response, String orderNo, String openId, String price) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        String nonce_str = getRandomString(7);
        InetAddress addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress().toString();// 获得本机IP
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        RequestHandler reqHandler = new RequestHandler(request, response);
        reqHandler.setParameter("appid", appId);
        reqHandler.setParameter("mch_id", mchid); // 商户号
        reqHandler.setParameter("nonce_str", nonce_str); // 随机字符串
        reqHandler.setParameter("body", "食国订单外卖"); // 商品描述
        reqHandler.setParameter("out_trade_no", orderNo); // 商家订单号
        reqHandler.setParameter("total_fee", (Integer.parseInt(price) * 100) + ""); // 商品金额,以分为单位
        reqHandler.setParameter("spbill_create_ip", ip); // 用户的公网ip
        reqHandler.setParameter("notify_url", payBackUrl);

        reqHandler.setParameter("trade_type", "JSAPI");
        // ------------需要进行用户授权获取用户openid-------------
        reqHandler.setParameter("openid", openId);
        String requestUrl = reqHandler.getRequestURL(clientSecret);

        // 发送预支付请求
        URL url1 = new URL(url);
        URLConnection con = url1.openConnection();
        con.setDoOutput(true);
        con.setRequestProperty("Pragma", "no-cache");
        con.setRequestProperty("Cache-Control", "no-cache");
        con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
        OutputStream out = con.getOutputStream();//new OutputStreamWriter(con.getOutputStream());
        String xmlInfo = requestUrl;
        out.write(requestUrl.getBytes("UTF-8"));
        out.flush();
        out.close();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String line = "";
        String result = "";
        for (line = br.readLine(); line != null; line = br.readLine()) {
            result = result + line;
        }
        return result;
    }

    public String getWechatToken(String code) {
        RestTemplate template = new RestTemplate();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + appId + "&secret=" + appSecret + "&code=" + code
                + "&grant_type=authorization_code";
        return template.getForObject(url, String.class);
    }

    public String getRandomString(int length) {
        StringBuffer buffer = new StringBuffer(
                "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i++) {
            sb.append(buffer.charAt(random.nextInt(range)));
        }
        return sb.toString();
    }
}
