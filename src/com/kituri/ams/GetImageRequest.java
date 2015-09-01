//package com.kituri.ams;
//
//import java.io.ByteArrayInputStream;
//import java.util.HashMap;
//
//import android.graphics.drawable.Drawable;
//
//public class GetImageRequest extends DefaultAmsRequest {
//	private String mImageUrl;
//
//	public void setData(String imageUrl) {
//		mImageUrl = imageUrl;
//	}
//
//	public String getUrl() {
//		// TODO Auto-generated method stub
//		if (mImageUrl.contains("http:")) {
//			return mImageUrl;
//		}
//		return AmsSession.sAmsRequestHost + mImageUrl;
//	}
//
//	public HashMap<String, String> getPost() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public int getHttpMode() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	public String getPriority() {
//		// TODO Auto-generated method stub
//		//return "low";
//		return "low";
//	}
//
//	public static final class GetImageResponse implements AmsResponse {
//		private Drawable mDrawable = null;
//
//		public Drawable getDrawable() {
//			return mDrawable;
//		}
//
//		public void parseFrom(AmsResult amsResult) {
//			if (amsResult == null || amsResult.getBytes() == null) {
//				mDrawable = null;
//				return;
//			}
//			
//			try {
//				//System.gc();
//				ByteArrayInputStream bs = new ByteArrayInputStream(amsResult.getBytes());
//				mDrawable = Drawable.createFromStream(bs, null);
//			} catch(Exception e) {
//				//Log.i("AppStore3", "GetImageResponse can't create drawable !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//				mDrawable = null;
//			}
//		}
//	}
//
//	@Override
//	public String getRequestMethod() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Boolean isUseUICache() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//}
