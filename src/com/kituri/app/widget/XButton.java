package com.kituri.app.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * 自定义Button
 * 
 * @author kituri
 */

public class XButton extends Button {

	private OnTouchListener mOutSideOnTouchListener;
	
	private OnTouchListener mOnTouchListener = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {

			if(mOutSideOnTouchListener != null){
				mOutSideOnTouchListener.onTouch(v, event);
			}
			
			Drawable drawable = v.getBackground();
			if (drawable == null)
				return false;

			drawable.mutate();
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				drawable.setColorFilter(Color.argb(100, 0, 0, 0), Mode.DST_IN); // 此处值可自行调整
				v.setBackgroundDrawable(drawable);
				break;
			case MotionEvent.ACTION_UP:
				drawable.clearColorFilter();
				v.setBackgroundDrawable(drawable);
				break;
			case MotionEvent.ACTION_CANCEL:
				drawable.clearColorFilter();
				v.setBackgroundDrawable(drawable);
				break;
			default:
				break;
			}

			return false;

		}
	}; 
	
	public XButton(Context context) {
		super(context);
		super.setOnTouchListener(mOnTouchListener);
	}

	public XButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		super.setOnTouchListener(mOnTouchListener);
	}

	public XButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		super.setOnTouchListener(mOnTouchListener);
	}

	@Override
	public void setOnTouchListener(OnTouchListener l){
		this.mOutSideOnTouchListener = l;
	}
	

}
