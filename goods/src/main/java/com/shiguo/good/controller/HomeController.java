package com.shiguo.good.controller;

import cn.com.inhand.common.bean.ServerInfoBean;
import com.shiguo.good.entity.Test;
import com.shiguo.good.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import javax.annotation.Resource;


@Controller
public class HomeController {

    @Resource
    private TestService testService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Object home(Locale locale) throws Exception {
        
        Test test = new Test();
        test.setName("823824394234");
        testService.save(test);
        
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        String formattedDate = dateFormat.format(date);
        return new ServerInfoBean("goods", formattedDate);
    }

}
