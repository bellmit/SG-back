/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.good.util;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author shixj
 */
public class DataSourceImp {
    private DataSource ds; 
    private JdbcTemplate jdbcTemplateObject;

    public DataSourceImp(DataSource ds){
        this.ds = ds;
        this.jdbcTemplateObject = new JdbcTemplate(ds); 
    }
    public JdbcTemplate getJdbcTemplate(){
       return this.jdbcTemplateObject;
    }
}
