/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.good.service;

import com.shiguo.good.dao.UserDAO;
import com.shiguo.good.dto.UserBean;
import com.shiguo.good.util.DataSourceImp;
import com.shiguo.good.util.UserMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import shixj.test.model.User;
import shixj.test.service.Collections;

/**
 *
 * @author shixj
 */
@Service
public class UserService implements UserDAO{
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final String collectionName = Collections.SHIXJ_TEST_USER;
    
    @Autowired
    private DataSourceImp dataImp;
    private JdbcTemplate jdbcTemplateObject;
    

    public List<User> getAllUser(UserBean bean, int cursor, int limit) {
        jdbcTemplateObject = dataImp.getJdbcTemplate();
        List<User> user = null;  
        String sql = "SELECT * FROM user ORDER BY createTime DESC limit "+cursor+","+ limit+" "; 
        user = jdbcTemplateObject.query(sql, new UserMapper());  
        return user;  
    }
    
    public long getCount(UserBean bean) {
        jdbcTemplateObject = dataImp.getJdbcTemplate();
        String sql = "SELECT count(*) FROM "+collectionName; 
        long count =jdbcTemplateObject.queryForInt(sql);
        return count;  
    }

    public void addUser(User user) {
        jdbcTemplateObject = dataImp.getJdbcTemplate();
        String sql = "INSERT INTO user(name,password,email,phone,createTime,updateTime)VALUES(?,?,?,?,?,?)";  
        jdbcTemplateObject.update(sql, user.getName(),user.getPassword(),user.getEmail(),user.getPhone(),user.getCreateTime(),user.getUpdateTime());  
    }

    public User getUserById(int id) {
        jdbcTemplateObject = dataImp.getJdbcTemplate();
        return (User)jdbcTemplateObject.queryForObject("select * from user where id=?", new Object[]{id},
        new int[]{java.sql.Types.INTEGER}, new UserMapper());
    }

    public void updateUser(User user) {
        jdbcTemplateObject = dataImp.getJdbcTemplate();
        jdbcTemplateObject.update("update user set name=?,password=?,email=?,phone=?,updateTime=? where id=?", new Object[]{user.getName(),user.getPassword(),user.getEmail(),user.getPhone(),user.getUpdateTime(), user.getId()},
        new int[]{java.sql.Types.VARCHAR,java.sql.Types.VARCHAR,java.sql.Types.VARCHAR,java.sql.Types.VARCHAR,java.sql.Types.BIGINT, java.sql.Types.INTEGER});
    }

    public void deleteUser(int id) {
        jdbcTemplateObject = dataImp.getJdbcTemplate();
        jdbcTemplateObject.update("delete from user where id=?", new Object[]{id},
        new int[]{java.sql.Types.INTEGER});
    }
}
