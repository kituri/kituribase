package com.kituri.app.utils;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * @author yuweichen
 */
public class PhotoUtil {
	public static final int PHOTO_RESULT_CODE = 0;
	public static final int SELECT_RESULT_CODE = 1;
	public static final int PHOTO_REQUEST_CUT = 2;

	/**
	 * 拍照
	 * 
	 * @param activity
	 * @param folderPath
	 * @param photoName
	 */
	public static void takePhoto(Activity activity, String folderPath,
			String photoName) {
		/*
		 * File outVideoFile = new java.io.File(fileDir); if
		 * (!outVideoFile.exists()) { outVideoFile.mkdirs(); }
		 */

		Intent it = new Intent("android.media.action.IMAGE_CAPTURE");
		it.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(FileUtils.createFile(folderPath, photoName)));
		it.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		activity.startActivityForResult(it, PHOTO_RESULT_CODE);
	}

	/**
	 * 选择本地图片
	 */
	public static void selectLocalPic(Activity activity) {
		Intent intent = new Intent();
		/* 使用Intent.ACTION_GET_CONTENT这个Action */
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		/* 开启Pictures画面Type设定为image */
		intent.setType("image/*");
		/* 取得相片后返回本画面 */
		activity.startActivityForResult(intent, SELECT_RESULT_CODE);
	}

	public static void openSystemCamera(Activity activity, String folderPath,
			String photoName) {
		Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 指定调用相机拍照后照片的储存路径
		cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(FileUtils.createFile(folderPath, photoName)));
		activity.startActivityForResult(cameraintent, PHOTO_RESULT_CODE);
	}

	public static void startPhotoZoom(Activity activity, Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		activity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

}
