package com.kituri.app.utils;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class CutImageTask extends AsyncTask<String, String, String> {
	private Intent intent;
	private String filePath;
	private Handler handler;
	private ImageView imageView;
	public static final int cutFinishSuccess = 0x10;
	public static final int cutFinishFail = 0x11;
	String result;
	
	public CutImageTask(Intent intent,String filePath,Handler handler,ImageView imageView){
		this.intent = intent;
		this.filePath = filePath;
		this.handler = handler;
		this.imageView = imageView;
	}
	
	@Override
	protected String doInBackground(String... params) {
		result = ImageUtils.saveImageToSD(intent, filePath);
		return result;
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		Message message = new Message();
		if(result.equals("1")){
			imageView.setImageBitmap(ImageUtils.getBitmapByPath(filePath));
			message.what = cutFinishSuccess;
		}else{
			message.what = cutFinishFail;
		}
		handler.sendMessage(message);
	}
	
}
