/**
 * 
 */
package com.kituri.ams;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import android.annotation.SuppressLint;

/**
 * RequestParameters
 * @author  zhangbp zhang86-vip@163.com
 */

@SuppressLint("DefaultLocale")
public class AmsRequestParameters{

	private Map<String, String> mParameters;
	
	
	public AmsRequestParameters(){
		mParameters = new ConcurrentHashMap<String, String>();
	}
	
	public void put(String key, String value){
		this.mParameters.put(key, value);
	}
	
	public void remove(String key){
		this.mParameters.remove(key);
	}
	
	public String getValue(String key){
		String rlt = this.mParameters.get(key);
		return rlt;
	}
	
	
	public void putAll(AmsRequestParameters parameters){
		this.mParameters.putAll(parameters.mParameters);
	}
	
	public Map<String, String> getRequestParameters(){
		return mParameters;
	}
	
	public void putAll(Map<String, String> map){
		this.mParameters.putAll(map);
	}
	
//	public ArrayList<Header> getHeaderList(){
//		ArrayList<Header> headers = new ArrayList<Header>();
//		String key,value;
//		
//		Iterator<Entry<String, String>> iter = mParameters.entrySet().iterator();
//		
//		Entry<String, String> entry = null;
//		int i = 0;
//		while (iter.hasNext()) {
//			entry = (Entry<String, String>) iter.next();
//			key = entry.getKey();
//			value = entry.getValue();
//		}
//	}


	
	@SuppressLint("DefaultLocale")
	public String getRquestParam(){
		String key,value;
		StringBuffer sb = new StringBuffer();
		Iterator<Entry<String, String>> iter = mParameters.entrySet().iterator();
		
		Entry<String, String> entry = null;
		int i = 0;
		while (iter.hasNext()) {
			entry = (Entry<String, String>) iter.next();
			key = entry.getKey();
			value = entry.getValue();
			if(i != 0){
				sb.append("&");
			}
			i++;
			if(value != null){
				sb.append(key+"="+URLEncoder.encode(value));
			}
		}
		
		return sb.toString();
	}
	
	public void clear(){
		this.mParameters.clear();
	}
}
