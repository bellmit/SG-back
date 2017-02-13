/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.order.dto;

import java.util.List;

/**
 *
 * @author shixj
 */
public class OrderStatisticsBean {
    private float priceT;
    private Long countT;
    private List<Object> price;
    private List<Object> count;
    private List<Object> statisticTime; 
    
    public OrderStatisticsBean(float priceT,Long countT,List<Object> price,List<Object> count,List<Object> statisticTime) {
         this.priceT = priceT;
         this.countT = countT;
         this.price = price;
         this.count = count;
         this.statisticTime = statisticTime;
    }

    public float getPriceT() {
        return priceT;
    }

    public void setPriceT(float priceT) {
        this.priceT = priceT;
    }

   

    public Long getCountT() {
        return countT;
    }

    public void setCountT(Long countT) {
        this.countT = countT;
    }

    public List<Object> getPrice() {
        return price;
    }

    public void setPrice(List<Object> price) {
        this.price = price;
    }

    public List<Object> getCount() {
        return count;
    }

    public void setCount(List<Object> count) {
        this.count = count;
    }

    

   

    public List<Object> getStatisticTime() {
        return statisticTime;
    }

    public void setStatisticTime(List<Object> statisticTime) {
        this.statisticTime = statisticTime;
    }

   

    
}
