//package com.kituri.app.ui;
//
//import android.app.TabActivity;
//import android.os.Bundle;
//import android.view.View;
//
//import com.kituri.app.LeHandler;
//
//public class BaseTabActivity extends TabActivity{
//
//	private BaseUI mBaseUI;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState){
//		super.onCreate(savedInstanceState);
//		mBaseUI = new BaseUI(this);
//		mBaseUI.onCreate();
//	}
//	
//	@Override
//	public void setContentView(int resId) {
//		super.setContentView(resId);
//		mBaseUI.setContentView();
//		mBaseUI.initListener();
//		
//	}
//	
//	@Override
//	public void setContentView(View view) {
//		super.setContentView(view);
//		mBaseUI.setContentView();
//		mBaseUI.initListener();
//	}
//	
//	protected void showLoading(){
//		mBaseUI.showLoading();
//	}
//	
//	protected void dismissLoading(){
//		mBaseUI.dismissLoading();
//	}
//	
//    @Override  
//    protected void onPause() {  
//    	mBaseUI.onPause();
//        super.onPause();  
//    }  
//	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		mBaseUI.onResume();
//		super.onResume();
//	}
//	
//	@Override
//	protected void onStop(){
//		mBaseUI.onStop();
//		super.onStop();
//	}
//	
//	@Override
//	protected void onStart(){
//		mBaseUI.onStart();
//		super.onStart();
//	}
//	
//	@Override
//	protected void onDestroy() {
//		// TODO Auto-generated method stub
//		mBaseUI.onDestroy();
//		super.onDestroy();
//	}
//	
//	//TitleBar相关
//	
////	public void backOnClick(View v){
////		mBaseUI.backOnClick();
////	}
//	
////	public void backOnClick(){
////		
////	}
//    
//	public void setTopBarTitle(String title){
//		mBaseUI.setTopBarTitle(title);
//	}
//	
//	public void setTopBarTitle(int resId){
//		mBaseUI.setTopBarTitle(resId);
//	}
//		
//	public void setRightText(String text){
//		mBaseUI.setRightText(text);
//	}
//	
//	public void setRightBackgroundResource(Integer resid){
//		mBaseUI.setRightBackgroundResource(resid);
//	}
//	
//	public void goneLeftButton(){
//		mBaseUI.goneLeftButton();
//	}
//	
//	protected void showLoading(final View view) {
//		LeHandler.getInstance().post(new Runnable() {
//
//			@Override
//			public void run() {
//				view.setVisibility(View.VISIBLE);
//			}
//		});
//	}
//
//	protected void dismissLoading(final View view) {
//		LeHandler.getInstance().post(new Runnable() {
//
//			@Override
//			public void run() {
//				view.setVisibility(View.GONE);
//			}
//		});
//
//	}
////    protected void onStart() {
////    	super.onStart();  
////    	if(mHomeWatcher != null){
////        	mHomeWatcher.startWatch();
////    	}
////	}
//    
//}
