/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.good.service;

import com.shiguo.common.dao.BaseDao;
import com.shiguo.common.service.CommonService;
import com.shiguo.good.dao.TestDAO;
import com.shiguo.good.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lenovo
 */
@Service
public class TestService extends CommonService<Test>{

    @Autowired
    private TestDAO testDAO;
    
    @Override
    protected BaseDao<Test> getServiceDao() {
        return testDAO;
    }
    
}
