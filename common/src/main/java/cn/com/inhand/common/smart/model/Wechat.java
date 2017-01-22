package cn.com.inhand.common.smart.model;

/**
 *
 * @author shixj
 *
 */
public class Wechat {

    private String appID;
    private String appSecret;
    private String mchId;
    private String mchType;
    private String subMchId;
    private String subAppId;
    private String clientSecret;
    private String token;
    private String encodingAESKey;
    private String encodingAESStyle;
    private String callback_url;
    private String shipmentNotice;
    private String refundKey;
    private Integer certificate;
    private String refundNoticeUrl;
    private String tradeCodeNoticeUrl;
    
    private String style;//支付方式  1扫码支付  2公众号支付
    
    private String counterfee;//手续费

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    
    
    
    public String getCounterfee() {
        return counterfee;
    }

    public void setCounterfee(String counterfee) {
        this.counterfee = counterfee;
    }


    public String getRefundKey() {
        return refundKey;
    }

    public void setRefundKey(String refundKey) {
        this.refundKey = refundKey;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEncodingAESKey() {
        return encodingAESKey;
    }

    public void setEncodingAESKey(String encodingAESKey) {
        this.encodingAESKey = encodingAESKey;
    }

    public String getEncodingAESStyle() {
        return encodingAESStyle;
    }

    public void setEncodingAESStyle(String encodingAESStyle) {
        this.encodingAESStyle = encodingAESStyle;
    }

    public String getCallback_url() {
        return callback_url;
    }

    public void setCallback_url(String callback_url) {
        this.callback_url = callback_url;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getShipmentNotice() {
        return shipmentNotice;
    }

    public void setShipmentNotice(String shipmentNotice) {
        this.shipmentNotice = shipmentNotice;
    }

    public Integer getCertificate() {
        return certificate;
    }

    public void setCertificate(Integer certificate) {
        this.certificate = certificate;
    }

    public String getRefundNoticeUrl() {
        return refundNoticeUrl;
    }

    public void setRefundNoticeUrl(String refundNoticeUrl) {
        this.refundNoticeUrl = refundNoticeUrl;
    }

    public String getTradeCodeNoticeUrl() {
        return tradeCodeNoticeUrl;
    }

    public void setTradeCodeNoticeUrl(String tradeCodeNoticeUrl) {
        this.tradeCodeNoticeUrl = tradeCodeNoticeUrl;
    }

    public String getMchType() {
        return mchType;
    }

    public void setMchType(String mchType) {
        this.mchType = mchType;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getSubAppId() {
        return subAppId;
    }

    public void setSubAppId(String subAppId) {
        this.subAppId = subAppId;
    }
    
}
