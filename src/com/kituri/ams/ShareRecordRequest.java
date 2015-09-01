//package com.kituri.ams;
//
//import java.util.HashMap;
//
//import android.content.Context;
//
//
//
///**
// *渠道打点 
// *
// *http://m.yuying.utan.com/?requestMethod=user.sharerecord&channel_type=0&share_type=0&content=223
// * */
//
//public class ShareRecordRequest extends DefaultAmsRequest {
//	private Context mContext;
//	private HashMap<String, String> postData;
//
//	public ShareRecordRequest(Context context) {
//		mContext = context;
//	}
//
//	public void setData(int channelType,int shareType,String content) {	
//		postData = new HashMap<String, String>();
//		postData.put("channelType", channelType+"");
//		postData.put("shareType", shareType+"");
//		postData.put("content", content);
//	}
//	
//	//private String user_id = "";
//	//private String url;
//	
//	@Override
//	public String getRequestMethod() {
//		// TODO Auto-generated method stub
//		return "user.sharerecord";
//	}
//	
//	public String getUrl() {
//		// TODO Auto-generated method stub
//		//Logger.i(getRequestMethod(),"getUrl() start");
//		StringBuffer sb = new StringBuffer();
//		sb.append(AmsSession.sAmsRequestHost);
//		sb.append(AmsSession.sAmsRequestMethod);
//		sb.append(getRequestMethod());
//		return sb.toString();
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
//	
//	public static final class ShareRecordResponse extends GetBaseResponse{
//		
////		private boolean mIsSuccess = true;
////		private ShareRecordContents contents = new ShareRecordContents();
////
////		public ShareRecordContents getContents() {
////			return contents;
////		}
//
////		public boolean getIsSuccess() {
////			return mIsSuccess;
////		}
//		
//		public void parseFrom(AmsResult amsResult) {
////			// TODO Auto-generated method stub
////			String data = new String(bytes);
////			//try catch 如果有问题则
////			//mIsSuccess = false;
////			//Logger.i("ShareRecordRequest parseFrom:" + data);
////			try {
////				JSONObject json = new JSONObject(data);
////				contents.setStatus(json.optInt("status"));
////				contents.setMsg(json.optString("msg"));
////
////			} catch (JSONException e) {
////				// TODO Auto-generated catch block
////				//e.printStackTrace();
////				//Logger.e("", "JSONException:" + e.getMessage());
////				mIsSuccess = false;
////			}
//			super.parseFrom(amsResult);
//			
//
//		}
//		
//	}
//
////	public static final class ShareRecordContents implements Serializable {
////
////		private static final long serialVersionUID = -929827676672957511L;
////		private int status = -1;
////		private String msg = "";
//////		private ArrayList<CellSuduku> cellSudukus = new ArrayList<CellSuduku>();
//////		
//////		public ArrayList<CellSuduku> getCellSudukus() {
//////			return cellSudukus;
//////		}
//////		public void setCellSudukus(ArrayList<CellSuduku> cellSudukus) {			
//////			this.cellSudukus.clear();
//////			synchronized(cellSudukus) {
//////				for (CellSuduku entry : cellSudukus) {
//////					this.cellSudukus.add(entry);	
//////				}				
//////			}
//////		}
////		/*
////		 * status == 0时，为正常
////		 * */
////		public int getStatus() {
////			return status;
////		}
////		public void setStatus(int status) {
////			this.status = status;
////		}
////		public String getMsg() {
////			return msg;
////		}
////		public void setMsg(String msg) {
////			this.msg = msg;
////		}
////
////	}
//
//
//
//}
