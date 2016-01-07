package com.kituri.app.widget.doubleClick;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class DoubleClickImageView extends ImageView{

	public DoubleClickImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public DoubleClickImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public DoubleClickImageView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	private OnDoubleClickListener mOnDoubleClickListener;
	
	private static final int DELAY_MILLIS = 500;
	
	public void setOnDoubleClickListener(OnDoubleClickListener onDoubleClickListener){
		this.mOnDoubleClickListener = onDoubleClickListener;
		setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mOnDoubleClickListener != null){
					doubleEven(DELAY_MILLIS);
				}
			}
		});
	}
	
	private void doubleEven(int delayMillis) {
		// TODO Auto-generated method stub
		//repress_to_exit_app
		if(!dRunnable.getIsRun()){
			dRunnable.setIsRun(true);
			//KituriToast.toastShow(this, R.string.repress_to_exit_app);
			this.postDelayed(dRunnable , delayMillis);
		}else{
			if(mOnDoubleClickListener != null){
				mOnDoubleClickListener.onDoubleClick(this);
			}			
		}
	}
		
	
	private DoubleRunnable dRunnable = new DoubleRunnable();
	
	class DoubleRunnable implements Runnable {

		private Boolean isRun = false;
		
		
		public Boolean getIsRun() {
			return isRun;
		}

		public void setIsRun(Boolean isRun) {
			this.isRun = isRun;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			isRun = false;
		}
		
	}
	
}
