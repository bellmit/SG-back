/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.order.controller;

import cn.com.inhand.common.dto.OnlyResultDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiguo.common.vo.Page;
import com.shiguo.order.dto.OrderBean;
import com.shiguo.order.entity.Order;
import com.shiguo.order.service.OrderService;
import com.shiguo.user.entity.WXUser;
import com.shiguo.user.service.WXUserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author shixj
 */
@Controller
@RequestMapping("wapi/order")
public class OrderController {
    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private WXUserService userService;
    @Autowired
    ObjectMapper mapper;
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody
    Object createOrder(
    @Valid @RequestBody OrderBean bean)throws Exception {
        Order order = mapper.convertValue(bean, Order.class);
        order.setState("1");//待支付
        orderService.save(order);
        //根据openId修改积分,经验值,会员等级
        String openId = bean.getOpenId();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("openId", openId);
        params.put("start", 0);
        params.put("pagesize", 1);
        Page<WXUser> user = userService.findByPage(params,0, 1);
        WXUser u =  user.getRows().get(0);
        u.setEmpirical(u.getEmpirical()+bean.getGtotalPrice()*10);//经验值
        u.setIntegration(u.getIntegration()+bean.getGtotalPrice());//积分
        //会员等级
        
        userService.modify(u);
        OnlyResultDTO result = new OnlyResultDTO();
        result.setResult("success");
        return result; 
    }
}
