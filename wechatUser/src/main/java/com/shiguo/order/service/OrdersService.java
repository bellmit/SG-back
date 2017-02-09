/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.order.service;

import com.shiguo.common.dao.BaseDao;
import com.shiguo.common.service.CommonService;
import com.shiguo.order.dao.OrdersDAO;
import com.shiguo.order.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author shixj
 */
@Service
public class OrdersService extends CommonService<Orders>{
    @Autowired
    private OrdersDAO orderDAO;
    @Override
    protected BaseDao<Orders> getServiceDao() {
        return orderDAO;
    }
    
}
