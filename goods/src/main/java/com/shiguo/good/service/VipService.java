/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.good.service;

import com.shiguo.common.dao.BaseDao;
import com.shiguo.common.service.CommonService;
import com.shiguo.good.dao.VipDAO;
import com.shiguo.good.entity.Vip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author shixj
 */
@Service
public class VipService extends CommonService<Vip>{
    @Autowired
    private VipDAO vipDAO;
     
    @Override
    protected BaseDao<Vip> getServiceDao() {
        return vipDAO;
    }
    
}
