package com.kituri.app.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

/**
 * 调整图片size
 * @author yuweichen
 *
 */
public class PicReSizeTask extends AsyncTask<String, String, String> {
	
	public static final String opeFromCamera = "1"; //表示图片来自相机拍照
	public static final String opeFromLocalFile = "2";//表示图片来自相册或其他文件
	public static final int cutImgFlag = 0x08;
	public static final int uploadFlag = 0x09;
	public static final int uploadNormalFlag = 0x10;
	private String fileName; //表示本地相册或文件的目录
	private String tempFileUrl;//自定义目录
	private Bitmap thumbfileBitMap;	
	private String opeType;
	private ImageView imageView;
	private Handler handler;
	private boolean cutFlag =false;
	private Bitmap tempMap = null;
	private Bitmap localMap = null;
	public PicReSizeTask(boolean cutFlag,Handler handler) {
		this.cutFlag = cutFlag;
		this.handler = handler;
	}

	public PicReSizeTask(ImageView imageView,Handler handler) {
		this.imageView = imageView;
		this.handler = handler;
	}

	@Override
	protected String doInBackground(String... params) {

		opeType = params[0];
		if (opeType.equals("1")) {
			try {
				tempFileUrl = params[1];
				thumbfileBitMap = ImageUtils.getNativeImage(tempFileUrl);//ImageUtils.getSmallBitmap(tempFileUrl);
				/*int engle = 0;
				if (thumbfileBitMap.getHeight() < thumbfileBitMap.getWidth()) {
					engle = 90;
				} else {
					engle = 0;
				}
				Matrix matrix = new Matrix();
				matrix.preRotate(engle);
				thumbfileBitMap = Bitmap.createBitmap(thumbfileBitMap, 0, 0,
						thumbfileBitMap.getWidth(),
						thumbfileBitMap.getHeight(), matrix, true);*/
				ImageUtils.saveImageToSD(tempFileUrl, thumbfileBitMap, 80);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (opeType.equals("2")) {
			try {
				fileName = params[1];
				tempFileUrl = params[2];
				
				tempMap = ImageUtils.getNativeImage(fileName);
				ImageUtils.saveImageToSD(tempFileUrl, tempMap, 80);
				
				
			
			/*	localMap = ImageUtils.getNativeImage(tempFileUrl);
						//ImageUtils.getBitmapByFile(new File(tempFileUrl));
				int engle = 0;
				if (localMap.getHeight() < localMap.getWidth()) {
					engle = 90;
				} else {
					engle = 0;
				}
				Matrix matrix = new Matrix();
				matrix.preRotate(engle);
				thumbfileBitMap = Bitmap.createBitmap(localMap, 0, 0,
						localMap.getWidth(), localMap.getHeight(), matrix, true);
						;
				
				ImageUtils.saveImageToSD(tempFileUrl, thumbfileBitMap, 40);*/
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(tempMap!=null){
					tempMap.recycle();
					tempMap = null;
				}
				if(localMap!=null){
					localMap.recycle();
					localMap = null;
				}
				if(thumbfileBitMap!=null){
					thumbfileBitMap.recycle();
					thumbfileBitMap = null;
				}
			}
		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (imageView != null){
			imageView.setImageBitmap(ImageUtils.getBitmapByPath(tempFileUrl));
			Message message = new Message();
			message.what = PicReSizeTask.uploadNormalFlag;
			handler.sendMessage(message);
		}
		if(cutFlag){
			Message message = new Message();
			message.what = PicReSizeTask.cutImgFlag;
			handler.sendMessage(message);
		}else{
			Message message = new Message();
			message.what = PicReSizeTask.uploadFlag;
			handler.sendMessage(message);
		}
	}
}