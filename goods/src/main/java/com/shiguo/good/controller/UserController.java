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
import com.shiguo.good.dto.UserBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiguo.common.vo.Page;
import com.shiguo.good.entity.User;
import com.shiguo.good.service.UserService;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("sapi/user")
public class UserController {
	 private final static Logger logger = LoggerFactory.getLogger(UserController.class);
	 @Autowired
         private UserService userService;
         @Autowired
         ObjectMapper mapper;
         
         /**
          * 添加
          * @param bean
          * @return 
          */
         @RequestMapping(value = "/add", method = RequestMethod.POST)
         public @ResponseBody
         Object createUser(
           @Valid @RequestBody UserBean bean)throws Exception {
            User user = mapper.convertValue(bean, User.class);
            long timestamp = DateUtils.getUTC();
            user.setCreateTime(timestamp);
            user.setUpdateTime(timestamp);
            userService.save(user);
            OnlyResultDTO result = new OnlyResultDTO();
            result.setResult("success");
            return result;
         }
         
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
                 @RequestParam(value = "name", required = false) String name)throws Exception {
             UserBean bean = new UserBean();
             if(name!=null && !name.equals("")){
                bean.setName(name);
             }
             Map<String, Object> params = new HashMap<String, Object>();
             params.put("name", name);
             params.put("start", cursor);
             params.put("pagesize", limit);
                     
             Page<User> user = userService.findByPage(params,cursor, limit);
             long total = userService.findCountByParams(params);
	     return new BasicResultDTO(total, cursor, limit,user.getRows());
         }
         
         /**
          * 根据用户id查询用户信息
          * @param id
          * @return 
          */
         @RequestMapping(value = "/{id}", method = RequestMethod.GET)
         public @ResponseBody
         Object getUserById(@PathVariable Long id)throws Exception {
            User user = userService.findByPrimaryKey(id);
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
         Object updateUserById(@PathVariable Long id,
             @RequestBody UserBean bean)throws Exception {
            User user = mapper.convertValue(bean, User.class);
            User user_old = userService.findByPrimaryKey(id);
            user.setId(user_old.getId());
            long timestamp = DateUtils.getUTC();
            user.setUpdateTime(timestamp);
            userService.modify(user);
            OnlyResultDTO result = new OnlyResultDTO();
            result.setResult("success");
            return result;
        }
         /**
          * 根据用户ID删除用户
          * @param id
          * @return 
          */
         @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
         public @ResponseBody
         Object deleteUserById(@PathVariable Long id)throws Exception {
             OnlyResultDTO result = new OnlyResultDTO();
             User user_old = userService.findByPrimaryKey(id);
             if(user_old!=null){
                 userService.removeByPrimaryKey(id);
                 result.setResult("success");
             }else{
                 result.setResult("failure");
             }
           
            return result;
         }
}
