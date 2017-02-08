/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.user.dto;

/**
 *
 * @author shixj
 */
public class WXUserAddressBean {
     private String receiveName;//收货人名称
     private String receiveGender;//收货人性别
     private String receivePhone;//收货人手机号
     private String receiveAddress;//收货人地址
     private String enabled;//0 可用  1不在配送范围
     private String openId;

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveGender() {
        return receiveGender;
    }

    public void setReceiveGender(String receiveGender) {
        this.receiveGender = receiveGender;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
     
     
}
