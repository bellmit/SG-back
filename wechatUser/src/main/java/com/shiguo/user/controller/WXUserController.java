/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.user.controller;

import cn.com.inhand.common.dto.BasicResultDTO;
import cn.com.inhand.common.dto.OnlyResultDTO;
import cn.com.inhand.common.util.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiguo.common.vo.Page;
import com.shiguo.user.dto.WXUserBean;
import com.shiguo.user.entity.WXUser;
import com.shiguo.user.service.WXUserService;
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
 *
 * @author shixj
 */
@Controller
@RequestMapping("wapi/wxuser")
public class WXUserController {
    private final static Logger logger = LoggerFactory.getLogger(WXUserController.class);
    @Autowired
    private WXUserService userService;
    @Autowired
    ObjectMapper mapper;
    /**
     * 添加微信用户
     * @param bean
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody
    Object createUser(
    @Valid @RequestBody WXUserBean bean)throws Exception {
        if(bean.getEmpirical() == null || bean.getEmpirical().equals("")){//经验值
            bean.setEmpirical(0l);
        }
        if(bean.getIntegration() == null || bean.getIntegration().equals("")){//积分
            bean.setIntegration(0l);
        }
        WXUser user = mapper.convertValue(bean, WXUser.class);
        userService.save(user);
        OnlyResultDTO result = new OnlyResultDTO();
        result.setResult("success");
        return result;    
    }
    /**
     * 获取所有的微信用户信息
     * @param limit
     * @param cursor
     * @param name
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public
    @ResponseBody
    Object getAllUser(
        @RequestParam(required = false, defaultValue = "10") int limit,
        @RequestParam(required = false, defaultValue = "0") int cursor,
        @RequestParam(value = "name", required = false) String name)throws Exception {
             WXUserBean bean = new WXUserBean();
             if(name!=null && !name.equals("")){
                bean.setNickName(name);
             }
             Map<String, Object> params = new HashMap<String, Object>();
             params.put("name", name);
             params.put("start", cursor);
             params.put("pagesize", limit);
                     
             Page<WXUser> user = userService.findByPage(params,cursor, limit);
             long total = userService.findCountByParams(params);
	     return new BasicResultDTO(total, cursor, limit,user.getRows());
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Object getUserById(@PathVariable Long id)throws Exception {
        WXUser user = userService.findByPrimaryKey(id);
        OnlyResultDTO result = new OnlyResultDTO();
        result.setResult(user);
        return result;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    Object updateUserById(@PathVariable Long id,
        @RequestBody WXUserBean bean)throws Exception {
            WXUser user = mapper.convertValue(bean, WXUser.class);
            WXUser user_old = userService.findByPrimaryKey(id);
            user.setId(user_old.getId());
            userService.modify(user);
            OnlyResultDTO result = new OnlyResultDTO();
            result.setResult("success");
            return result;
        }
    
    
}
