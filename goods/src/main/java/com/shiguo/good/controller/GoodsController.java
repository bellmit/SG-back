/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.good.controller;

import cn.com.inhand.common.dto.BasicResultDTO;
import cn.com.inhand.common.dto.OnlyResultDTO;
import cn.com.inhand.common.util.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiguo.common.vo.Page;
import com.shiguo.good.dto.GoodsBean;
import com.shiguo.good.entity.Goods;
import com.shiguo.good.service.GoodsService;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *商品管理
 * @author shixj
 */
@Controller
@RequestMapping("sapi/goods")
public class GoodsController {
     private final static Logger logger = LoggerFactory.getLogger(GoodsController.class);
     @Autowired
     private GoodsService goodService;
     @Autowired
     ObjectMapper mapper;
    /**
     * 添加商品
     * @param bean
     * @return
     * @throws Exception 
     */ 
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody
    Object createGoodsType(
    @Valid @RequestBody GoodsBean bean)throws Exception {
        Goods type = mapper.convertValue(bean, Goods.class);
        long timestamp = DateUtils.getUTC();
        type.setCreateTime(timestamp);
        type.setUpdateTime(timestamp);
        goodService.save(type);
        OnlyResultDTO result = new OnlyResultDTO();
        result.setResult("success");
        return result;
    }
    /**
     * 查询所有的商品
     * @param limit
     * @param cursor
     * @param name
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public
    @ResponseBody
    Object getAllGoodsType(
        @RequestParam(required = false, defaultValue = "10") int limit,
        @RequestParam(required = false, defaultValue = "0") int cursor,
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "typeID", required = false) String typeID,
        @RequestParam(value = "state", required = false) String state)throws Exception {
             GoodsBean bean = new GoodsBean();
             if(name!=null && !name.equals("")){
                bean.setName(name);
             }
             if(typeID!=null && !typeID.equals("")){
                bean.setTypeID(typeID);
             }
             if(state!=null && !state.equals("")){
                bean.setState(state);
             }
             
             Map<String, Object> params = new HashMap<String, Object>();
             params.put("name", name);
             params.put("typeID", typeID);
             params.put("state", state);
             params.put("start", cursor);
             params.put("pagesize", limit);
             Page<Goods> type = goodService.findByPage(params,cursor, limit);
             long total = goodService.findCountByParams(params);
	     return new BasicResultDTO(total, cursor, limit,type.getRows());
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Object getUserById(@PathVariable Long id)throws Exception {
            Goods user = goodService.findByPrimaryKey(id);
            OnlyResultDTO result = new OnlyResultDTO();
            result.setResult(user);
            return result;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    Object updateUserById(@PathVariable Long id,
             @RequestBody GoodsBean bean)throws Exception {
            Goods type = mapper.convertValue(bean, Goods.class);
           
            Goods type_old = goodService.findByPrimaryKey(id);
            type.setId(type_old.getId());
            type.setCreateTime(type_old.getCreateTime());
            long timestamp = DateUtils.getUTC();
            type.setUpdateTime(timestamp);
            goodService.modify(type);
            
            OnlyResultDTO result = new OnlyResultDTO();
            result.setResult("success");
            return result;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Object deleteUserById(@PathVariable Long id)throws Exception {
             OnlyResultDTO result = new OnlyResultDTO();
             Goods user_old = goodService.findByPrimaryKey(id);
             if(user_old!=null){
                 goodService.removeByPrimaryKey(id);
                 result.setResult("success");
             }else{
                 result.setResult("failure");
             }
            return result;
    }
    
    
}
