/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.business.controller;

import cn.com.inhand.common.dto.BasicResultDTO;
import cn.com.inhand.common.dto.OnlyResultDTO;
import cn.com.inhand.common.util.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiguo.business.dto.BusinessBean;
import com.shiguo.business.entity.Business;
import com.shiguo.business.service.BusinessService;
import com.shiguo.common.vo.Page;
import com.shiguo.good.controller.UserController;
import com.shiguo.good.dto.UserBean;
import com.shiguo.good.entity.GoodsType;
import com.shiguo.good.entity.User;
import com.shiguo.good.service.UserService;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping("sapi/business")
public class BusinessController {
    private final static Logger logger = LoggerFactory.getLogger(BusinessController.class);
    @Autowired
    private BusinessService businessService;
    @Autowired
    ObjectMapper mapper;
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody
    Object createUser(
         @Valid @RequestBody BusinessBean bean)throws Exception {
         Business user = mapper.convertValue(bean, Business.class);
         businessService.save(user);
         OnlyResultDTO result = new OnlyResultDTO();
         result.setResult("success");
         return result;
    }
    
     
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    Object getAllBusiness(@RequestParam(required = false, defaultValue = "1000") int limit,
        @RequestParam(required = false, defaultValue = "0") int cursor,
            @RequestParam(value = "number", required = false) String number,
            @RequestParam(value = "name", required = false) String name)throws Exception {
             Map<String, Object> params = new HashMap<String, Object>();
             params.put("name", name);
             params.put("number", number);
             params.put("start", cursor);
             params.put("pagesize", limit);
             Page<Business> type = businessService.findByPage(params,cursor, limit);
             long total = businessService.findCountByParams(params);
	     return new BasicResultDTO(total, cursor, limit,type.getRows());
    }
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody
    Object getInfoByNumber(@RequestParam(value = "number", required = false) String number,
            @RequestParam(value = "name", required = false) String name)throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("number", number);
        params.put("name", name);
        List<Business> user = businessService.findByParams(params);
        OnlyResultDTO result = new OnlyResultDTO();
        result.setResult(user);
        return result;
    }
    
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public @ResponseBody
    Object updateUserById(@RequestParam(value = "number", required = false) String number,
            @RequestBody BusinessBean bean)throws Exception {
            Business user = mapper.convertValue(bean, Business.class);
            Map<String, String> params = new HashMap<String, String>();
            params.put("number", number);
            Business user_old = businessService.findUniqueByParams(params);
            user.setId(user_old.getId());
            businessService.modify(user);
            OnlyResultDTO result = new OnlyResultDTO();
            result.setResult("success");
            return result;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Object getUserById(@PathVariable Long id)throws Exception {
         Business user = businessService.findByPrimaryKey(id);//
         OnlyResultDTO result = new OnlyResultDTO();
         result.setResult(user);
         return result;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    Object updateUserById(@PathVariable Long id,
            @RequestBody BusinessBean bean)throws Exception {
            Business user = mapper.convertValue(bean, Business.class);
            Business user_old = businessService.findByPrimaryKey(id);
            user.setId(user_old.getId());
            businessService.modify(user);
            OnlyResultDTO result = new OnlyResultDTO();
            result.setResult("success");
            return result;
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Object deleteUserById(@PathVariable Long id)throws Exception {
             OnlyResultDTO result = new OnlyResultDTO();
             Business user_old = businessService.findByPrimaryKey(id);
             if(user_old!=null){
                 businessService.removeByPrimaryKey(id);
                 result.setResult("success");
             }else{
                 result.setResult("failure");
             }
            return result;
    }
    
}
