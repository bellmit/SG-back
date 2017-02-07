/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.good.entity;

import com.shiguo.common.entity.AbstractEntity;

/**
 *会员信息表
 * @author shixj
 */
public class Vip extends AbstractEntity{
    private Long id;
    private String levelName;//会员等级名称
    private Long empiricalU;//经验值 上限
    private Long empiricalL;//经验值 下限

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Long getEmpiricalU() {
        return empiricalU;
    }

    public void setEmpiricalU(Long empiricalU) {
        this.empiricalU = empiricalU;
    }

    public Long getEmpiricalL() {
        return empiricalL;
    }

    public void setEmpiricalL(Long empiricalL) {
        this.empiricalL = empiricalL;
    }

    
}
