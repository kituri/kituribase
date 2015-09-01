package com.kituri.ams;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class GetBaseResponse implements AmsResponse {

	private GetBaseContents mBaseContents = new GetBaseContents();
	private boolean mIsSuccess = true;

	public GetBaseContents getBaseContents() {
		return mBaseContents;
	}

	public boolean getIsSuccess() {
		return mIsSuccess;
	}

	// public void parseBaseFrom(byte[] bytes) {
	// // TODO Auto-generated method stub
	//
	// }

	private String jsonTrim(String data) {
		if (data.indexOf("{") < 0 || data.lastIndexOf("}") < 0) {
			return data;
		}
		return data.substring(data.indexOf("{"), data.lastIndexOf("}") + 1);
	}

	@Override
	public void parseFrom(AmsResult amsResult) {
		// TODO Auto-generated method stub
		// String data = null;
		JSONObject json = amsResult.getBody();
		try {

			if (json == null) {
				json = new JSONObject(jsonTrim(new String(amsResult.getBytes())));
			}
			mBaseContents.setStatus(json.optInt("status"));
			mBaseContents.setData(json.optString("data"));
			mBaseContents.setMsg(json.optString("msg"));
			mBaseContents.setRequestMethod(json.optString("requestMethod"));
			mBaseContents.setAttachSign(json.optString("attachSign"));
			mBaseContents.setExecTime(json.optLong("execTime"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			// Logger.e("", "JSONException:" + e.getMessage());
			mIsSuccess = false;
		}
		// try catch 如果有问题则
		// mIsSuccess = false;
		// baseContents.setContents(data);
	}

	public static final class GetBaseContents implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1505085902782464372L;

		public int getStatus() {
			return mStatus;
		}

		public void setStatus(int mStatus) {
			this.mStatus = mStatus;
		}

		public String getData() {
			return mData;
		}

		public void setData(String mData) {
			this.mData = mData;
		}

		public String getMsg() {
			return mMsg;
		}

		public void setMsg(String mMsg) {
			this.mMsg = mMsg;
		}

		public String getRequestMethod() {
			return mRequestMethod;
		}

		public void setRequestMethod(String mRequestMethod) {
			this.mRequestMethod = mRequestMethod;
		}

		public String getAttachSign() {
			return mAttachSign;
		}

		public void setAttachSign(String mAttachSign) {
			this.mAttachSign = mAttachSign;
		}

		public long getExecTime() {
			return mExecTime;
		}

		public void setExecTime(long mExecTime) {
			this.mExecTime = mExecTime;
		}

		private int mStatus;
		private String mData;
		private String mMsg;
		private String mRequestMethod;
		private String mAttachSign;
		private long mExecTime;

		public GetBaseContents() {
			mStatus = 0;
			mData = "";
			mMsg = "";
			mRequestMethod = "";
			mAttachSign = "";
			mExecTime = 0;
		}
	}

}
