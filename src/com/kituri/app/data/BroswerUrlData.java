package com.kituri.app.data;

import java.util.HashMap;
import java.util.Map;


public class BroswerUrlData extends Entry{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5685058871733367852L;

	//public static final String EXTRA_BROSWER_PROTOCOL_HEADER = "com.kituri.app.intent.extra.protocol.header";
	
	public static final String EXTRA_BROSWER_PROTOCOL_HEADER_CLOSE = "close";
	public static final String EXTRA_BROSWER_PROTOCOL_HEADER_ORDERCREATE = "ordercreate";
	public static final String EXTRA_BROSWER_PROTOCOL_HEADER_SHARE = "share";
	//public static final String EXTRA_BROSWER_PROTOCOL_VALUE = "com.kituri.app.intent.extra.protocol.value";
	
	//public static final String EXTRA_BROSWER_PROTOCOL_KEY = "com.kituri.app.intent.extra.protocol.key";
	public static final String EXTRA_BROSWER_PROTOCOL_KEY_IS_PAY = "is_pay";
	public static final String EXTRA_BROSWER_PROTOCOL_KEY_ID = "id";
	public static final String EXTRA_BROSWER_PROTOCOL_KEY_PTID = "ptId";
	public static final String EXTRA_BROSWER_PROTOCOL_KEY_TYPE = "type";
	
	public BroswerUrlData(){
		mProtocolHeader = "";
		mProtocolContent = "";
		mProtocolData = new HashMap<String, String>();
//		mProtocolKey = "";
//		mProtocolValue = "";
		
	}
	
	//http://id=3
	//http
	String mProtocolHeader;
	//id=3
	String mProtocolContent;
//	//id
//	String mProtocolKey;
//	//3
//	String mProtocolValue;
	
	Map<String, String> mProtocolData;
	
	public Map<String, String> getProtocolData() {
		return mProtocolData;
	}
	public void setProtocolData(Map<String, String> mProtocolData) {
		this.mProtocolData = mProtocolData;
	}
	public String getProtocolHeader() {
		return mProtocolHeader;
	}
	public void setProtocolHeader(String mProtocolHeader) {
		this.mProtocolHeader = mProtocolHeader;
	}
	public String getProtocolContent() {
		return mProtocolContent;
	}
	public void setProtocolContent(String mProtocolContent) {
		this.mProtocolContent = mProtocolContent;
	}
//	public String getProtocolKey() {
//		return mProtocolKey;
//	}
//	public void setProtocolKey(String mProtocolKey) {
//		this.mProtocolKey = mProtocolKey;
//	}
//	public String getProtocolValue() {
//		return mProtocolValue;
//	}
//	public void setProtocolValue(String mProtocolValue) {
//		this.mProtocolValue = mProtocolValue;
//	}
	
	
}
