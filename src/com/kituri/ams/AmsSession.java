package com.kituri.ams;

import java.io.ByteArrayInputStream;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

//import com.lenovo.leos.sns.SnsSession;

public class AmsSession {
	/**
	 * http://m.renyuxian.utan.com/
	 */
	public static String sAmsRequestHost = "http://m.renyuxian.utan.com/";
	public static String sAmsRequestHostUpload = "http://up2.utan.com/chatroom/chat/up?";
//	public static String sAmsRequestHostNew = "http://m.utanbaby.com/";
	/**
	 * 淘宝正式域名 utanbaby.wx.m.jaeapp.com
	         淘宝测试域名 utanbaby-1.wx.m.jaeapp.com
	         淘宝本地域名 192.168.1.199:8080
	 */
	public static String sAmsRequestHostAli = "http://utanbaby.wx.m.jaeapp.com/";
	/**
	 * 上传用户头像ur
	 */
	public static final String sAmsUploadMobileAvatar = "http://up1.utan.com/api/mobile_avatar_up.php?";
	/**
	 * 上传宝宝头像ur
	 */
	public static final String sAmsUploadBabyAvatar = "http://up1.utan.com/api/baby_avatar_up.php?";

	/**
	 * 上传音频地址
	 */
	public static final String sAmsUploadChatAudio = "http://up2.utan.com/chatroom/chat/upaudio?";
	
	// public static String sAmsRequestHost = "http://testm22.yuying.utan.com/";
	// public static String sAmsRequestHostUpload =
	// "http://up1.utan.com/api/mamabang_img_up.php?";
	// public static String sAmsRequestHostNew = "http://m.test.utanbaby.com/";

	/**
	 * ?requestMethod=
	 */
	public static String sAmsRequestMethod = "?requestMethod=";

	// public static String sAmsRequestInterface = "api";

	static public String URL_JOIN_RENYUXIAN = "http://renyuxian.utan.com";
	static public String URL_BUY_WEIGHING_MACHINE = "http://m.renyuxian.utan.com/ryfit";
	
	public interface AmsCallback {
		public void onResult(AmsRequest request, int code, AmsResult amsResult);
	}


	
//	public interface AmsCallByteBack {
//		public void onResult(AmsRequest request, int code, AmsResult amsResult);
//	}
	
	/*
	 * 
	 */

	// public static String appendRequestParam(String param){
	// return "&" + param + "=" + param;
	// }

	public static String appendRequestParam(String param1, String param2) {
		return "&" + param1 + "=" + param2;
	}

	public static String appendRequestParam(String param1, int param2) {
		return "&" + param1 + "=" + param2;
	}

	public static String appendRequestParam(String param1, float param2) {
		return "&" + param1 + "=" + param2;
	}
	
	public static String appendRequestParam(String param1, double param2) {
		return "&" + param1 + "=" + param2;
	}

	public static String appendRequestParam(String param1, long param2) {
		return "&" + param1 + "=" + param2;
	}

	// static private String getSuffix(Context context){
	// StringBuffer sb = new StringBuffer();
	// if(Constants.mUser != null){
	// if(TextUtils.isEmpty(Constants.mUser.userid)){
	// sb.append(AmsSession.appendRequestParam("user_id",Constants.mUser.userid));
	// }
	// }
	// sb.append(AmsSession.appendRequestParam("Cookie",Constants.Cookie==null?"":Constants.Cookie));
	// sb.append(AmsSession.appendRequestParam("YR_TOKEN",Constants.YR_TOKEN==null?"":Constants.YR_TOKEN));
	// sb.append(AmsSession.appendRequestParam("YR_CODE_VERSION",Constants.YR_CODE_VERSION));
	// sb.append(AmsSession.appendRequestParam("User-Agent","dayima"+Constants.YR_CODE_VERSION+"Phonesystle:"+
	// android.os.Build.MODEL+"systemversion:"+android.os.Build.VERSION.RELEASE));
	// sb.append(AmsSession.appendRequestParam("device_id",Setting.getInstance(context).getDeviceId()));
	// sb.append(AmsSession.appendRequestParam("DEVICE_MAC",Setting.getInstance(context).getDeviceId()));
	// sb.append(AmsSession.appendRequestParam("android",Constants.Phone));
	// sb.append(AmsSession.appendRequestParam("YR_CODE",Constants.YR_CODE));
	// long time = System.currentTimeMillis();
	// sb.append(AmsSession.appendRequestParam("time",String.valueOf(time)));
	// sb.append(AmsSession.appendRequestParam("reqauth",MD5Util.MD5toString(
	// Setting.getInstance(context).getDeviceId(),time,
	// MD5Util.getLetter()+MD5Util.getMark())));
	// return sb.toString();
	// }

//	static public AmsRequestParameters getParameters(Context context) {
//		AmsRequestParameters arameters = new AmsRequestParameters();
//
//		// arameters.put("user_id",PsPushUserData.getUserId(context));
//		// arameters.put("Cookie",Constants.Cookie==null?"":Constants.Cookie);
//		// arameters.put("YR-TOKEN",PsPushUserData.getYRToken(context));
//		String umengVesion = Setting.getInstance(context).getUmengVersion();
//		arameters.put("source", umengVesion);
//		arameters.put("userid", PsPushUserData.getUserId(context));
//		arameters.put("YR-TOKEN", PsPushUserData.getYRToken(context));
//		arameters.put("YR-CODE-VERSION", Setting.getInstance(context).getAppVersion());
//		arameters.put("User-Agent",Constants.YR_CODE+ Setting.getInstance(context).getAppVersion()	+ "Phonesystle:" + android.os.Build.MODEL+ "systemversion:" + android.os.Build.VERSION.RELEASE);
//		arameters.put("device-id", Setting.getInstance(context).getDeviceId());
//		arameters.put("IMEI", Setting.getInstance(context).getDeviceId());
//		if(!StringUtils.isEmpty(Setting.getInstance(context).getMAC())){
//			arameters.put("DEVICE-MAC",Setting.getInstance(context).getMAC() );
//		}else{
//			arameters.put("DEVICE-MAC",Setting.getInstance(context).getDeviceId());
//		}
//		arameters.put("YR-SYSTEM", Constants.Phone);
//		arameters.put("YR-CODE", Constants.YR_CODE);
////		if (!StringUtils.isEmpty(PsPushUserData.getPushUserIdChannelId(context))) {
////			arameters.put("device-token",PsPushUserData.getPushUserIdChannelId(context));
////		}
//		arameters.put("YR-PK",MD5Util.MD5(Setting.getInstance(context).getMAC()+ MD5Util.MD5(Setting.getInstance(context).getAppVersion() + Constants.Phone)+ Constants.MALL_PUBLIC_KEY));
//		// long time = System.currentTimeMillis();
//		// arameters.put("time",String.valueOf(time));
//		// arameters.put("reqauth",MD5Util.MD5toString(
//		// Setting.getInstance(context).getDeviceId(),time,
//		// MD5Util.getLetter()+MD5Util.getMark()));
//		return arameters;
//		// arameters.put(key, value);
//	}
	
	public static void executeInputStream(final Context context, final AmsRequest request,
			AmsRequestParameters parameters, final AmsCallback callback){

		int sMode = request.getHttpMode();
		final String sPriority = request.getPriority();
		if (sMode == AmsRequest.Method.GET) {
			if (AmsRequest.PRIORITY.DEFAULT.equals(sPriority)) {
				
				AmsNetworkHandler.getInstance(context).executeHttpGetInputStream(request,
								parameters, new Listener<ByteArrayInputStream>() {
					
									@Override
									public void onResponse(ByteArrayInputStream arg0) {
										if(context == null){
											return;
										}
										AmsResult amsResult = new AmsResult(sPriority);
										amsResult.setIs(arg0);
										callback.onResult(request, 200, amsResult);
									}
								}, new ErrorListener() {

									@Override
									public void onErrorResponse(VolleyError arg0) {
										// TODO Auto-generated method stub
										if(context == null){
											return;
										}
										AmsResult amsResult = new AmsResult(sPriority);
										amsResult.setError(arg0);
										NetworkResponse NetworkResponse = arg0.networkResponse;
										if(NetworkResponse != null){
											callback.onResult(request, arg0.networkResponse.statusCode, amsResult);
										}else{
											callback.onResult(request, 404, amsResult);
										}
									}
								});

			}
		}
	}
	
	public static void execute(final Context context, final AmsRequest request,
			AmsRequestParameters parameters, final AmsCallback callback) {
//		String sUrl = request.getUrl().replaceAll(" ", "%20");
//		if(!(request instanceof TraceRequest)){
//			SharedPreference.getInstance(context).recordTrace(
//					SharedPreference.TRACE_ON_URL, sUrl);
//		}
//		Logger.i("execute:" + sUrl);
		//final Map<String, String> sPostData = request.getPost();
		//final String mFileName = request.getFileName();
		int sMode = request.getHttpMode();
		final String sPriority = request.getPriority();
		//final AmsRequestParameters parameters = getParameters(context);
		if (sMode == AmsRequest.Method.GET) {
			if (AmsRequest.PRIORITY.DEFAULT.equals(sPriority)) {
				
				AmsNetworkHandler.getInstance(context).executeHttpGet(request,
								parameters, new Listener<JSONObject>() {
					
									@Override
									public void onResponse(JSONObject arg0) {
										// TODO Auto-generated method stub
										//callback.onResult(request, 200, arg0.toString().getBytes());
										if(context == null){
											return;
										}
										AmsResult amsResult = new AmsResult(sPriority);
										amsResult.setBody(arg0);
										callback.onResult(request, 200, amsResult);
									}
								}, new ErrorListener() {

									@Override
									public void onErrorResponse(VolleyError arg0) {
										// TODO Auto-generated method stub
										if(context == null){
											return;
										}
										AmsResult amsResult = new AmsResult(sPriority);
										amsResult.setError(arg0);
										NetworkResponse NetworkResponse = arg0.networkResponse;
										if(NetworkResponse != null){
											callback.onResult(request, arg0.networkResponse.statusCode, amsResult);
										}else{
											callback.onResult(request, 404, amsResult);
										}
									}
								});

			}
		} else if (sMode == AmsRequest.Method.POST) {
			// post
			if (AmsRequest.PRIORITY.DEFAULT.equals(sPriority)) {
				AmsNetworkHandler.getInstance(context).executeHttpPost(request,
								parameters, new Listener<JSONObject>() {

									public void onResponse(JSONObject arg0) {
										// TODO Auto-generated method stub
										if(context == null){
											return;
										}
										AmsResult amsResult = new AmsResult(sPriority);
										amsResult.setBody(arg0);
										callback.onResult(request, 200, amsResult);
									}
								}, new ErrorListener() {

									@Override
									public void onErrorResponse(VolleyError arg0) {
										// TODO Auto-generated method stub
										AmsResult amsResult = new AmsResult(sPriority);
										amsResult.setError(arg0);
										NetworkResponse NetworkResponse = arg0.networkResponse;
										if(NetworkResponse != null){
											callback.onResult(request, arg0.networkResponse.statusCode, amsResult);
										}else{
											callback.onResult(request, -1, amsResult);
										}
										
									}
								});


			} 
//			else if (AmsRequest.PRIORITY.FILE.equals(sPriority)) {
//				AmsNetworkHandler.getInstance(context).executeFileHttpPost(context, request,
//						mFileName, getParameters(context), new ILeHttpCallback() {
//
//									public void onReturn(int code, byte[] data) {
//										// TODO Auto-generated method stub
//										//callback.onResult(request, code, data);
//										if(context == null){
//											return;
//										}
//										AmsResult amsResult = new AmsResult(sPriority);
//										amsResult.setBytes(data);
//										callback.onResult(request, code, amsResult);
//									}
//								});
//			} else if (AmsRequest.PRIORITY.FILES.equals(sPriority)) {
//
//				AmsNetworkHandler.getInstance(context).executeFileHttpPosts(context,
//								request,
//								((AmsUploadsRequest) request).getPosts(),
//								getParameters(context), new ILeHttpCallback() {
//
//									public void onReturn(int code, byte[] data) {
//										// TODO Auto-generated method stub
//										//callback.onResult(request, code, data);
//										if(context == null){
//											return;
//										}
//										AmsResult amsResult = new AmsResult(sPriority);
//										amsResult.setBytes(data);
//										callback.onResult(request, code, amsResult);
//									}
//								});
//
//			}
//		} else if (sMode == AmsRequest.Method.GET_STRING) {
//			AmsNetworkHandler.getInstance(context).executeHttpGetString(request, parameters, new Listener<String>() {
//				@Override
//				public void onResponse(String response) {
//					if(context == null){
//						return;
//					}
//					AmsResult amsResult = new AmsResult(sPriority);
//					amsResult.setStringBody(response);
//					callback.onResult(request, 200, amsResult);
//				}
//			}, new ErrorListener() {
//
//				@Override
//				public void onErrorResponse(VolleyError error) {
//					if(context == null){
//						return;
//					}
//					NetworkResponse NetworkResponse = error.networkResponse;
//					if(NetworkResponse != null){
//						callback.onResult(request, error.networkResponse.statusCode, null);
//					}else{
//						callback.onResult(request, 404, null);
//					}
//				}
//			});
//		}
		}
	}
}
