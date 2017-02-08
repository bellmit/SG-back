/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.user.controller;

import cn.com.inhand.common.dto.BasicResultDTO;
import cn.com.inhand.common.dto.OnlyResultDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiguo.common.vo.Page;
import com.shiguo.user.dto.WXUserAddressBean;
import com.shiguo.user.entity.WXUserAddress;
import com.shiguo.user.service.WXUserAddressService;
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
 *微信用户的收货地址
 * @author shixj
 */
@Controller
@RequestMapping("wapi/wuAddress")
public class WXUserAddressController {
    private final static Logger logger = LoggerFactory.getLogger(WXUserAddressController.class);
    @Autowired
    private WXUserAddressService addressService;
    @Autowired
    ObjectMapper mapper;
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody
    Object createUser(
    @Valid @RequestBody WXUserAddressBean bean)throws Exception {
        WXUserAddress user = mapper.convertValue(bean, WXUserAddress.class);
        addressService.save(user);
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
        @RequestParam(value = "openId", required = false) String openId)throws Exception {
             WXUserAddressBean bean = new WXUserAddressBean();
             if(openId!=null && !openId.equals("")){
                bean.setOpenId(openId);
             }
             Map<String, Object> params = new HashMap<String, Object>();
             params.put("openId", openId);
             params.put("start", cursor);
             params.put("pagesize", limit);
                     
             Page<WXUserAddress> user = addressService.findByPage(params,cursor, limit);
             long total = addressService.findCountByParams(params);
	     return new BasicResultDTO(total, cursor, limit,user.getRows());
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Object getUserById(@PathVariable Long id)throws Exception {
        WXUserAddress user = addressService.findByPrimaryKey(id);
        OnlyResultDTO result = new OnlyResultDTO();
        result.setResult(user);
        return result;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    Object updateUserById(@PathVariable Long id,
    @RequestBody WXUserAddressBean bean)throws Exception {
            WXUserAddress user = mapper.convertValue(bean, WXUserAddress.class);
            WXUserAddress user_old = addressService.findByPrimaryKey(id);
            user.setId(user_old.getId());
            addressService.modify(user);
            OnlyResultDTO result = new OnlyResultDTO();
            result.setResult("success");
            return result;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Object deleteUserById(@PathVariable Long id)throws Exception {
             OnlyResultDTO result = new OnlyResultDTO();
             WXUserAddress user_old = addressService.findByPrimaryKey(id);
             if(user_old!=null){
                 addressService.removeByPrimaryKey(id);
                 result.setResult("success");
             }else{
                 result.setResult("failure");
             }
            return result;
    }
    
}
