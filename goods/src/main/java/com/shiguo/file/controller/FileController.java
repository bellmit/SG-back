/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.file.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *文件上传
 * @author shixj
 */
@Controller
@RequestMapping("sapi/file")
public class FileController {
    private final static Logger logger = LoggerFactory.getLogger(FileController.class);
    
    /**
     * 上传文件
     * @param req
     * @param resp
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public
    @ResponseBody
    Object uploadFile(
             HttpServletRequest req,
             HttpServletResponse resp)throws IOException {
       
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        Map mes = new HashMap();
        String name = "";
        String endName = "";
        try {
            List fileItems = upload.parseRequest(req);
            Iterator iter = fileItems.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                // 忽略其他不是文件域的所有表单信息
                if (!item.isFormField()) {
                    name = item.getName();
                    endName = name.substring(name.lastIndexOf(".") + 1);
                    try {
                        item.write(new File("/usr/share/nginx/html/file" + "/" + name));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    mes.put(item.getFieldName(), item.getString()); // 将前台界面是非文件域的信息存入Map中
                }
            }
        } catch (FileUploadException e1) {
            e1.printStackTrace();
        }
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", "success");
        result.put("fileName", name);
        return result;
    }
    
}
