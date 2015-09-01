package com.kituri.app.push;

import android.content.Context;

/**
 * PsPushUserData toolkit class
 * 
 * {@hide}
 */
public class PsPushUserData {

	//查看大图模式 alreadyShowPicturesTooLargeHint
	public static final String KEY_ALREADY_SHOW_PIC_TOO_LARGE_HINT = "key_already_show_pic_too_large_hint";
	

	public static void setData(Context context, String KEY, Object value){
		PsPushUserDao.getInstance(context).setData(KEY, value);
	}
	
	// public <T extends View> void display(T container, String uri) {	
	public static <T extends Object> T getData(Context context, String KEY, T defData){
		return (T) PsPushUserDao.getInstance(context).getData(KEY, defData);
	}

}
