/**
 * 
 */
package com.kituri.app.model;

import java.io.Serializable;

/**
 * @author Kituri
 * 
 */
public class Intent extends android.content.Intent implements Serializable {

	private static final long serialVersionUID = -50553432991124L;

	public Intent(String action){
		super(action);
	}
	
	public Intent(){
		super();
	}
	
	public static final String ACTION_DETAIL_PICS = "kituridemo.intent.action.detail.pics";
	
	
	public static final String EXTRA_DETAIL_PICS = "kituridemo.intent.extra.detail.pics";
	public static final String EXTRA_PHOTOS_RECT = "kituridemo.intent.photos.rect";
	
}
