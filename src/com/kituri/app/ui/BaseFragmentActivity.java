package com.kituri.app.ui;

import com.kituri.app.KituriApplication;
import com.kituri.app.push.PsPushUserData;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseFragmentActivity extends FragmentActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		KituriApplication.getInstance().addActivity(this);
	    //KituriApplication.getInstance().setActivity(this);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	

	protected void onStart() {
		// TODO Auto-generated method stub
		//setReplyCallBack();
//		if(!KituriApplication.getInstance().isAllActivityAlive() && !(this == KituriApplication.getInstance())){
//			PsAuthenServiceL.GetPsSystemStartupRequest(mActivity, PsPushUserData.getPushUserIdChannelId(mActivity), null);
//			PsPushUserData.setRestartCount(KituriApplication.getInstance(), 
//					PsPushUserData.getRestartCount(KituriApplication.getInstance())+ 1);
//			KituriApplication.getInstance().getMallStartUpRequest();
//		}
		super.onStart();
	}

	
    protected void onPause() {  
    	//MobclickAgent.onPause(this);
    	super.onPause();
    }  
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		KituriApplication.getInstance().addActivityStatus(this, true);
//		MobclickAgent.onResume(this);
//		SharedPreference.getInstance(KituriApplication.getInstance()).recordTrace(SharedPreference.TRACE_ON_START,
//				mActivity.getClass().getName());
		super.onResume();
	}
	
	@Override
	public void onStop(){
		//dismissLoading();
		KituriApplication.getInstance().addActivityStatus(this, false);
//		if(!KituriApplication.getInstance().isAllActivityAlive() && (this instanceof Loft)){	 
//		}
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}	
	
}
