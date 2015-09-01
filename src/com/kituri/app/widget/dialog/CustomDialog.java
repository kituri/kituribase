package com.kituri.app.widget.dialog;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.kituri.app.data.Entry;
import com.kituri.app.widget.Populatable;
import com.kituri.app.widget.Selectable;
import com.kituri.app.widget.SelectionListener;

public class CustomDialog extends Dialog {

	//private Context mContext;
	private View mView;
	private View mAnimView;
	private Animation mAnimationIn;
	private Animation mAnimationOut;
	

	public CustomDialog(Context context, View view) {
		this(context, view, 0, 0);
	}

	public CustomDialog(Context context, View view, int inAnimResId, int outAnimResId) {
		this(context, view, inAnimResId, outAnimResId, 0);
	}
	
	public CustomDialog(Context context, View view, int inAnimResId, int outAnimResId, int animViewId) {
		super(context, R.style.Theme_Translucent_NoTitleBar);
		this.mView = view;
		if(mView != null && animViewId != 0){
			mAnimView = mView.findViewById(animViewId);
		}
		if(inAnimResId != 0){
			mAnimationIn = AnimationUtils.loadAnimation(context, inAnimResId);
		}
		if(outAnimResId != 0){
			mAnimationOut = AnimationUtils.loadAnimation(context, outAnimResId);
		}		
	}
	
//	protected View getCustomView(){
//		return mView;
//	}
	
	// public CustomDialog(Context context, int theme,View view){
	// super(context, theme);
	// this.mContext = context;
	// this.mView = view;
	// }
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		super.setCancelable(true);

		Window window = this.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		lp.gravity = Gravity.CENTER;
		window.setAttributes(lp);
		// this.onWindowAttributesChanged(lp);
		//LinearLayout mainView = getMainView(getContext());
		//mainView.addView(mView);
		mView.setBackgroundColor(Color.BLACK);
		mView.getBackground().setAlpha(180);
		this.setContentView(mView);

	}

	private LinearLayout getMainView(Context context) {
		LinearLayout view = new LinearLayout(context);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		// layoutParams.
		view.setLayoutParams(layoutParams);
		view.setBackgroundColor(Color.BLACK);
		view.getBackground().setAlpha(180);
		return view;
	}

	public void setSelectionListener(SelectionListener<Entry> mListener) {
		if (mView instanceof Selectable) {
			((Selectable<Entry>) mView).setSelectionListener(mListener);
		}
	}

	public void populate(Entry data) {
		if (mView instanceof Populatable) {
			((Populatable<Entry>) mView).populate(data);
		}
	}
	
	@Override
	public void show(){
		//mView
		//show(mAnimationIn);
		if(mAnimationIn != null){
			if(mAnimView != null){
				mAnimView.startAnimation(mAnimationIn);
			}else{
				mView.startAnimation(mAnimationIn);
			}			
		}
		super.show();
	}
	
	@Override
	public void dismiss(){
		if(mAnimationOut != null){
			if(mAnimView != null){
				mAnimView.startAnimation(mAnimationOut);
			}else{
				mView.startAnimation(mAnimationOut);
			}			
		}
		super.dismiss();
	}
	
//	public Animation getAnimationIn() {
//		return mAnimationIn;
//	}
//
//	public Animation getAnimationOut() {
//		return mAnimationOut;
//	}
	
	// @Override
	// public void show(){
	// super.show();
	// }
}