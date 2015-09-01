//package com.kituri.ams;
//
//import java.util.HashMap;
//
//import android.content.Context;
//
///*
// *TRACE接口，记录一切用户行为
// *
// *http://a.utanbaby.com/trace/mobileuserbehaviour
// * */
//
////2014-04-14 15:42:26,HotShopsViewController|
//
//public class TraceRequest extends DefaultAmsRequest {
//	private Context mContext;
//	private HashMap<String, String> postData;
//	static public final String URL = "http://a.utanbaby.com/trace/mobileuserbehaviour?form=22222222";
//
//	public TraceRequest(Context context) {
//		mContext = context;
//	}
//
//	public void setData(String postData) {
//		//this.postData = "log=" + postData;
//		this.postData = new HashMap<String, String>();
//		this.postData.put("log", postData);
//	}
//
//	// private String user_id = "";
//	// private String url;
//
//	@Override
//	public String getRequestMethod() {
//		// TODO Auto-generated method stub
//		return "";
//	}
//
//	public String getUrl() {
//		// TODO Auto-generated method stub
//		// Logger.i(getRequestMethod(),"getUrl() start");
//		return URL;
//	}
//
//	public HashMap<String, String> getPost() {
//		// TODO Auto-generated method stub
//		return postData;
//	}
//
//	public int getHttpMode() {
//		// TODO Auto-generated method stub
//		return 1;
//	}
//
//	public String getPriority() {
//		// TODO Auto-generated method stub
//		return "high";
//	}
//
//	public static final class TraceResponse implements AmsResponse {
//
//		private boolean mIsSuccess = true;
//
//		public boolean getIsSuccess() {
//			return mIsSuccess;
//		}
//
//		public void parseFrom(AmsResult amsResult) {
//			// TODO Auto-generated method stub
//		}
//
//	}
//
//}
