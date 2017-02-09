/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.business.dto;

/**
 *
 * @author shixj
 */
public class BusinessBean {
    private String number;//商家编号
    private String name;//商家名称
    private String phone;//客服电话
    private String address;//商家地址
    private String servicetime;//配送时间
    private String service;//配送服务
    private String activity;//促销活动

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getServicetime() {
        return servicetime;
    }

    public void setServicetime(String servicetime) {
        this.servicetime = servicetime;
    }

    

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
    
}
