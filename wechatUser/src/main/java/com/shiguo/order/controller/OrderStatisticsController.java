/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.order.controller;

import cn.com.inhand.common.dto.BasicResultDTO;
import cn.com.inhand.common.util.DateUtils;
import com.shiguo.common.vo.Page;
import com.shiguo.order.dto.OrderStatisticsBean;
import com.shiguo.order.entity.OrderStatistics;
import com.shiguo.order.entity.OrderStatisticsM;
import com.shiguo.order.entity.OrderStatisticsY;
import com.shiguo.order.entity.Orders;
import com.shiguo.order.service.OrderStatisticsMService;
import com.shiguo.order.service.OrderStatisticsService;
import com.shiguo.order.service.OrderStatisticsYService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author shixj
 */
@Controller
@RequestMapping("wapi/statistics")
public class OrderStatisticsController {
     private final static Logger logger = LoggerFactory.getLogger(OrderStatisticsController.class);
     @Autowired
     private OrderStatisticsService orderStatisticsService;
     @Autowired
     private OrderStatisticsMService orderMService;
     @Autowired
     private OrderStatisticsYService orderYService;
     
    @RequestMapping(value = "/day", method = RequestMethod.GET)
    public
    @ResponseBody
    Object getAllDay(@RequestParam(required = false, defaultValue = "10") int limit,
        @RequestParam(required = false, defaultValue = "0")int cursor,
        @RequestParam(value = "number", required = false) String number,
        @RequestParam(value = "startTime", required = false) Long startTime,
        @RequestParam(value = "endTime", required = false) Long endTime) throws Exception {
             
             Map<String, Object> params = new HashMap<String, Object>();
             params.put("start", cursor);
             params.put("pagesize", limit);
             params.put("startTime", startTime);
             params.put("endTime", endTime);
             params.put("number", number);
                     
             Page<OrderStatistics> user = orderStatisticsService.findByPage(params,cursor, limit);
             Long priceT=0l;
             Long countT=0l;
             List<Object> amounts = new ArrayList<Object>();
             List<Object> sums = new ArrayList<Object>();
             List<Object> statisticTime = new ArrayList<Object>();
             
             int flag = 1;
             for (long stime = startTime; stime <= endTime;) {
                  long amountBase = 0l;
                  long sumBase = 0l;
                  for (OrderStatistics order : user.getRows()) {
                     statisticTime.add(stime);
                     if (stime == order.getStatisticTime()) {
                         flag = 0;
                         amountBase = order.getPrice();
                         sumBase = order.getCount();
                         priceT +=  order.getPrice()*100;
                         countT += order.getCount();
                     }
                  }
                  
                  if (flag == 1) {
                      amounts.add(0l);
                      sums.add(0l);
                  } else if (flag == 0) {
                     amounts.add(amountBase);
                     sums.add(sumBase);
                  }
                  stime = stime + 60 * 60;
                  flag = 1;
             }
             
             List<OrderStatisticsBean> result = new ArrayList<OrderStatisticsBean>();
             result.add(new OrderStatisticsBean((float)priceT/100,countT,amounts,sums,statisticTime));
             return result;
    }
    
    @RequestMapping(value = "/month", method = RequestMethod.GET)
    public
    @ResponseBody
    Object getAllMonth(@RequestParam(required = false, defaultValue = "10") int limit,
        @RequestParam(required = false, defaultValue = "0")int cursor,
        @RequestParam(value = "number", required = false) String number,
        @RequestParam(value = "startTime", required = false) Long startTime,
        @RequestParam(value = "endTime", required = false) Long endTime) throws Exception {
             Map<String, Object> params = new HashMap<String, Object>();
             params.put("start", cursor);
             params.put("pagesize", limit);
             params.put("startTime", startTime);
             params.put("endTime", endTime);
             params.put("number", number);
                     
             Page<OrderStatisticsM> user = orderMService.findByPage(params,cursor, limit);
             
             Long priceT=0l;
             Long countT=0l;
             List<Object> amounts = new ArrayList<Object>();
             List<Object> sums = new ArrayList<Object>();
             List<Object> statisticTime = new ArrayList<Object>();
             
             int flag = 1;
             int day = 1;
             for (long stime = startTime; stime <= endTime;) {
                  long amountBase = 0l;
                  long sumBase = 0l;
                  for (OrderStatisticsM order : user.getRows()) {
                     statisticTime.add(stime);
                     if (stime == order.getStatisticTime()) {
                         flag = 0;
                         amountBase = order.getPrice();
                         sumBase = order.getCount();
                         priceT +=  order.getPrice()*100;
                         countT += order.getCount();
                     }
                  }
                  
                  if (flag == 1) {
                      amounts.add(0l);
                      sums.add(0l);
                  } else if (flag == 0) {
                     amounts.add(amountBase);
                     sums.add(sumBase);
                  }
                  
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                int months = 0;
                try {
                    months = format.parse(format.format(startTime*1000)).getMonth()+1;
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    String sds = format1.format(new Date(Long.parseLong(String.valueOf(startTime*1000)))); 
                    Date date = format.parse(sds);
                    Calendar cale = Calendar.getInstance();
                    cale.setTime(date);
                    int year = cale.get(Calendar.YEAR);
                    cale.set(Calendar.YEAR, year);  
                    cale.set(Calendar.MONTH, 1);  

                    int maxday = cale.getActualMaximum(Calendar.DAY_OF_MONTH); 

                    if(months == 1|| months ==3 || months == 5 || months == 7 || months ==8 || months == 10 || months == 12){
                        if(day>31 || day == 31 ){
                           break;
                        }
                    }else if(months == 2){
                        if(day>maxday || day == maxday ){
                           break;
                        }
                    }else{
                       if(day>30 || day == 30 ){
                           break;
                       }
                    }
                   stime = stime + 24 * 60 * 60;
                   day = day + 1;
                } catch (ParseException ex) {
                    java.util.logging.Logger.getLogger(OrderStatisticsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                  flag = 1;
             }
             
             List<OrderStatisticsBean> result = new ArrayList<OrderStatisticsBean>();
             result.add(new OrderStatisticsBean((float)priceT/100,countT,amounts,sums,statisticTime));
             return result;
    }
    
    @RequestMapping(value = "/year", method = RequestMethod.GET)
    public
    @ResponseBody
    Object getAllYear(@RequestParam(required = false, defaultValue = "10") int limit,
        @RequestParam(required = false, defaultValue = "0")int cursor,
        @RequestParam(value = "number", required = false) String number,
        @RequestParam(value = "startTime", required = false) Long startTime,
        @RequestParam(value = "endTime", required = false) Long endTime) throws Exception {
             Map<String, Object> params = new HashMap<String, Object>();
             params.put("start", cursor);
             params.put("pagesize", limit);
             params.put("startTime", startTime);
             params.put("endTime", endTime);
             params.put("number", number);
                     
             Page<OrderStatisticsY> user = orderYService.findByPage(params,cursor, limit);
             Long priceT=0l;
             Long countT=0l;
             List<Object> amounts = new ArrayList<Object>();
             List<Object> sums = new ArrayList<Object>();
             List<Object> statisticTime = new ArrayList<Object>();
             
             int flag = 1;
             int month = 1;
             for (long stime = startTime; stime <= endTime;) {
                  long amountBase = 0l;
                  long sumBase = 0l;
                  for (OrderStatisticsY order : user.getRows()) {
                     statisticTime.add(stime);
                     if (stime == order.getStatisticTime()) {
                         flag = 0;
                         amountBase = order.getPrice();
                         sumBase = order.getCount();
                         priceT +=  order.getPrice()*100;
                         countT += order.getCount();
                     }
                  }
                  
                  if (flag == 1) {
                      amounts.add(0l);
                      sums.add(0l);
                  } else if (flag == 0) {
                     amounts.add(amountBase);
                     sums.add(sumBase);
                  }
                  
                if(month>12 || month == 12 ){
                    break;
                }
                month = month + 1;
                stime = DateUtils.getOneMonthStartDayOfYear(month,startTime);
                
                flag = 1;
             }
             
             List<OrderStatisticsBean> result = new ArrayList<OrderStatisticsBean>();
             result.add(new OrderStatisticsBean((float)priceT/100,countT,amounts,sums,statisticTime));
             return result;
    }
}
