/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.inhand.common.smart.model;

/**
 *
 * @author liqiang
 */
public class VipPay {
    
    private Integer accept;
    private String vipInfoUrl;   //获取用户会员信息接口
    private String tradeNoticeUrl;  //发送会员支付交易通知
    private String shipmentNoticeUrl;  //出货通知
    
    private String appId;
    private String appSecret;
    private String registerUrl;
    
    private String mode;   //方式  1,用自己的支付页面    2,统一支付页面

    public String getRegisterUrl() {
        return registerUrl;
    }

    public void setRegisterUrl(String registerUrl) {
        this.registerUrl = registerUrl;
    }

    
    
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    
    
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }   
       

    public Integer getAccept() {
        return accept;
    }

    public void setAccept(Integer accept) {
        this.accept = accept;
    }
    
    public String getVipInfoUrl() {
        return vipInfoUrl;
    }

    public void setVipInfoUrl(String vipInfoUrl) {
        this.vipInfoUrl = vipInfoUrl;
    }

    public String getTradeNoticeUrl() {
        return tradeNoticeUrl;
    }

    public void setTradeNoticeUrl(String tradeNoticeUrl) {
        this.tradeNoticeUrl = tradeNoticeUrl;
    }

    public String getShipmentNoticeUrl() {
        return shipmentNoticeUrl;
    }

    public void setShipmentNoticeUrl(String shipmentNoticeUrl) {
        this.shipmentNoticeUrl = shipmentNoticeUrl;
    }
    
    
}
