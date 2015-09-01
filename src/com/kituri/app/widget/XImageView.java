package com.kituri.app.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

/**
 * 自定义ImageView
 * 
 * @author kituri
 */

public class XImageView extends ImageView {
	private OnTouchListener mOutSideOnTouchListener;
	
	private OnTouchListener mOnTouchListener = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {

			if(mOutSideOnTouchListener != null){
				mOutSideOnTouchListener.onTouch(v, event);
			}
			
			Drawable drawable = getDrawable();
			if (drawable == null){
				return false;
			}

			drawable.mutate();
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				drawable.setColorFilter(Color.argb(100, 0, 0, 0), Mode.DST_IN); // 此处值可自行调整
				setImageDrawable(drawable);
				break;
			case MotionEvent.ACTION_UP:
				drawable.clearColorFilter();
				setImageDrawable(drawable);
				break;
			case MotionEvent.ACTION_CANCEL:
				drawable.clearColorFilter();
				setImageDrawable(drawable);
				break;
			default:
				break;
			}

			return false;

		}
	}; 
	
	public XImageView(Context context) {
		super(context);
		super.setOnTouchListener(mOnTouchListener);
	}

	public XImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		super.setOnTouchListener(mOnTouchListener);
	}

	public XImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		super.setOnTouchListener(mOnTouchListener);
	}

	@Override
	public void setOnTouchListener(OnTouchListener l){
		this.mOutSideOnTouchListener = l;
	}
	

}
