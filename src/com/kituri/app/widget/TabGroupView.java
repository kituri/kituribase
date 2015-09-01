package com.kituri.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * @author kituri
 *
 * tab widget
 */

public class TabGroupView extends RelativeLayout {

	private SparseArray<View> mViews;
	
	public TabGroupView(Context context) {
		this(context, null);
		// makeLayout(context);
	}

	public TabGroupView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// makeLayout(context);
	}

	public TabGroupView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		// makeLayout(context);
	}

	
	private void init(){
		mViews = new SparseArray<View>();
	}
	
	//LayoutInflater.from(getContext()).inflate(R.layout.mall_index_item_brand, null);
	
	public void setTabGroup(ViewGroup group) {
		mViews.clear();	
		for(int i = 0; i < group.getChildCount(); i++){
			View view = group.getChildAt(i);
			mViews.put(view.getId(), view);
			if(mViews.size() > 1){
				view.setVisibility(View.GONE);
			}else{
				view.setVisibility(View.VISIBLE);
			}
		}
		this.addView(group, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	public void setTabGroup(Integer layoutId) {
		setTabGroup((ViewGroup)LayoutInflater.from(getContext()).inflate(layoutId, null));
	}
	
	public void showNextTab() {
		showTab(getShowNewTabTag());
	}
	
	public void showTab(Integer tag) {
		if (mViews.get(tag) == null) {
			return;
		}
		for(int i = 0; i < mViews.size(); i++) {
			Integer key = mViews.keyAt(i);
			View view = mViews.get(mViews.keyAt(i));
			if(key.equals(tag)){
				view.setVisibility(View.VISIBLE);
			}else{
				view.setVisibility(View.GONE);
			}
		}
	}
	
	public Integer getShowNewTabTag(){
		for(int i = 0; i < mViews.size(); i++) {
			View view = mViews.get(mViews.keyAt(i));			
			if(view.getVisibility() == View.VISIBLE){
				if(i + 1 < mViews.size()){			
					return mViews.keyAt(i + 1);	
				}else{	
					return mViews.keyAt(0);
				}			
			}
		}
		return null;
	}
	public Integer getShowTabTag(){
		for(int i = 0; i < mViews.size(); i++) {
			View view = mViews.get(mViews.keyAt(i));
			if(view.getVisibility() == View.VISIBLE){
				return mViews.keyAt(i);
			}
		}
		return null;
	}
	
	public View getTabView(Integer tag){
		return mViews.get(tag);
	}
	
}
