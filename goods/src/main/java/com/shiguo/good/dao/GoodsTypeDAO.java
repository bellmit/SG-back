/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.good.dao;

import com.shiguo.good.dto.GoodsTypeBean;
import com.shiguo.model.GoodsType;
import java.util.List;

/**
 *
 * @author shixj
 */
public interface GoodsTypeDAO {


    public List<GoodsType> getAllGoodsType(GoodsTypeBean bean, int cursor, int limit);

    public long getCount(GoodsTypeBean bean);
    
}
