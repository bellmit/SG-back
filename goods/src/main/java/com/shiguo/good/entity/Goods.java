/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.good.entity;

import com.shiguo.common.entity.AbstractEntity;
import java.sql.Blob;

/**
 *商品表
 * @author shixj
 */
public class Goods extends AbstractEntity{
    private Long id;
    private String name;           //商品名称
    private String imageName;      //商品图片名称
    private String typeID;         //商品分类
    private String typeName;       //商品分类名称
    private Long price;          //价格
    private String state;          //售卖状态  0售卖中  1暂停售卖
    private String descript;       //描述
    private String specifications;    //商品规格
    private Long CreateTime;
    private Long UpdateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

   

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long CreateTime) {
        this.CreateTime = CreateTime;
    }

    public Long getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(Long UpdateTime) {
        this.UpdateTime = UpdateTime;
    }

    
}
