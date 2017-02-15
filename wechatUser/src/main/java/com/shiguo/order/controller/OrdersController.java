/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.order.controller;

import cn.com.inhand.common.dto.BasicResultDTO;
import cn.com.inhand.common.dto.OnlyResultDTO;
import cn.com.inhand.common.util.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiguo.common.vo.Page;
import com.shiguo.order.dto.OrdersBean;
import com.shiguo.order.entity.Orders;
import com.shiguo.order.service.OrdersService;
import com.shiguo.user.entity.WXUser;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *订单
 * @author shixj
 */
@Controller
@RequestMapping("wapi/order")
public class OrdersController {
    private final static Logger logger = LoggerFactory.getLogger(OrdersController.class);
    @Autowired
    private OrdersService orderService;
    @Autowired
    ObjectMapper mapper;
    
    /**
     * 创建订单
     * @param bean
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody
    Object createOrder(
    @Valid @RequestBody OrdersBean bean)throws Exception {
        Orders order = mapper.convertValue(bean, Orders.class);
        long timestamp = DateUtils.getUTC();
        order.setCreateTime(timestamp);
        orderService.save(order);
        OnlyResultDTO result = new OnlyResultDTO();
        result.setResult("success");
        return result; 
    }
    /**
     * 获取所有的订单
     * @param limit
     * @param cursor
     * @param orderNo
     * @param openId
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public
    @ResponseBody
    Object getAllOrder(
        @RequestParam(required = false, defaultValue = "10") int limit,
        @RequestParam(required = false, defaultValue = "0") int cursor,
        @RequestParam(value = "orderNo", required = false) String orderNo,
        @RequestParam(value = "openId", required = false) String openId,
        @RequestParam(value = "state", required = false) String state,
        @RequestParam(value = "number", required = false) String number,
        @RequestParam(value = "payState", required = false) String payState)throws Exception {
             Map<String, Object> params = new HashMap<String, Object>();
             params.put("orderNo", orderNo);
             params.put("openId", openId);
             params.put("state", state);
             params.put("payState", payState);
             params.put("start", cursor);
             params.put("pagesize", limit);
             params.put("number", number);
                     
             Page<Orders> user = orderService.findByPage(params,cursor, limit);
             long total = orderService.findCountByParams(params);
	     return new BasicResultDTO(total, cursor, limit,user.getRows());
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Object getUserById(@PathVariable Long id)throws Exception {
        Orders user = orderService.findByPrimaryKey(id);
        OnlyResultDTO result = new OnlyResultDTO();
        result.setResult(user);
        return result;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    Object updateUserById(@PathVariable Long id,
        @RequestBody OrdersBean bean)throws Exception {
            Orders user = orderService.findByPrimaryKey(id);
            if(bean.getState()!=null&&!bean.getState().equals("")){
                user.setState(bean.getState());
                long timestamp = DateUtils.getUTC();
                if(bean.getState().equals("1")){
                  user.setOrderInTime(timestamp);//接单时间
                }else if(bean.getState().equals("2")){
                  user.setOrderOutTime(timestamp);//配送时间
                }else if(bean.getState().equals("3")){
                  user.setOrderFinishTime(timestamp);//完成时间
                }
            }
            
            orderService.modify(user);
            OnlyResultDTO result = new OnlyResultDTO();
            result.setResult("success");
            return result;
        }
    
}
