/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.good.entity;

import com.shiguo.common.entity.AbstractEntity;

/**
 *商品分类表
 * @author shixj
 */
public class GoodsType extends AbstractEntity{

   private Long id;
   private String name;//分类名称
   private String descript;//描述
   private String typeOrder;//排序

   @Override
    public Long getId() {
        return id;
    }
   @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getTypeOrder() {
        return typeOrder;
    }

    public void setTypeOrder(String typeOrder) {
        this.typeOrder = typeOrder;
    }

   
   
    
}
