package com.kituri.app.push;




import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;



/**
 * author by lizhi 2011 5.27 11:11
 * This class is used to do with item'state. It's a single instance class.
 */
public class PsPushUserDao {
	
	//define the item state

	
	//appShareUrl

	//YR_CODE_VERSION
	
	
//	"brandJoin":"http:\/\/m.guimi.utan.com\/?requestMethod=Brand.joinApplyHtml&itye=html",
//	"disclaimer":"http:\/\/m.guimi.utan.com\/?requestMethod=Info.disclaimerHtml&itye=html",
//	"rebateExplain":"http:\/\/m.guimi.utan.com\/?requestMethod=Info.rebateExplainHtml&itye=html",
//	"brandList":"http:\/\/m.guimi.utan.com\/?requestMethod=Brand.listHtml&itye=html"
	
	//lastBrowseTime

//	private String mUserId = "";
//	private String mYRToken = "";
//	private String mMobile = "";
//	private String mPwd = "";
//	private String mAvatar = "";
//	private Boolean mIsJoinGroup = false;
//	private Long mMessageLastBrowseTime = 0l;
//	private Long mGroupLastBrowseTime = 0l;
//
//	private String mBrandJoin;
//	private String mDisclaimer;
//	private String mRebateExplain;
//	private String mBrandList;
//	private String mAppUpdateUrl;
//	private String mAppShareUrl;
//	
//	private Boolean mTipUploadAvatar = false;
	
	//private Context mContext;
	private SharedPreferences mSharedPreferences;
	private Editor mEdit;
	private static PsPushUserDao mAppStoreSetting;
	private Map<String, Object> cache = new HashMap<String, Object>();

	private PsPushUserDao(Context context) {
		//this.mContext = context;
		initSetting(context);

	}

	private void initSetting(Context context) {
		mSharedPreferences = context.getSharedPreferences("app_setting",
				Context.MODE_PRIVATE);
		mEdit = mSharedPreferences.edit();
	}

	protected static PsPushUserDao getInstance(Context context) {

		if (mAppStoreSetting == null) {
			mAppStoreSetting = new PsPushUserDao(context);
			return mAppStoreSetting;
		} else {
			return mAppStoreSetting;
		}
	}
	
	protected synchronized Object getData(String key, Object defData){
		if(defData == null || key == null){
			//error
			return defData;
		}
		Object data = cache.get(key);
		if(data == null){
			if(defData instanceof Integer){
				data = mSharedPreferences.getInt(key, (Integer)defData);
			}else if(defData instanceof String){
				data = mSharedPreferences.getString(key, (String)defData);
			}else if(defData instanceof Long){
				data = mSharedPreferences.getLong(key, (Long)defData);
			}else if(defData instanceof Float){
				data = mSharedPreferences.getFloat(key, (Float)defData);
			}else if(defData instanceof Boolean){
				data = mSharedPreferences.getBoolean(key, (Boolean)defData);
			}
			if(data != null){
				cache.put(key, data);
				return data;
			}
			return defData;
		}
		return data;
	}
	
	protected synchronized void setData(String key, Object value){
		if(value == null || key == null){
			return;
		}
		if(value instanceof Integer){
			mEdit.putInt(key, (Integer)value);
		}else if(value instanceof String){
			mEdit.putString(key, (String)value);
		}else if(value instanceof Long){
			mEdit.putLong(key, (Long)value);
		}else if(value instanceof Float){
			mEdit.putFloat(key, (Float)value);
		}else if(value instanceof Boolean){
			mEdit.putBoolean(key, (Boolean)value);
		}
		cache.put(key, value);
		mEdit.commit();
	}
	

	// define the item state
	/*
	 * private static final String KEY_USER_ID = "user_id"; private static final
	 * String KEY_YR_TOKEN = "YR_TOKEN"; private static final String KEY_MOBILE
	 * = "mobile"; private static final String KEY_PASSWORD = "psd"; private
	 * static final String KEY_IS_JOIN_GROUP = "is_join_group";
	 * 
	 * private static final String KEY_USER_AVATAR = "user_avatar";
	 * 
	 * private static final String KEY_MESSAGE_LAST_BROWSE_TIME =
	 * "message.lastBrowseTime"; private static final String
	 * KEY_GROUP_LAST_BROWSE_TIME = "group.lastBrowseTime";
	 * 
	 * private static final String KEY_HTML_URL_BRAND_JOIN = "brandJoin";
	 * private static final String KEY_HTML_URL_DISCLAIMER = "disclaimer";
	 * private static final String KEY_HTML_URL_REBATE_EXPLAIN =
	 * "rebateExplain"; private static final String KEY_HTML_URL_BRANDLIST =
	 * "brandList"; private static final String KEY_APP_UPDATE_URL =
	 * "appUpdateUrl"; private static final String KEY_APP_SHARE_URL =
	 * "appShareUrl";
	 * 
	 * private static final String KEY_SQUARE_MAX_OFFSETID =
	 * "square_max_offsetid"; private static final String
	 * KEY_SQUARE_MIX_OFFSETID = "square_mix_offsetid";
	 * 
	 * private static final String KEY_SQUARE_LOAD_TYPE = "square_load_type";
	 * 
	 * private static final String KEY_SQUARE_IS_REFRESH = "square_is_refresh";
	 */

	// appShareUrl

	// YR_CODE_VERSION

	// "brandJoin":"http:\/\/m.guimi.utan.com\/?requestMethod=Brand.joinApplyHtml&itye=html",
	// "disclaimer":"http:\/\/m.guimi.utan.com\/?requestMethod=Info.disclaimerHtml&itye=html",
	// "rebateExplain":"http:\/\/m.guimi.utan.com\/?requestMethod=Info.rebateExplainHtml&itye=html",
	// "brandList":"http:\/\/m.guimi.utan.com\/?requestMethod=Brand.listHtml&itye=html"

	// lastBrowseTime

	/*
	 * private String mUserId = ""; private String mYRToken = ""; private String
	 * mMobile = ""; private String mPwd = ""; private String mAvatar = "";
	 * private Boolean mIsJoinGroup = false; private Long mMessageLastBrowseTime
	 * = 0l; private Long mGroupLastBrowseTime = 0l;
	 * 
	 * private String mBrandJoin; private String mDisclaimer; private String
	 * mRebateExplain; private String mBrandList; private String mAppUpdateUrl;
	 * private String mAppShareUrl;
	 * 
	 * private Integer mSquareMaxOffsetId = 0; private Integer
	 * mSquareMixOffsetId = 0;
	 * 
	 * private String mSquareLoadType;
	 * 
	 * private boolean mSquareIsRefresh;
	 * 
	 * private Context mContext; private SharedPreferences mSharedPreferences;
	 * private Editor mEdit; private static PsPushUserDao mAppStoreSetting;
	 * 
	 * private PsPushUserDao(Context context) { this.mContext = context;
	 * initSetting();
	 * 
	 * }
	 * 
	 * private void initSetting() { mSharedPreferences =
	 * mContext.getSharedPreferences("preferences", Context.MODE_PRIVATE); mEdit
	 * = mSharedPreferences.edit(); }
	 * 
	 * protected static PsPushUserDao getInstance(Context context) {
	 * 
	 * if(mAppStoreSetting == null) { mAppStoreSetting = new
	 * PsPushUserDao(context); return mAppStoreSetting; } else { return
	 * mAppStoreSetting; } }
	 * 
	 * protected String getUserId(){ if(TextUtils.isEmpty(mUserId)){ mUserId =
	 * mSharedPreferences.getString(KEY_USER_ID, ""); } return mUserId; }
	 * 
	 * protected void setUserId(String mUserId){ this.mUserId = mUserId;
	 * mEdit.putString(KEY_USER_ID, mUserId); mEdit.commit(); }
	 * 
	 * protected String getYRToken(){ if(TextUtils.isEmpty(mYRToken)){ mYRToken
	 * = mSharedPreferences.getString(KEY_YR_TOKEN, ""); } return mYRToken; }
	 * 
	 * protected void setYRToken(String mYRToken){ this.mYRToken = mYRToken;
	 * mEdit.putString(KEY_YR_TOKEN, mYRToken); mEdit.commit(); }
	 * 
	 * protected String getMobile(){ if(TextUtils.isEmpty(mMobile)){ mMobile =
	 * mSharedPreferences.getString(KEY_MOBILE, ""); } return mMobile; }
	 * 
	 * protected void setMobile(String mMobile){ this.mMobile = mMobile;
	 * mEdit.putString(KEY_MOBILE, mMobile); mEdit.commit(); }
	 * 
	 * protected void setPwd(String mPwd){ this.mPwd = mPwd;
	 * mEdit.putString(KEY_PASSWORD, mPwd); mEdit.commit(); }
	 * 
	 * protected String getPwd(){ if(TextUtils.isEmpty(mPwd)){ mPwd =
	 * mSharedPreferences.getString(KEY_PASSWORD, ""); } return mPwd; }
	 * 
	 * protected void setAvatar(String mAvatar){ this.mAvatar = mAvatar;
	 * mEdit.putString(KEY_USER_AVATAR, mAvatar); mEdit.commit(); }
	 * 
	 * protected String getAvatar(){ if(TextUtils.isEmpty(mAvatar)){ mAvatar =
	 * mSharedPreferences.getString(KEY_USER_AVATAR, ""); } return mAvatar; }
	 * 
	 * protected void setJoinGroup(Boolean mIsJoinGroup){ this.mIsJoinGroup =
	 * mIsJoinGroup; mEdit.putBoolean(KEY_IS_JOIN_GROUP, mIsJoinGroup);
	 * mEdit.commit(); }
	 * 
	 * protected Boolean isJoinGroup(){ if(mIsJoinGroup == false){ mIsJoinGroup
	 * = mSharedPreferences.getBoolean(KEY_IS_JOIN_GROUP, false); } return
	 * mIsJoinGroup; }
	 * 
	 * protected Long getMessageLastBrowseTime(){ if(mMessageLastBrowseTime ==
	 * 0l){ mMessageLastBrowseTime =
	 * mSharedPreferences.getLong(KEY_MESSAGE_LAST_BROWSE_TIME, 0l); } return
	 * mMessageLastBrowseTime; }
	 * 
	 * protected void setMessageLastBrowseTime(Long mMessageLastBrowseTime){
	 * this.mMessageLastBrowseTime = mMessageLastBrowseTime;
	 * mEdit.putLong(KEY_MESSAGE_LAST_BROWSE_TIME, mMessageLastBrowseTime);
	 * mEdit.commit(); }
	 * 
	 * protected Long getGroupLastBrowseTime(){ if(mGroupLastBrowseTime == 0l){
	 * mGroupLastBrowseTime =
	 * mSharedPreferences.getLong(KEY_GROUP_LAST_BROWSE_TIME, 0l); } return
	 * mGroupLastBrowseTime; }
	 * 
	 * protected void setGroupLastBrowseTime(Long mGroupLastBrowseTime){
	 * this.mGroupLastBrowseTime = mGroupLastBrowseTime;
	 * mEdit.putLong(KEY_GROUP_LAST_BROWSE_TIME, mGroupLastBrowseTime);
	 * mEdit.commit(); }
	 * 
	 * protected String getBrandJoin(){ if(TextUtils.isEmpty(mBrandJoin)){
	 * mBrandJoin = mSharedPreferences.getString(KEY_HTML_URL_BRAND_JOIN, ""); }
	 * return mBrandJoin; }
	 * 
	 * protected void setBrandJoin(String mBrandJoin){ this.mBrandJoin =
	 * mBrandJoin; mEdit.putString(KEY_HTML_URL_BRAND_JOIN, mBrandJoin);
	 * mEdit.commit(); }
	 * 
	 * protected String getDisclaimer(){ if(TextUtils.isEmpty(mDisclaimer)){
	 * mDisclaimer = mSharedPreferences.getString(KEY_HTML_URL_DISCLAIMER, "");
	 * } return mDisclaimer; }
	 * 
	 * protected void setDisclaimer(String mDisclaimer){ this.mDisclaimer =
	 * mDisclaimer; mEdit.putString(KEY_HTML_URL_DISCLAIMER, mDisclaimer);
	 * mEdit.commit(); }
	 * 
	 * protected String getRebateExplain(){
	 * if(TextUtils.isEmpty(mRebateExplain)){ mRebateExplain =
	 * mSharedPreferences.getString(KEY_HTML_URL_REBATE_EXPLAIN, ""); } return
	 * mRebateExplain; }
	 * 
	 * protected void setRebateExplain(String mRebateExplain){
	 * this.mRebateExplain = mRebateExplain;
	 * mEdit.putString(KEY_HTML_URL_REBATE_EXPLAIN, mRebateExplain);
	 * mEdit.commit(); }
	 * 
	 * protected String getBrandList(){ if(TextUtils.isEmpty(mBrandList)){
	 * mBrandList = mSharedPreferences.getString(KEY_HTML_URL_BRANDLIST, ""); }
	 * return mBrandList; }
	 * 
	 * protected void setBrandList(String mBrandList){ this.mBrandList =
	 * mBrandList; mEdit.putString(KEY_HTML_URL_BRANDLIST, mBrandList);
	 * mEdit.commit(); }
	 * 
	 * protected String getAppUpdateUrl(){ if(TextUtils.isEmpty(mAppUpdateUrl)){
	 * mAppUpdateUrl = mSharedPreferences.getString(KEY_APP_UPDATE_URL, ""); }
	 * return mAppUpdateUrl; }
	 * 
	 * protected void setAppUpdateUrl(String mAppUpdateUrl){ this.mAppUpdateUrl
	 * = mAppUpdateUrl; mEdit.putString(KEY_APP_UPDATE_URL, mAppUpdateUrl);
	 * mEdit.commit(); }
	 * 
	 * protected String getAppShareUrl(){ if(TextUtils.isEmpty(mAppShareUrl)){
	 * mAppShareUrl = mSharedPreferences.getString(KEY_APP_SHARE_URL, ""); }
	 * return mAppShareUrl; }
	 * 
	 * protected void setAppShareUrl(String mAppShareUrl){ this.mAppShareUrl =
	 * mAppShareUrl; mEdit.putString(KEY_APP_SHARE_URL, mAppShareUrl);
	 * mEdit.commit(); }
	 * 
	 * protected Integer getSquareMaxOffsetId(){ if(mSquareMaxOffsetId == 0){
	 * mSquareMaxOffsetId = mSharedPreferences.getInt(KEY_SQUARE_MAX_OFFSETID,
	 * 0); } return mSquareMaxOffsetId; }
	 * 
	 * protected void setSquareMaxOffsetId(Integer mSquareMaxOffsetId){
	 * this.mSquareMaxOffsetId = mSquareMaxOffsetId;
	 * mEdit.putInt(KEY_SQUARE_MAX_OFFSETID, mSquareMaxOffsetId);
	 * mEdit.commit(); }
	 * 
	 * protected Integer getSquareMixOffsetId(){ if(mSquareMixOffsetId == 0){
	 * mSquareMixOffsetId = mSharedPreferences.getInt(KEY_SQUARE_MIX_OFFSETID,
	 * 0); } return mSquareMixOffsetId; }
	 * 
	 * protected void setSquareMixOffsetId(Integer mSquareMixOffsetId){
	 * this.mSquareMixOffsetId = mSquareMixOffsetId;
	 * mEdit.putInt(KEY_SQUARE_MIX_OFFSETID, mSquareMixOffsetId);
	 * mEdit.commit(); }
	 * 
	 * protected String getSquareLoadType(){
	 * if(TextUtils.isEmpty(mSquareLoadType)){ mSquareLoadType =
	 * mSharedPreferences.getString(KEY_SQUARE_LOAD_TYPE, ""); } return
	 * mSquareLoadType; }
	 * 
	 * protected void setSquareLoadType(String mSquareLoadType){
	 * this.mSquareLoadType = mSquareLoadType;
	 * mEdit.putString(KEY_SQUARE_LOAD_TYPE, mSquareLoadType); mEdit.commit(); }
	 * 
	 * protected boolean getSquareIsRefresh(){ mSquareIsRefresh =
	 * mSharedPreferences.getBoolean(KEY_SQUARE_IS_REFRESH, false); return
	 * mSquareIsRefresh; }
	 * 
	 * protected void setSquareIsRefresh(boolean mSquareIsRefresh){
	 * this.mSquareIsRefresh = mSquareIsRefresh;
	 * mEdit.putBoolean(KEY_SQUARE_IS_REFRESH, mSquareIsRefresh);
	 * mEdit.commit(); }
	 */

	// mAppShareUrl
	// protected String getAppVersion(){
	// if(TextUtils.isEmpty(appVersion)){
	// appVersion = mSharedPreferences.getString(KEY_APP_VERSION, "");
	// }
	// return appVersion;
	// }
	//
	// protected void setAppVersion(String appVersion){
	// this.appVersion = appVersion;
	// mEdit.putString(KEY_APP_VERSION, appVersion);
	// mEdit.commit();
	// }
	// appVersion
	// KEY_APP_UPDATE_URL
	// appUpdateUrl
}
