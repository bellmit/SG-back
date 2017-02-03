/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.good.service;

import com.shiguo.common.dao.BaseDao;
import com.shiguo.common.service.CommonService;
import com.shiguo.good.dao.GoodsTypeDAO;
import com.shiguo.good.entity.GoodsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author shixj
 */
@Service
public class GoodsTypeService  extends CommonService<GoodsType>{
    
    @Autowired
    private GoodsTypeDAO goodsTypeDAO;

    @Override
    protected BaseDao<GoodsType> getServiceDao() {
        return goodsTypeDAO;
    }
    
}
