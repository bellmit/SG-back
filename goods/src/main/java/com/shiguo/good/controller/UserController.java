package com.shiguo.good.controller;

import cn.com.inhand.common.dto.BasicResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.inhand.common.dto.OnlyResultDTO;
import cn.com.inhand.common.util.DateUtils;
import com.shiguo.good.dao.UserDAO;
import com.shiguo.good.dto.UserBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import shixj.test.model.User;

@Controller
@RequestMapping("sapi/user")
public class UserController {
	 private final static Logger logger = LoggerFactory.getLogger(UserController.class);
	 @Autowired
         private UserDAO userService;
         @Autowired
         ObjectMapper mapper;
         
         /**
          * 查询所有
          * @return 
          */
	 @RequestMapping(value = "/list", method = RequestMethod.GET)
         public
         @ResponseBody
         Object getAllUser(
                 @RequestParam(required = false, defaultValue = "10") int limit,
                 @RequestParam(required = false, defaultValue = "0") int cursor,
                 @RequestParam(value = "name", required = false) String name) {
             UserBean bean = new UserBean();
             if(name!=null && !name.equals("")){
                bean.setName(name);
             }
             List<User> user = userService.getAllUser(bean,cursor, limit);
             long total = userService.getCount(bean);
	     return new BasicResultDTO(total, cursor, limit,user);
         }
         /**
          * 添加
          * @param bean
          * @return 
          */
         @RequestMapping(value = "/add", method = RequestMethod.POST)
         public @ResponseBody
         Object createUser(
           @Valid @RequestBody UserBean bean) {
            User user = mapper.convertValue(bean, User.class);
            long timestamp = DateUtils.getUTC();
            user.setCreateTime(timestamp);
            user.setUpdateTime(timestamp);
            userService.addUser(user);
            OnlyResultDTO result = new OnlyResultDTO();
            result.setResult("success");
            return result;
         }
         /**
          * 根据用户id查询用户信息
          * @param id
          * @return 
          */
         @RequestMapping(value = "/{id}", method = RequestMethod.GET)
         public @ResponseBody
         Object getUserById(@PathVariable int id) {
            User user = userService.getUserById(id);
            OnlyResultDTO result = new OnlyResultDTO();
            result.setResult(user);
            return result;
         }
         /**
          * 根据用户ID修改用户信息
          * @param id
          * @param bean
          * @return 
          */
         @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
         public @ResponseBody
         Object updateUserById(@PathVariable int id,
             @RequestBody UserBean bean) {
            User user = mapper.convertValue(bean, User.class);
            user.setId(id);
            long timestamp = DateUtils.getUTC();
            user.setUpdateTime(timestamp);
            
            OnlyResultDTO result = new OnlyResultDTO();
            
            User user_old = userService.getUserById(id);
            if(user_old!=null){
               userService.updateUser(user);
               result.setResult("success");
            }else{
               result.setResult("failure");
            }
           
            return result;
        }
         /**
          * 根据用户ID删除用户
          * @param id
          * @return 
          */
         @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
         public @ResponseBody
         Object deleteUserById(@PathVariable int id) {
             OnlyResultDTO result = new OnlyResultDTO();
             User user_old = userService.getUserById(id);
             if(user_old!=null){
                 userService.deleteUser(id);
                 result.setResult("success");
             }else{
                 result.setResult("failure");
             }
           
            return result;
         }
}
