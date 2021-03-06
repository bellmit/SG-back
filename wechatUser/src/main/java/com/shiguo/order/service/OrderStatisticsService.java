/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.order.service;

import com.shiguo.common.dao.BaseDao;
import com.shiguo.common.service.CommonService;
import com.shiguo.order.dao.OrderStatisticsDAO;
import com.shiguo.order.entity.OrderStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author shixj
 */
@Service
public class OrderStatisticsService extends CommonService<OrderStatistics>{
    @Autowired
    private OrderStatisticsDAO orderStatisticsDAO;
    @Override
    protected BaseDao<OrderStatistics> getServiceDao() {
        return orderStatisticsDAO;
    }
}
