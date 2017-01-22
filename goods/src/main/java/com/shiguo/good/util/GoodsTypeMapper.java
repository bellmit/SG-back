/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.good.util;

import com.shiguo.model.GoodsType;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author shixj
 */
public class GoodsTypeMapper implements RowMapper<GoodsType>{
    public GoodsType mapRow(ResultSet rs, int i) throws SQLException {
        GoodsType type = new GoodsType();  
        type.setName(rs.getString("name"));
        type.setDesc(rs.getString("desc"));
        return type;       
    }
}
