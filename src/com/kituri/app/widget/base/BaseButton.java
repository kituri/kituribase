package com.kituri.app.widget.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class BaseButton extends Button{
		
	//private BaseOnClickListener mBaseOnClickListener;
	//private OnClickListener mOnClickListener;
	
	public BaseButton(Context context) {
		super(context);
	}

	public BaseButton(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public BaseButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	public void setOnClickListener(final OnClickListener listener){
		super.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listener.onClick(v);
				onBaseClick();
			}
		});
	}
	
	private void onBaseClick(){
		//Toast.makeText(getContext(), getContentDescription(), Toast.LENGTH_SHORT).show();
	}
	
}
