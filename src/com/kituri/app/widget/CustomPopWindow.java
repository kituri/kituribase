package com.kituri.app.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

public class CustomPopWindow {
	private PopupWindow mMenuPopupWindow;

	public View initView(Context context, int layoutId) {
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(layoutId, null);
		return view;
	}

	public PopupWindow showMenuPopupWindow(View view,
			final PopupWindow myWindow, View parentView, int styleId, int yPos) {
		if (mMenuPopupWindow == null) {
			mMenuPopupWindow = new PopupWindow(view,
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		// 使其聚集
		mMenuPopupWindow.setFocusable(false);
		// 设置允许在外点击消失
		mMenuPopupWindow.setOutsideTouchable(false);

		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		// mMenuPopupWindow.setBackgroundDrawable(new BitmapDrawable());

		// // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
		// int xPos = Env.getScreenWidth() / 2 - mPopupWindow.getWidth() / 2 -
		// mPopupWindow.getWidth();

		mMenuPopupWindow.setAnimationStyle(styleId);

		int myYPos = yPos - mMenuPopupWindow.getHeight();
		mMenuPopupWindow.showAtLocation(parentView, Gravity.NO_GRAVITY, 0,
				myYPos);

		mMenuPopupWindow.update();

		mMenuPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				if (myWindow != null)
					myWindow.dismiss();
			}
		});

		return mMenuPopupWindow;
	}
}