/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.inhand.common.httpclient;

import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;

public class HttpRequest {

	/** HTTP GET method */
	public static final String METHOD_GET = "GET";

	/** HTTP POST method */
	public static final String METHOD_POST = "POST";

	/**
	 * �������url
	 */
	private String url = null;

	/**
	 * Ĭ�ϵ�����ʽ
	 */
	private String method = METHOD_POST;

	private int timeout = 0;

	private int connectionTimeout = 0;

	/**
	 * Post��ʽ����ʱ��װ�õĲ���ֵ��
	 */
	private NameValuePair[] parameters = null;
	
	private Map body = null;
	
	private String csvBody = null;

	/**
	 * Get��ʽ����ʱ��Ӧ�Ĳ���
	 */
	private String queryString = null;

	/**
	 * Ĭ�ϵ�������뷽ʽ
	 */
	private String charset = "GBK";

	/**
	 * �����𷽵�ip��ַ
	 */
	private String clientIp;

	/**
	 * ���󷵻صķ�ʽ
	 */
	private HttpResultType resultType = HttpResultType.BYTES;

	public HttpRequest(HttpResultType resultType) {
		super();
		this.resultType = resultType;
	}

	/**
	 * @param clientIp
	 *            The clientIp to set.
	 */
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public NameValuePair[] getParameters() {
		return parameters;
	}

	public void setParameters(NameValuePair[] parameters) {
		this.parameters = parameters;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return Returns the charset.
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * @param charset
	 *            The charset to set.
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	public HttpResultType getResultType() {
		return resultType;
	}

	public void setResultType(HttpResultType resultType) {
		this.resultType = resultType;
	}

	public Map getBody() {
		return body;
	}

	public void setBody(Map body) {
		this.body = body;
	}

	public String getCsvBody() {
		return csvBody;
	}

	public void setCsvBody(String csvBody) {
		this.csvBody = csvBody;
	}
	

}
