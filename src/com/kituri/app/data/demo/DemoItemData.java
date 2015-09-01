package com.kituri.app.data.demo;

import com.kituri.app.data.Entry;
import com.kituri.app.utils.PhotoUtils.Photoable;

public class DemoItemData extends Entry{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2274659657137823424L;
	private String picUrl;
	private int resId;
	private int index;
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getResId() {
		return resId;
	}

	public void setResId(int resId) {
		this.resId = resId;
	}

	public String getPicUrl() {
		return picUrl; 	
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	@Override
	public boolean equals(Object obj){		
		if(obj != null){
			if(obj instanceof DemoItemData){
				DemoItemData data = (DemoItemData) obj;
				if(data.getPicUrl().equals(getPicUrl())){
					return true;
				}
			}
		}		
		return false;
	}
	
}
