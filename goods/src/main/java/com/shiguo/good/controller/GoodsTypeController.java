/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.good.controller;

import cn.com.inhand.common.dto.BasicResultDTO;
import cn.com.inhand.common.dto.OnlyResultDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiguo.common.vo.Page;
import com.shiguo.good.dto.GoodsTypeBean;
import com.shiguo.good.entity.GoodsType;
import com.shiguo.good.service.GoodsTypeService;
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
 *商品分类管理
 * @author shixj   
 */
@Controller
@RequestMapping("sapi/goodsType")
public class GoodsTypeController {
     private final static Logger logger = LoggerFactory.getLogger(GoodsTypeController.class);
     @Autowired
     private GoodsTypeService goodTypeService;
     @Autowired
     ObjectMapper mapper;
     
     /**
      * 添加商品分类
      * @param bean
      * @return 
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody
    Object createGoodsType(
    @Valid @RequestBody GoodsTypeBean bean)throws Exception {
        GoodsType type = mapper.convertValue(bean, GoodsType.class);
        goodTypeService.save(type);
        OnlyResultDTO result = new OnlyResultDTO();
        result.setResult("success");
        return result;
    }
    
     /**
      * 查询所有商品分类
      * @return 
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public
    @ResponseBody
    Object getAllGoodsType(
        @RequestParam(required = false, defaultValue = "10") int limit,
        @RequestParam(required = false, defaultValue = "0") int cursor,
        @RequestParam(value = "name", required = false) String name)throws Exception {
             GoodsTypeBean bean = new GoodsTypeBean();
             if(name!=null && !name.equals("")){
                bean.setName(name);
             }
             Map<String, Object> params = new HashMap<String, Object>();
             params.put("name", name);
             params.put("start", cursor);
             params.put("pagesize", limit);
             Page<GoodsType> type = goodTypeService.findByPage(params,cursor, limit);
             long total = goodTypeService.findCountByParams(params);
	     return new BasicResultDTO(total, cursor, limit,type.getRows());
    }
    /**
     * 根据ID获取某个商品分类的信息
     * @param id
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Object getUserById(@PathVariable Long id)throws Exception {
            GoodsType user = goodTypeService.findByPrimaryKey(id);
            OnlyResultDTO result = new OnlyResultDTO();
            result.setResult(user);
            return result;
    }
    /**
     * 根据ID修改商品分类
     * @param id
     * @param bean
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    Object updateUserById(@PathVariable Long id,
             @RequestBody GoodsTypeBean bean)throws Exception {
            GoodsType type = mapper.convertValue(bean, GoodsType.class);
           
            GoodsType type_old = goodTypeService.findByPrimaryKey(id);
            type.setId(type_old.getId());
            
            goodTypeService.modify(type);
            
            OnlyResultDTO result = new OnlyResultDTO();
            result.setResult("success");
            return result;
    }
    /**
     * 根据ID删除商品分类
     * @param id
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Object deleteUserById(@PathVariable Long id)throws Exception {
             OnlyResultDTO result = new OnlyResultDTO();
             GoodsType user_old = goodTypeService.findByPrimaryKey(id);
             if(user_old!=null){
                 goodTypeService.removeByPrimaryKey(id);
                 result.setResult("success");
             }else{
                 result.setResult("failure");
             }
            return result;
    }
}
