/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.order.controller;

import com.shiguo.order.service.OrdersService;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author shixj
 */
@Controller
@RequestMapping("wapi/orderState")
public class OrderStateController {
    private static final Logger logger = LoggerFactory.getLogger(OrderStateController.class);
    @Autowired
    private OrdersService orderService;
    
    /**
     * 
     * @param id
     * @param request
     * @param response
     * @throws Exception 
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody
    void getOrderStateOfNumber( @RequestParam(required = false, defaultValue = "10000") int limit,
            @RequestParam(required = false, defaultValue = "0") int cursor,
            @RequestParam(value = "number", required = false) String number,//店铺编号
            @RequestParam(value = "state", required = false) String state,//订单状态
            @RequestParam(value = "payState", required = false) String payState,//付款状态状态
            @RequestParam(value = "cancelState", required = false) String cancelState,//订单取消确认状态
            @RequestParam(value = "reminderState", required = false) String reminderState,//催单状态
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
             Map<String, Object> params = new HashMap<String, Object>();
             params.put("state", state);
             params.put("start", cursor);
             params.put("pagesize", limit);
             params.put("number", number);
             params.put("cancelState", cancelState);
             params.put("payState", payState);
             params.put("reminderState", reminderState);
             
             response.setContentType("text/event-stream");
             response.setCharacterEncoding("UTF-8");
             PrintWriter writer = response.getWriter();
             String content = "error:no data";
             
             long total = orderService.findCountByParams(params);
             content = "data:" + total;
             
             writer.write(content + "\n\n");
             writer.flush();
             try {
                Thread.sleep(1000);
             } catch (InterruptedException e) {
                e.printStackTrace();
             }
             writer.close();
    }
}
