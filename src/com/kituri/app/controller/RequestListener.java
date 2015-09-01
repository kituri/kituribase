/**
 * 
 */
package com.kituri.app.controller;

/**
 * @author Kituri
 */
public interface RequestListener {
	
	public static final int REQUEST_SUCCESS = 0;
	public static final int REQUEST_FAILED = 1;
	public static final int REQUEST_PRIVATE_ERROR = 2;
	public static final int REQUEST_SENSITIVE_WORD_NICKNAME = 3;
	public static final int REQUEST_SENSITIVE_WORD_JOB = 4;
	public static final int REQUEST_NICK_NAME_ALREADY_EXIST = 5;
	
	public void onResult(int isSuccess, Object data);
}
