package com.kituri.app.model;

import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class KituriAnimation {

	static public void ScaleOutAnimation(View view) {
		ScaleAnimation myAnimation_Scale = new ScaleAnimation(1.2f, 1.0f, 1.3f,
				1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		myAnimation_Scale.setInterpolator(new AccelerateInterpolator());
		AnimationSet aa = new AnimationSet(true);
		aa.addAnimation(myAnimation_Scale);
		aa.setDuration(500);

		view.startAnimation(aa);
	}

	static public void ScaleInAnimation(View view) {

		ScaleAnimation myAnimation_Scale = new ScaleAnimation(1.0f, 0.0f, 1.0f,
				0.0f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		myAnimation_Scale.setInterpolator(new AccelerateInterpolator());
		// 缩小Layout的类,在动画结束需要从父View移除它
		AnimationSet aa = new AnimationSet(true);
		aa.addAnimation(myAnimation_Scale);
		aa.setDuration(500);

		view.startAnimation(aa);
	}

	// 交换两个View的位置动画
	static public void slideview(final View formView, final View toView) {

		// Logger.i("slideview 已被执行");

		TranslateAnimation animation = new TranslateAnimation(toView.getLeft(),
				toView.getTop(), 0, 0);
		animation.setInterpolator(new OvershootInterpolator());
		animation.setDuration(200);
		// 延迟启动
		// animation.setStartOffset(delayMillis);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// int left = formView.getLeft()+(int)(p2-p1);
				// int top = formView.getTop();
				// int width = formView.getWidth();
				// int height = formView.getHeight();
				// formView.clearAnimation();
				// formView.layout(left, top, left+width, top+height);
			}
		});
		formView.startAnimation(animation);
	}

	public static void startAnimation(View view, Context context, int resId) {
		Animation animation = AnimationUtils.loadAnimation(context, resId);
		view.startAnimation(animation);
	}
	
	public static void startAnimation( Context context, View view, int resId, AnimationListener animationListener) {
		Animation animation = AnimationUtils.loadAnimation(context, resId);
		animation.setFillAfter(true);
		animation.setAnimationListener(animationListener);
		view.startAnimation(animation);
	}
	
	public static void startAnimation(Context context, View view, Animation animation, AnimationListener animationListener){
		animation.setAnimationListener(animationListener);
		view.startAnimation(animation);
	}
	
}
