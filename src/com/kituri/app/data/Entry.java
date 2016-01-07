/**
 * 
 */
package com.kituri.app.data;

import java.io.Serializable;

import com.kituri.app.model.Intent;
import com.kituri.app.utils.Utility;

import android.util.Log;


/**
 * @author Kituri
 *
 */
public class Entry implements Serializable, Viewable, Actionable, Selectionable, Indexable, EntryComparable, Cloneable{

	private static final long serialVersionUID = -594713265002520688L;
	
	private String mViewName;
	private Intent mIntent;
//	private long mDate;
//	private long mReplyCount;
	private Integer mEntryId;
	private String mName;
	//@Foreign(column = "parentId", foreign = "id")

//	private int id;
	private Boolean mSelectionable;
	private int mIndex;

	public Entry(Entry entry){
		this.mViewName = entry.getViewName();
		this.mIntent = entry.getIntent();
		this.mEntryId = entry.getEntryId();
		this.mName = entry.getName();
		this.mSelectionable = entry.getSelection();
		this.mIndex = entry.getIndex();
	}
	
	public Entry(){
		mSelectionable = false;
	}
	
	public String getViewName() {
		return mViewName;
	}
	
	public void setViewName(String viewName) {
		this.mViewName = viewName;
	}
	
	public void setIntent(Intent intent) {
		this.mIntent = intent;
	}

	public Intent getIntent() {
		return mIntent;
	}
	
//	public long getDate() {
//		return mDate;
//	}
//	
//	public void setDate(long date) {
//		this.mDate = date;
//	}
//	
//	public void setReplyCount(long count) {
//		this.mReplyCount = count;
//	}
//	
//	public long getReplyCount() {
//		return mReplyCount;
//	}
	
	public void setEntryId(Integer id) {
		this.mEntryId = id;
	}
	
	public Integer getEntryId() {
		return mEntryId;
	}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		this.mName = name;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Entry)) {
			return false;
		}
		Integer id = ((Entry)o).getEntryId();
		if (id != null && id == mEntryId) {
			return true;
		}
		return super.equals(o);
	}
	
	@Override
	public Boolean getSelection() {
		// TODO Auto-generated method stub
		return mSelectionable;
	}

	@Override
	public void setSelection(Boolean isSelection) {
		// TODO Auto-generated method stub
		this.mSelectionable = isSelection;
	}
	
	public void print(){
		Log.i(getClass().getName(), Utility.getClassPrint(this));
	}

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return mIndex;
	}

	@Override
	public void setIndex(int index) {
		// TODO Auto-generated method stub
		this.mIndex = index;
	}

	@Override
	public long entryCompare() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Object clone(){ 
		Entry o = null; 
        try{ 
            o = (Entry)super.clone(); 
        }catch(CloneNotSupportedException e){ 
            e.printStackTrace(); 
        } 
        return o; 
    } 


//	@Override
//	public int getId() {
//		// TODO Auto-generated method stub
//		return id;
//	}
//
//	@Override
//	public void setId(int id) {
//		// TODO Auto-generated method stub
//		this.id = id;
//	}
}
