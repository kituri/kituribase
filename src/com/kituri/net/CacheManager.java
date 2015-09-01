//package com.kituri.net;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.UUID;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.os.Environment;
//
//import com.kituri.app.Constants;
//
//
//public class CacheManager {
//	
////	public static final String CacheFolder = "/iunlock/";
////	public static final String CacheFolderr = "iunlock/";
////	public static final String CacheFolde = "iunlock";
////	public static final String CacheFolder = 
////			"/" + AppDriverConstant.CACHE_ADDRESS + "/";
//	public static final String CacheFolder = Constants.CacheFolder;
//	public static final String DownloadFolder = "download/";
//	
//	public static void init(){
////		String fileName = Environment.getExternalStorageDirectory()
////				+ "/" + CacheFolder;
////				//+ uuid.toString().replaceAll("-", "");
////		File file = new File(fileName);
////		if (!file.exists()) {
////			file.mkdir();
////		}
//
//		getCacheFolder();
//		
//	}
//	
//	public static String getCacheFolder(){
//		//
//		String fileName = Environment.getExternalStorageDirectory()
//				+ "/" + CacheFolder;
//				//+ uuid.toString().replaceAll("-", "");
//		File file = new File(fileName);
//		if (!file.exists()) {
//			file.mkdir();
//		}
//		return fileName;
//	}
//	//判断是否有sd卡 否则使用内部路径
//	public static String getCacheFolder(Context context){
//		//
//		String fileName = null;
//		if(android.os.Environment.getExternalStorageState().equals(  
//			    android.os.Environment.MEDIA_MOUNTED)){
//			fileName = Environment.getExternalStorageDirectory()
//			+ "/" + CacheFolder;
//		}else{
//			fileName = context.getCacheDir().getAbsolutePath()+ "/" + CacheFolder;
//		}
//				//+ uuid.toString().replaceAll("-", "");
//		File file = new File(fileName);
//		if (!file.exists()) {
//			file.mkdir();
//		}
//		return fileName;
//	}
//	
//	public static String readCacheData(Context context, String url) {
//		Cursor cursor = CacheUtils.query(context, url, "");
//		if (cursor != null) {
//			if (cursor.getCount() > 0) {
//				cursor.moveToFirst();
//				String fileName = cursor.getString(cursor
//						.getColumnIndex(ImageCacheHelper.FILENAME));
//				cursor.close();
//				long time = System.currentTimeMillis();
//				CacheUtils.update(context, time + "", url, "");
//				return fileName;
//			}
//			cursor.close();
//		}
//		return null;
//	};
//
//	public static String readCacheName(Context context, String url) {
//		Cursor cursor = CacheUtils.query(context, url, "");
//		if (cursor != null) {
//			if (cursor.getCount() > 0) {
//				cursor.moveToFirst();
//				String fileName = cursor.getString(cursor
//						.getColumnIndex(ImageCacheHelper.FILENAME));
//				cursor.close();
//				long time = System.currentTimeMillis();
//				CacheUtils.update(context, time + "", url, "");
//				return fileName;
//			}
//			cursor.close();
//		}
//		return null;
//	};
//	
//	public static String readCacheData(Context context, String url, String post) {
//		Cursor cursor = CacheUtils.query(context, url, post);
//		if (cursor != null) {
//			if (cursor.getCount() > 0) {
//				cursor.moveToFirst();
//				String fileName = cursor.getString(cursor
//						.getColumnIndex(ImageCacheHelper.FILENAME));
//				cursor.close();
//				long time = System.currentTimeMillis();
//				CacheUtils.update(context, time + "", url, post);
//				return fileName;
//			}
//			cursor.close();
//		}
//		return null;
//	};
//
//	public static Boolean writeCacheData(Context context, String url,
//			byte[] bytes) {
//		if (Environment.getExternalStorageState().equals(
//				Environment.MEDIA_MOUNTED)) {
//			try {
//				UUID uuid = UUID.randomUUID();
//				String fileName = Environment.getExternalStorageDirectory()
//						+ "/" + CacheFolder + "/";
//				String cacheFileName = Environment
//						.getExternalStorageDirectory()
//						+ "/" + CacheFolder + "/"
//						+ url.hashCode();
//						//+ uuid.toString().replaceAll("-", "");
//				File file = new File(fileName);
//				if (!file.exists()) {
//					file.mkdir();
//				}
//				File cacheFile = new File(cacheFileName);
//				cacheFile.createNewFile();
//				file.createNewFile();
//				FileOutputStream fos = new FileOutputStream(cacheFileName);
//				fos.write(bytes);
//				fos.close();
//				long time = System.currentTimeMillis();
//				CacheUtils.insert(context, url, cacheFileName, time + "", "");
//				return true;
//			} catch (Exception e) {
//				//MobclickAgent.reportError(context,"NetworkHttpRequest："+e.getMessage()) ;
//				e.printStackTrace();
//			}
//		}
//		return false;
//	};
//
//	public static Boolean writeCacheData(Context context, String url,
//			byte[] bytes, String post) {
//		if (Environment.getExternalStorageState().equals(
//				Environment.MEDIA_MOUNTED)) {
//			try {
//				UUID uuid = UUID.randomUUID();
//				String fileName = Environment.getExternalStorageDirectory()
//						+ "/" + CacheFolder + "/";
//				String cacheFileName = Environment
//						.getExternalStorageDirectory()
//						+ "/" + CacheFolder + "/"
//						+ url.hashCode();
//						//+ uuid.toString().replaceAll("-", "");
//				File file = new File(fileName);
//				if (!file.exists()) {
//					file.mkdir();
//				}
//				File cacheFile = new File(cacheFileName);
//				cacheFile.createNewFile();
//				file.createNewFile();
//				FileOutputStream fos = new FileOutputStream(cacheFileName);
//				fos.write(bytes);
//				fos.close();
//				long time = System.currentTimeMillis();
//				CacheUtils.insert(context, url, cacheFileName, time + "", post);
//				return true;
//			} catch (Exception e) {
//				//MobclickAgent.reportError(context,"NetworkHttpRequest："+e.getMessage()) ;
//				e.printStackTrace();
//			}
//		}
//		return false;
//	}
////
////	public static String readLocalData(Context context, String fileName) {
////		//Cursor cursor = CacheUtils.query(context, url, post);
////		if (fileName != null) {
////
////				cursor.moveToFirst();
////				String fileName = cursor.getString(cursor
////						.getColumnIndex(CacheFiles.FILENAME));
////				cursor.close();
////				long time = System.currentTimeMillis();
////				CacheUtils.update(context, time + "", url, post);
////				return fileName;
////		}
////		return null;
////	};
////
////	public static Boolean writeLocalData(Context context, String url,
////			byte[] bytes) {
////		if (Environment.getExternalStorageState().equals(
////				Environment.MEDIA_MOUNTED)) {
////			try {
////				UUID uuid = UUID.randomUUID();
////				String fileName = Environment.getExternalStorageDirectory()
////						+ CacheFolder;
////				String cacheFileName = Environment
////						.getExternalStorageDirectory()
////						+ CacheFolder
////						+ url.hashCode();
////						//+ uuid.toString().replaceAll("-", "");
////				File file = new File(fileName);
////				if (!file.exists()) {
////					file.mkdir();
////				}
////				File cacheFile = new File(cacheFileName);
////				cacheFile.createNewFile();
////				file.createNewFile();
////				FileOutputStream fos = new FileOutputStream(cacheFileName);
////				fos.write(bytes);
////				fos.close();
////				long time = System.currentTimeMillis();
////				//CacheUtils.insert(context, url, cacheFileName, time + "", "");
////				return true;
////			} catch (FileNotFoundException e) {
////				e.printStackTrace();
////			} catch (IOException e) {
////				e.printStackTrace();
////			}
////		}
////		return false;
////	};
//	public static Boolean isExists(Context context,String url)
//	{
//		String fileName = Environment
//		.getExternalStorageDirectory() + "/"
//		+ CacheManager.CacheFolder + "/" + url.hashCode();
//		//Logger.i("fileName:" + fileName);
//		//String fileName = CacheManager.readCacheData(mContext, url);
//		File file = new File(fileName);
//		if (file.exists()) {
//			return true;
//		}
//		return false;
//	}
//	
//	public static String chageFileName(String url){
//		return Environment
//				.getExternalStorageDirectory() + "/"
//				+ CacheManager.CacheFolder + "/" + url.hashCode();
//	}
//	
//	//删除缓存
//    static public boolean clearCache(){
//    	File cacheDir = null;
//    	 if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
//             cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),CacheFolder);
//         if(!cacheDir.exists())cacheDir.mkdirs();     
//    	try {
//        File[] files=cacheDir.listFiles();
//        for(File f:files)
//            f.delete();
//    	
//		} catch (Exception e) {
//			// TODO: handle exception
//			return false;
//		}
//		return true;
//    }
//
//	
//	
//	//读取SD卡上的路径使用
//	 public static byte[] readSDData(String fileName){
////		 String fileName = Environment.getExternalStorageDirectory()
////					+ CacheFolder;
////			String cacheFileName = Environment
////					.getExternalStorageDirectory()
////					+ CacheFolder
////					+ URLEncoder.encode(url);					
//			File file = new File(fileName);
//					if (file.exists()) {
//					//if (fileName != null) {
//						//AppDriverUtil.info("RequestController", "read from cache----" + url);
//						FileInputStream fis;
//						try {
//							fis = new FileInputStream(new File(fileName));
//							byte[] bytes = new byte[fis.available()];
//							fis.read(bytes);
//							return bytes;
//						} catch (FileNotFoundException e) {
//							e.printStackTrace();
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					} 
//					return null;
//	 }
//	 
//		//读取Assets下的文件使用
//	 public static byte[] readAssetsData(Context mContext,String fileName){				
//		 InputStream is = null;
//		 try {
//			 is = mContext.getAssets().open(fileName);
//			//fis = new FileInputStream(new File(fileName));
//			byte[] bytes = new byte[is.available()];
//			is.read(bytes);
//			return bytes;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 return null;
//	 }
//	 
//}
