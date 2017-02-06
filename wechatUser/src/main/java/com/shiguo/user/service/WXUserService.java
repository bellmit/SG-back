/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.user.service;

import com.shiguo.common.dao.BaseDao;
import com.shiguo.common.service.CommonService;
import com.shiguo.user.dao.WXUserDAO;
import com.shiguo.user.entity.WXUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author shixj
 */
@Service
public class WXUserService extends CommonService<WXUser>{
    @Autowired
    private WXUserDAO wxUserDAO;
    @Override
    protected BaseDao<WXUser> getServiceDao() {
        return wxUserDAO;
    }
    
}
