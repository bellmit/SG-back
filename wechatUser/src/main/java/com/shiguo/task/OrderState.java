/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.task;

import cn.com.inhand.common.util.DateUtils;
import com.shiguo.common.vo.Page;
import com.shiguo.order.entity.Orders;
import com.shiguo.order.service.OrdersService;
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
                     
        Page<Orders> user = orderService.findByPage(params,cursor, limit);
        if(user.getRows().size()>0){
           for(int i=0;i<user.getRows().size();i++){
             logger.info("***checkOrderState****"+user.getRows().get(i).getState());
             if(user.getRows().get(i).getState().equals("-1")){
                 long id = user.getRows().get(i).getId();
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
    }
}
