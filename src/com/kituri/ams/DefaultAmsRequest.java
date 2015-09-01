package com.kituri.ams;

import java.util.Map;

import org.json.JSONObject;

public class DefaultAmsRequest implements AmsRequest{
	
	private String url;
	//private Map<String, String> postData;
	private int mode;
	private JSONObject postData;
	
	public DefaultAmsRequest(String url, JSONObject postData, int mode){
		this.url = url;
		this.postData = postData;
		this.mode = mode;
	}
	
	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return url;
	}

	@Override
	public JSONObject getPost() {
		// TODO Auto-generated method stub
		return postData;
	}

	@Override
	public int getHttpMode() {
		// TODO Auto-generated method stub
		return mode;
	}

	@Override
	public String getPriority() {
		// TODO Auto-generated method stub
		return PRIORITY.DEFAULT;
	}

	@Override
	public String getRequestMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isUseUICache(){
		return false;
	}

	@Override
	public String getFileName() {
		return null;
	}

//	@Override
//	public String getJSON() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
}
