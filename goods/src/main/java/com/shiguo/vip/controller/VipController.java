/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.vip.controller;

import cn.com.inhand.common.dto.BasicResultDTO;
import cn.com.inhand.common.dto.OnlyResultDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiguo.common.vo.Page;
import com.shiguo.good.dto.VipBean;
import com.shiguo.good.entity.Vip;
import com.shiguo.good.service.VipService;
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
 *会员信息管理
 * @author shixj
 */
@Controller
@RequestMapping("sapi/vip")
public class VipController {
    private final static Logger logger = LoggerFactory.getLogger(VipController.class);
    @Autowired
    private VipService vipService;
    @Autowired
    ObjectMapper mapper;
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody
    Object createUser(
           @Valid @RequestBody VipBean bean)throws Exception {
            Vip vip = mapper.convertValue(bean, Vip.class);
            vipService.save(vip);
            OnlyResultDTO result = new OnlyResultDTO();
            result.setResult("success");
            return result;
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public
    @ResponseBody
    Object getAllUser(
        @RequestParam(required = false, defaultValue = "10") int limit,
        @RequestParam(required = false, defaultValue = "0") int cursor,
        @RequestParam(value = "levelName", required = false) String levelName)throws Exception {
             VipBean bean = new VipBean();
             if(levelName!=null && !levelName.equals("")){
                bean.setLevelName(levelName);
             }
             Map<String, Object> params = new HashMap<String, Object>();
             params.put("levelName", levelName);
             params.put("start", cursor);
             params.put("pagesize", limit);
                     
             Page<Vip> user = vipService.findByPage(params,cursor, limit);
             long total = vipService.findCountByParams(params);
	     return new BasicResultDTO(total, cursor, limit,user.getRows());
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Object getUserById(@PathVariable Long id)throws Exception {
            Vip user = vipService.findByPrimaryKey(id);
            OnlyResultDTO result = new OnlyResultDTO();
            result.setResult(user);
            return result;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    Object updateUserById(@PathVariable Long id,
            @RequestBody VipBean bean)throws Exception {
            Vip user = mapper.convertValue(bean, Vip.class);
            Vip user_old = vipService.findByPrimaryKey(id);
            user.setId(user_old.getId());
            vipService.modify(user);
            OnlyResultDTO result = new OnlyResultDTO();
            result.setResult("success");
            return result;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Object deleteUserById(@PathVariable Long id)throws Exception {
             OnlyResultDTO result = new OnlyResultDTO();
             Vip user_old = vipService.findByPrimaryKey(id);
             if(user_old!=null){
                 vipService.removeByPrimaryKey(id);
                 result.setResult("success");
             }else{
                 result.setResult("failure");
             }
           
            return result;
    }
    
    
}
