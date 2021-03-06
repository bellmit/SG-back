/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shixj.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 *
 * @author shixj
 */
public class User {
    private int id;
    private String password;
    private String name;
    private String email;
    private String phone;
    private Long createTime;
    private Long updateTime;

    public User() {
    }
    
    public User(int id, String password, String name,String email,String phone,Long createTime,Long updateTime) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

     public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
   

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
