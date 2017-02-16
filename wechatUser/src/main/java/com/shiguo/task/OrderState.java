/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.task;

import cn.com.inhand.common.util.DateUtils;
import com.shiguo.common.vo.Page;
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
import com.shiguo.wechat.factory.WeChatRefundFactory.JSONUtil;
import com.shiguo.wechat.factory.WeChatRefundFactory.RefundBean;
import com.shiguo.wechat.factory.WeChatRefundFactory.RefundResult;
import com.shiguo.wechat.factory.WeChatRefundFactory.ResponseHandler;
import com.shiguo.wechat.factory.WeChatRefundFactory.WeChatRefundFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author shixj
 */
public class OrderState {
    private final static Logger logger = LoggerFactory.getLogger(OrderState.class);
    @Autowired
    private OrdersService orderService;
    @Autowired
    private WeChatRefundFactory wechatRefundFactory;
    @Autowired
    private WXUserService userService;
    @Autowired
    private OrderStatisticsService orderStatisticsService;
    @Autowired
    private OrderStatisticsMService orderMService;
    @Autowired
    private OrderStatisticsYService orderYService;
    
    @Scheduled(cron="0 * * * * *")  //代表每分钟执行一次
    
    public void checkOrderState()throws Exception{
        Date date=new Date();  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
        
        int limit=1000000;
        int cursor=0;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", "-1");//未支付的订单
        params.put("start", cursor);
        params.put("pagesize", limit);
                     
        Page<Orders> order = orderService.findByPage(params,cursor, limit);
        if(order.getRows().size()>0){
           for(int i=0;i<order.getRows().size();i++){
             logger.info("***checkOrderState****"+order.getRows().get(i).getState());
             if(order.getRows().get(i).getState().equals("-1")){
                 long id = order.getRows().get(i).getId();
                 Orders oldOrder = orderService.findByPrimaryKey(id);
                 long createTime = oldOrder.getCreateTime();
                 long timestamp = DateUtils.getUTC();
                 long s = timestamp - createTime;
                 if(s > 30*60){//订单超过30分钟未支付,自动取消
                     oldOrder.setState("4");
                     oldOrder.setOrderCancelTime(timestamp);
                     orderService.modify(oldOrder);
                 }
             }
           }
        }
        
        Map<String, Object> params_refund = new HashMap<String, Object>();
        params_refund.put("state", "4");//取消的订单
        params_refund.put("start", cursor);
        params_refund.put("pagesize", limit);
        Page<Orders> user = orderService.findByPage(params_refund,cursor, limit);
        if(user.getRows().size()>0){
           for(int i=0;i<user.getRows().size();i++){
               if(user.getRows().get(i).getRefundStatus()!=null){
                 if(user.getRows().get(i).getRefundStatus().toString().equals("2")){
                   return;
                 }
               }
               if(user.getRows().get(i).getState().equals("4") && user.getRows().get(i).getPayState().equals("3")){
                     String orderNo = user.getRows().get(i).getOrderNo();
                     String now = System.currentTimeMillis() + "" + orderNo.subSequence(orderNo.length() - 6, orderNo.length());
                     int total_fee = Integer.parseInt((user.getRows().get(i).getPayPrice() * 100)+"");
                     int refund_fee = total_fee;
                     String appId = "wx93bb5b9d65937271";
                     String appSecret = "6b8ae25929fdac0488e1aa4391359af7";
                     String mchid = "1438038502";
                     String clientSecret = "s3M81qfqoPPMCHDgnRuP7dOZI2eRjBjE";
                     String refundResult = wechatRefundFactory.refund(orderNo, now,total_fee, refund_fee, mchid, appId, clientSecret);
                     logger.info("refund application refundResult is "+refundResult);
                     if (refundResult != null && !refundResult.equals("")) {
                         String resultJson = ResponseHandler.xml2JSON(refundResult);
                         RefundResult result = (RefundResult) JSONUtil.unserializedJson(resultJson, RefundResult.class);
                         RefundBean refundBean = result.getXml();
                         Map responseMap = ResponseHandler.doXMLParse(refundResult);
                         if (refundBean.getReturn_code().equals("SUCCESS")) {
                             user.getRows().get(i).setRefundTime(System.currentTimeMillis() / 1000);
                             user.getRows().get(i).setRefundOrderNo(now);
                             user.getRows().get(i).setRefundFee(user.getRows().get(i).getPayPrice());
                             if (refundBean.getResult_code().equals("SUCCESS")) {
                                 logger.info("refund application success  result code SUCCESS");
                                 user.getRows().get(i).setRefundStatus(2l);//退款成功
                                 Long price = user.getRows().get(i).getPayPrice();
                                 Long integral = Long.parseLong(user.getRows().get(i).getGetIntegral());
                                 
                                 //用户此次消费获得的积分和经验值取消
                                  Map<String,String> parames = new HashMap<String,String>();
                                  parames.put("openId", user.getRows().get(i).getOpenId());
                                  WXUser userObj = userService.findUniqueByParams(parames);
                                  if(userObj != null){
                                     if(userObj.getEmpirical() != null){//经验值
                                         userObj.setEmpirical(userObj.getEmpirical() -price*10);
                                     }
                                     if(userObj.getIntegration()!=null){
                                           userObj.setIntegration(userObj.getIntegration() - integral);
                                     }
                                     userService.modify(userObj);
                                  }
                                  
                                 Long payTime=user.getRows().get(i).getPayTime();
                                 String number=user.getRows().get(i).getNumber();
                                 //统计 减
                                 Map<String,String> parames_order = new HashMap<String,String>();
                                 //日
                                Long dayTime = DateUtils.getTimeByHourByTime(payTime);
                                parames_order.put("statisticTime", dayTime.toString());
                                parames_order.put("number", number);
                                OrderStatistics orderStatic = orderStatisticsService.findUniqueByParams(parames_order);
                                if(orderStatic !=null){
                                    orderStatic.setPrice(orderStatic.getPrice() - price);   
                                    orderStatic.setCount(orderStatic.getCount() - 1l);
                                    orderStatic.setUpdateTime(DateUtils.getUTC());
                                    orderStatic.setNumber(number);
                                    orderStatisticsService.modify(orderStatic);
                                }

                                parames_order.clear();
                                Long monthTime = DateUtils.getTimesMorningByTime(payTime);
                                parames_order.put("statisticTime", monthTime.toString());
                                parames_order.put("number", number);
                                OrderStatisticsM orderMStatic = orderMService.findUniqueByParams(parames_order);
                                if(orderMStatic != null){
                                    orderMStatic.setNumber(number);
                                    orderMStatic.setPrice(orderMStatic.getPrice() - price);   
                                    orderMStatic.setCount(orderMStatic.getCount() - 1l);
                                    orderMStatic.setUpdateTime(DateUtils.getUTC());
                                    orderMService.modify(orderMStatic);
                                }

                                parames_order.clear();
                                Long yearTime = DateUtils.getOneMonthFirstDate(payTime);
                                parames_order.put("statisticTime", yearTime.toString());
                                parames_order.put("number", number);
                                OrderStatisticsY orderYStatic = orderYService.findUniqueByParams(parames_order);
                                if(orderYStatic != null){
                                    orderYStatic.setNumber(number);
                                    orderYStatic.setPrice(orderYStatic.getPrice() - price);   
                                    orderYStatic.setCount(orderYStatic.getCount() - 1l);
                                    orderYStatic.setUpdateTime(DateUtils.getUTC());
                                    orderYService.modify(orderYStatic);
                                }
                                 
                             }else{
                                 logger.info("==========refund application success  result code ERROR:" + refundBean.getErr_code() + "-" + refundBean.getErr_code_des());
                                 user.getRows().get(i).setRefundStatus(3l);
                             }
                             orderService.modify(user.getRows().get(i));
                         }else {
                            if (refundBean.getReturn_msg() != null) {
                               logger.info("============" + refundBean.getReturn_msg());
                            }
                         }
                    }
           }
        }
      }
    }
}
