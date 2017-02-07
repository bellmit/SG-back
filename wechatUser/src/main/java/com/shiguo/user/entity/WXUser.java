/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.user.entity;

import com.shiguo.common.entity.AbstractEntity;

/**
 *微信用户信息
 * @author shixj
 */
public class WXUser extends AbstractEntity{
     private Long id;
     private String openId;
     private String nickName;//昵称
     private String image;//头像
     private Long integration;//积分
     private Long empirical;//经验值
     private String levelId;//会员等级ID
     private String levelName;//会员等级名称

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getIntegration() {
        return integration;
    }

    public void setIntegration(Long integration) {
        this.integration = integration;
    }

    public Long getEmpirical() {
        return empirical;
    }

    public void setEmpirical(Long empirical) {
        this.empirical = empirical;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    

    
     
}
