/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.good.controller;

import cn.com.inhand.common.dto.BasicResultDTO;
import cn.com.inhand.common.dto.OnlyResultDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiguo.good.dao.GoodsTypeDAO;
import com.shiguo.good.dto.GoodsTypeBean;
import com.shiguo.model.GoodsType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shixj.test.model.User;

/**
 *商品分类管理
 * @author shixj   
 */
@Controller
@RequestMapping("sapi/goodsType")
public class GoodsTypeController {
     private final static Logger logger = LoggerFactory.getLogger(GoodsTypeController.class);
     @Autowired
     private GoodsTypeDAO goodTypeService;
     @Autowired
     ObjectMapper mapper;
     
     /**
      * 获取所有的商品分类
      * @return 
      */ 
     @RequestMapping(value = "/list", method = RequestMethod.GET)
     public
     @ResponseBody 
     Object getAllGoodsType(
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "0") int cursor,
            @RequestParam(value = "name", required = false) String name) {
         
         GoodsTypeBean bean = new GoodsTypeBean();
         if(name!=null && !name.equals("")){
             bean.setName(name);
         }
         List<GoodsType> type = goodTypeService.getAllGoodsType(bean,cursor, limit);
	 long total = goodTypeService.getCount(bean);
         return new BasicResultDTO(total, cursor, limit, type);
    }
     
     
}
