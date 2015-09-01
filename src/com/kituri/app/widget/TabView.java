package com.kituri.app.widget;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @author kituri
 *
 * tab widget
 */

public class TabView<T> extends RelativeLayout {

	private HashMap<T, View> mViews;
	
	public TabView(Context context) {
		this(context, null);
		// makeLayout(context);
	}

	public TabView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// makeLayout(context);
	}

	public TabView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		// makeLayout(context);
	}

	
	private void init(){
		mViews = new HashMap<T, View>();
	}
	
	//LayoutInflater.from(getContext()).inflate(R.layout.mall_index_item_brand, null);
	
	public void addTabGroup(T tag, View view) {
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		addTab(tag, view, layoutParams);
		
	}
	
	public void addTab(T tag, View view) {
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		addTab(tag, view, layoutParams);
	}

	public void addTab(T tag, View view, LayoutParams layoutParams) {
		if (mViews.get(tag) != null) {
			return;
		}
		if(mViews.size() > 1){
			view.setVisibility(View.GONE);
		}else{
			view.setVisibility(View.VISIBLE);
		}
		
		mViews.put(tag, view);

		if(layoutParams != null){
			this.addView(view, layoutParams);
		}else{
			this.addView(view);
		}
	}
	
	public void removeTab(T tag) {
		if (mViews.get(tag) == null) {
			return;
		}
		this.removeView(mViews.get(tag));
		mViews.remove(tag);
	}

	public void showTab(T tag) {
		if (mViews.get(tag) == null) {
			return;
		}
		Iterator<java.util.Map.Entry<T, View>> iter = mViews.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<T, View> entry = (Map.Entry<T, View>) iter.next();
			T key = entry.getKey();
			if(key.equals(tag)){
			View view = entry.getValue();
			view.setVisibility(View.VISIBLE);
			}else{
				View view = entry.getValue();
				view.setVisibility(View.GONE);
			}
		}
		//Logger.i("当前tab显示的页面为：" + tag);
	}

	public T getShowTabTag(){
		Iterator<java.util.Map.Entry<T, View>> iter = mViews.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<T, View> entry = (Map.Entry<T, View>) iter.next();
			T key = entry.getKey();
			View view = entry.getValue();
			if(view.getVisibility() == View.VISIBLE){
				return key;
			}
		}
		return null;
	}
	
	public View getView(T tag){
		return mViews.get(tag);
	}
	
}
