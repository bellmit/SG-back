/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.good.entity;

import com.shiguo.common.entity.AbstractEntity;

/**
 *
 * @author lenovo
 */
public class Test extends AbstractEntity{
    private Long id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
    
}
