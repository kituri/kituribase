package com.kituri.app.widget;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class CustomViewPager extends ViewPager{
      public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public CustomViewPager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
		if(android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.HONEYCOMB) {
			if (v != this && v instanceof ViewPager) {
				return true;
			}
		}
		return super.canScroll(v, checkV, dx, x, y);
    }
    
}