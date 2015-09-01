package com.kituri.app.ui;


import android.content.Intent;

public class BaseFragment extends FixedOnActivityResultBugFragment{
	
    @Override
	public void onPause() {  
        super.onPause();  
        //MobclickAgent.onPageEnd(fragmentName); 
    }  
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		MobclickAgent.onPageStart(fragmentName); 
//    	SharedPreference.getInstance(KituriApplication.getInstance()).recordTrace(SharedPreference.TRACE_ON_START, fragmentName);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onStop(){
		super.onStop();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
