/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.order.entity;

import com.shiguo.common.entity.AbstractEntity;

/**
 *订单表
 * @author shixj
 */
public class Order extends AbstractEntity{
    private Long id;
    private String openId;
    
    private String receiveName;//收货人名称
    private String receiveGender;//收货人性别
    private String receivePhone;//收货人手机号
    private String receiveAddress;//收货人地址
    
    private String orderNo;          //订单号 唯一 
    private String tradeNo;          //交易号 
    private String goodsInfo;        //商品信息   商品id,商品名称,商品分类ID,商品分类名称,交易价格,数量;商品id,商品名称,商品分类ID,商品分类名称,交易价格,数量;
    private Long   gtotalPrice;      //商品总金额
    private Long   integral;      //积分扣除 10积分=1元
    private Long   empirical;       //此次累计的经验值
    private Long   payPrice;         //最终支付金额
    private String state;            //订单状态   1待支付  2支付完成,等待商户确认 3商户已接单 4骑手正赶往商家  5骑手已取货 6已送达
    
    private Long payTime;            //支付时间
    private Long createTime;         //创建时间
    private Long traCreateTime;      //交易创建时间
    
    //退款信息
    private Long refundTime;           //申請退款时间
    private String refundOrderNo;      //退款商户编号  唯一
    private Long refundFee;     //退款金额
    private Long refundStatus;  //退款狀態  1：正在退款  2：退款成功 3：支付失败
    private String refundTradeNo;      //退款交易号
    

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public Long getGtotalPrice() {
        return gtotalPrice;
    }

    public void setGtotalPrice(Long gtotalPrice) {
        this.gtotalPrice = gtotalPrice;
    }

    public Long getIntegral() {
        return integral;
    }

    public void setIntegral(Long integral) {
        this.integral = integral;
    }

    public Long getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Long payPrice) {
        this.payPrice = payPrice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getPayTime() {
        return payTime;
    }

    public void setPayTime(Long payTime) {
        this.payTime = payTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getTraCreateTime() {
        return traCreateTime;
    }

    public void setTraCreateTime(Long traCreateTime) {
        this.traCreateTime = traCreateTime;
    }

    public Long getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Long refundTime) {
        this.refundTime = refundTime;
    }

    public String getRefundOrderNo() {
        return refundOrderNo;
    }

    public void setRefundOrderNo(String refundOrderNo) {
        this.refundOrderNo = refundOrderNo;
    }

    public Long getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Long refundFee) {
        this.refundFee = refundFee;
    }

    public Long getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Long refundStatus) {
        this.refundStatus = refundStatus;
    }

   

    public String getRefundTradeNo() {
        return refundTradeNo;
    }

    public void setRefundTradeNo(String refundTradeNo) {
        this.refundTradeNo = refundTradeNo;
    }

    public Long getEmpirical() {
        return empirical;
    }

    public void setEmpirical(Long empirical) {
        this.empirical = empirical;
    }
    
    
    
}
