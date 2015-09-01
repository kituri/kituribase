//package com.kituri.net;
//
//
//import android.content.Context;
//
//
//public class RequestManager {
//	private static RequestController controller;
//
//	public interface ILeHttpCallback {
//		void onReturn(int code, byte[] data);
//	};
//
//	public static void executeLowHttpGet(Context context, String url,
//			ILeHttpCallback callback) {
//		/*String fileName = URLEncoder.encode(url);
//		//String fileName = CacheManager.readCacheData(context, url);
//		File file = new File(fileName);
//		if (file.exists()) {
//		//if (fileName != null) {
//			Log.i("AppStore3", "read from cache----" + url);
//			FileInputStream fis = null;
//			try {
//				fis = new FileInputStream(new File(fileName));
//				byte[] bytes = new byte[fis.available()];
//				fis.read(bytes);
//				callback.onReturn(-1, bytes);
//			} catch (FileNotFoundException e) {
//				callback.onReturn(-1, null);
//				e.printStackTrace();
//			} catch (IOException e) {
//				callback.onReturn(-1, null);
//				e.printStackTrace();
//			}finally{
//				try {
//					if(fis!=null)
//					fis.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		} else {*/
//			//Logger.i("AppStore3", "new request----" + url);
//			controller = RequestController.getInstance(context);
//			controller.addRequestToQueue(url, callback);
//		//}
//	}
//
//	public static void executeLowHttpPost(Context context, String url, String postdata,
//			ILeHttpCallback callback) {
//		/*String fileName = URLEncoder.encode(url);
//		//String fileName = CacheManager.readCacheData(context, url, postdata);
//		File file = new File(fileName);
//		if (file.exists()) {
//		//if (fileName != null) {
//			FileInputStream fis = null;
//			try {
//				fis = new FileInputStream(new File(fileName));
//				byte[] bytes = new byte[fis.available()];
//				fis.read(bytes);
//				callback.onReturn(-1, bytes);
//			} catch (FileNotFoundException e) {
//				callback.onReturn(-1, null);
//				e.printStackTrace();
//			} catch (IOException e) {
//				callback.onReturn(-1, null);
//				e.printStackTrace();
//			}finally{
//				try {
//					if(fis!=null)
//					fis.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		} else {*/
//			controller = RequestController.getInstance(context);
//			controller.addRequestToQueue(url, postdata, callback);
//		//}
//	}
//}
