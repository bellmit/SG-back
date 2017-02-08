/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.user.service;

import com.shiguo.common.dao.BaseDao;
import com.shiguo.common.service.CommonService;
import com.shiguo.user.dao.WXUserAddressDAO;
import com.shiguo.user.entity.WXUserAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author shixj
 */
@Service
public class WXUserAddressService extends CommonService<WXUserAddress>{
    @Autowired
    private WXUserAddressDAO wxUserAddressDAO;
    @Override
    protected BaseDao<WXUserAddress> getServiceDao() {
         return wxUserAddressDAO;
    }
    
}
