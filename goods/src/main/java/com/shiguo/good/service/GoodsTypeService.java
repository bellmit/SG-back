/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.good.service;

import com.shiguo.good.dao.GoodsTypeDAO;
import com.shiguo.good.dto.GoodsTypeBean;
import com.shiguo.good.util.DataSourceImp;
import com.shiguo.good.util.GoodsTypeMapper;
import com.shiguo.model.GoodsType;
import com.shiguo.service.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author shixj
 */
@Service
public class GoodsTypeService  implements GoodsTypeDAO{
    private final static Logger logger = LoggerFactory.getLogger(GoodsTypeService.class);
    private static final String collectionName = Collections.COM_SHIGUO_GOODS_TYPE;

    @Autowired
    private DataSourceImp dataImp;
    private JdbcTemplate jdbcTemplateObject;
    public List<GoodsType> getAllGoodsType(GoodsTypeBean bean, int skip, int limit) {
        jdbcTemplateObject = dataImp.getJdbcTemplate();
        List<GoodsType> user = null;  
        String sql = "SELECT * FROM "+collectionName; 
        user = jdbcTemplateObject.query(sql, new GoodsTypeMapper());  
        return user;  
    }

    public long getCount(GoodsTypeBean bean) {
        jdbcTemplateObject = dataImp.getJdbcTemplate();
        String sql = "SELECT count(*) FROM "+collectionName; 
        long count =jdbcTemplateObject.queryForInt(sql);
        return count;  
    }
    
    
    
    
}
