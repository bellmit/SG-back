/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.wechat.factory.WeChatRefundFactory;

import cn.com.inhand.common.constant.Constant;
import com.shiguo.user.util.RequestHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author shixj
 */
@Component
public class WeChatRefundFactory {
    public String refund(String orderNo, String out_refund_no, int total_fee, int refund_fee,String mchId,String appId,String clientSecret) throws Exception {
        String result = "";
        RequestHandler requestHandler = new RequestHandler();
        requestHandler.setParameter("appid", appId);
        requestHandler.setParameter("mch_id", mchId);
        requestHandler.setParameter("nonce_str", WeChatUtil.getRandomString(7));
        requestHandler.setParameter("out_trade_no", orderNo);
        requestHandler.setParameter("out_refund_no", out_refund_no);
        requestHandler.setParameter("total_fee", total_fee + "");
        requestHandler.setParameter("refund_fee", refund_fee + "");
        requestHandler.setParameter("op_user_id", mchId);
        String reuqestXml = requestHandler.getRequestURL(clientSecret);
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        String filepath  = "/usr/local/wechat/cert/apiclient_cert.p12";
         File ff = new File(filepath);
        if (!ff.exists()) {
            return result;
        }
        FileInputStream instream = new FileInputStream(new File(filepath));//放退款证书的路径
        try {
            keyStore.load(instream, mchId.toCharArray());
        } catch (IOException ex) {
            Logger.getLogger(WeChatRefundFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(WeChatRefundFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(WeChatRefundFactory.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                instream.close();
            } catch (IOException ex) {
                Logger.getLogger(WeChatRefundFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        try {
            HttpPost httpPost = new HttpPost(Constant.WECHAT_ORDER_REFUND_URL);//退款接口
            StringEntity reqEntity = new StringEntity(reuqestXml);
            // 设置类型 
            reqEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(reqEntity);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
//                System.out.println(response.getStatusLine());
                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        result = result + text;
                    }
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return result;
    }
}
