/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.good.service;

import com.shiguo.common.dao.BaseDao;
import com.shiguo.common.service.CommonService;
import com.shiguo.good.dao.UserDAO;
import com.shiguo.good.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author shixj
 */
@Service
public class UserService extends CommonService<User>{
    
    @Autowired
    private UserDAO userDAO;

    @Override
    protected BaseDao<User> getServiceDao() {
        return userDAO;
    }
    
    
}
