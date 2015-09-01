//package com.kituri.net;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.text.TextUtils;
//
//public class CacheUtils {
//
//	/**
//	 * 保存或更新一张图片相关信息记录到本地数据库
//	 * 
//	 * @param context
//	 * @param url
//	 * @param fileName
//	 * @param time
//	 * @param post
//	 */
//	public static void insert(Context context, String url, String fileName,
//			String time, String post) {
//		ContentValues values = new ContentValues();
//		if (!TextUtils.isEmpty(url)) {
//			values.put(ImageCacheHelper.URL, url);
//		}
//		if (!TextUtils.isEmpty(fileName)) {
//			values.put(ImageCacheHelper.FILENAME, fileName);
//		}
//		if (!TextUtils.isEmpty(time)) {
//			values.put(ImageCacheHelper.LASTACCESSTIME, time);
//		}
//
//		if (!TextUtils.isEmpty(post)) {
//			values.put(ImageCacheHelper.POSTDATA, post);
//		}
//
//		ImageCacheHelper ich = ImageCacheHelper.getInstance(context,
//				ImageCacheHelper.DATABASE_NAME);
//
//		if (queryImageByUrl(context, url)) {
//			// update
//			ich.getWritableDatabase().update(ImageCacheHelper.TAB_NAME, values,
//					ImageCacheHelper.URL + "=?", new String[] { url });
//		} else {
//			// insert
//			ich.getWritableDatabase().insert(ImageCacheHelper.TAB_NAME,
//					ImageCacheHelper.URL, values);
//		}
//		ich.getWritableDatabase().close();
//
//	}
//
//	/**
//	 * 删除某张图片相关信息所对应的数据库映射
//	 * 
//	 * @param context
//	 * @param url
//	 * @param postdata
//	 */
//	public static void delete(Context context, String url, String postdata) {
//		if (TextUtils.isEmpty(url)) {
//			return;
//		}
//		ImageCacheHelper ich = ImageCacheHelper.getInstance(context,
//				ImageCacheHelper.DATABASE_NAME);
//		String selection = "";
//		String[] selectionArgs = null;
//		if (TextUtils.isEmpty(postdata)) {
//			selection = ImageCacheHelper.URL + "=?";
//			selectionArgs = new String[] { url };
//		} else {
//			selection = ImageCacheHelper.URL + "=? and "
//					+ ImageCacheHelper.POSTDATA + "=?";
//			selectionArgs = new String[] { url, postdata };
//		}
//
//		ich.getWritableDatabase().delete(ImageCacheHelper.TAB_NAME, selection,
//				selectionArgs);
//		ich.getWritableDatabase().close();
//	}
//
//	/**
//	 * 刷新某张图片相关信息记录
//	 * 
//	 * @param context
//	 * @param time
//	 * @param url
//	 * @param postdata
//	 */
//	public static void update(Context context, String time, String url,
//			String postdata) {
//		ContentValues values = new ContentValues();
//		if (!TextUtils.isEmpty(time)) {
//			values.put(ImageCacheHelper.LASTACCESSTIME, time);
//		}
//		if (!TextUtils.isEmpty(url)) {
//			values.put(ImageCacheHelper.URL, url);
//		}
//		if (!TextUtils.isEmpty(postdata)) {
//			values.put(ImageCacheHelper.POSTDATA, postdata);
//		}
//		ImageCacheHelper ich = ImageCacheHelper.getInstance(context,
//				ImageCacheHelper.DATABASE_NAME);
//		ich.getWritableDatabase().update(ImageCacheHelper.TAB_NAME, values,
//				ImageCacheHelper.URL + "=?", new String[] { url });
//		ich.getWritableDatabase().close();
//	}
//
//	/**
//	 * 查询指定URL所对应的一条图片信息
//	 * 
//	 * @param context
//	 * @param url
//	 * @param postdata
//	 * @return
//	 */
//	public static Cursor query(Context context, String url, String postdata) {
//		if (TextUtils.isEmpty(url)) {
//			return null;
//		}
//		ImageCacheHelper ich = ImageCacheHelper.getInstance(context,
//				ImageCacheHelper.DATABASE_NAME);
//		String selection = "";
//		String[] selectionArgs = null;
//		Cursor cursor = null;
//		if (TextUtils.isEmpty(postdata)) {
//			selection = ImageCacheHelper.URL + "=?";
//			selectionArgs = new String[] { url };
//		} else {
//			selection = ImageCacheHelper.URL + "=? and "
//					+ ImageCacheHelper.POSTDATA + "=?";
//			selectionArgs = new String[] { url, postdata };
//		}
//		try {
//			cursor = ich.getReadableDatabase().query(ImageCacheHelper.TAB_NAME,
//					null, selection, selectionArgs, null, null, null);
//		} catch (Exception e) {
//			// TODO: handle exception
//			return null;
//		}
//
//		return cursor;
//
//	}
//
//	/**
//	 * 查询是否已经有该条URL对应的记录
//	 * 
//	 * @param context
//	 * @param url
//	 * @return
//	 */
//	private static boolean queryImageByUrl(Context context, String url) {
//
//		if (TextUtils.isEmpty(url)) {
//			return false;
//		}
//		boolean flag = false;
//		ImageCacheHelper ich = ImageCacheHelper.getInstance(context,
//				ImageCacheHelper.DATABASE_NAME);
//		Cursor cursor = ich.getReadableDatabase().query(
//				ImageCacheHelper.TAB_NAME, null, ImageCacheHelper.URL + "=?",
//				new String[] { url }, null, null, null);
//		if (cursor != null) {
//			if (cursor.getCount() > 0) {
//				if (cursor.moveToFirst()) {
//					flag = true;
//				}
//			}
//			cursor.close();
//		}
//		ich.close();
//		return flag;
//	}
//
//}
