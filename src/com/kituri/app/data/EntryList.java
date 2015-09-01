/**
 * 
 */
package com.kituri.app.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author kituri
 * 继承用，一般不用
 */
public class EntryList extends ArrayList<Entry> implements Serializable, Viewable{

	private static final long serialVersionUID = -1797215192050958113L;

	private String mViewName;
//	private boolean mIsRanking = false;
//	private int mRankType;
//	private boolean mIsFinish;
	
	public String getViewName() {
		return mViewName;
	}

	public void setViewName(String viewName) {
		this.mViewName = viewName;
	}
	
//	public void setRanking(boolean ranking) {
//		mIsRanking = ranking;
//	}
//	
//	public boolean isRanking() {
//		return mIsRanking;
//	}
//	
//	public void setRankType(int type) {
//		mRankType = type;
//	}
//	
//	public int getRankType() {
//		return mRankType;
//	}
//
//	public void setFinish(boolean finish) {
//		this.mIsFinish = finish;
//	}
//
//	public boolean isFinish() {
//		return mIsFinish;
//	}
}
