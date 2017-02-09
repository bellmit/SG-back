/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.business.service;

import com.shiguo.business.dao.BusinessDAO;
import com.shiguo.business.entity.Business;
import com.shiguo.common.dao.BaseDao;
import com.shiguo.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author shixj
 */
@Service
public class BusinessService extends CommonService<Business>{
@Autowired
    private BusinessDAO businessDAO;
    @Override
    protected BaseDao<Business> getServiceDao() {
        return businessDAO;
    }
    
}
