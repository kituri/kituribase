package com.kituri.app.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.opengl.GLES10;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.HapticFeedbackConstants;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kituri.app.KituriApplication;
import com.kituri.app.data.BroswerUrlData;
import com.kituri.app.model.KituriToast;
import com.kituri.app.model.Logger;
import com.kituri.app.model.MyAsyncTask;
import com.kituri.app.widget.SelectPickDateListener;
import com.kituri.demo.R;

//import com.lenovo.leos.sns.GetActivityRequest.ActivityInfo;
//import com.lenovo.leos.sns.GetActivityRequest.AppRecommendActivity;
//import com.lenovo.leos.sns.GetActivityRequest.AppShareActivity;
//import com.lenovo.leos.sns.GetActivityRequest.ContinueShareActivity;
//import com.lenovo.leos.sns.GetActivityRequest.NewAppActivity;
//import com.lenovo.leos.sns.GetActivityRequest.ReleaseShareActivity;
//import com.lenovo.leos.sns.GetActivityRequest.ReviewActivity;

public class Utility {

	private final static String TAG = "Utility";
	public static final String WEIXIN = "com.tencent.mm";
	public static final String WEIXIN_FRIEND = "com.tencent.mm.ui.tools.ShareToTimeLineUI";
	public static final String MOBILE_QQ = "com.tencent.mobileqq";
	public static final String DAYIMA = "com.dayima";

	private Utility() {
		// Forbidden being instantiated.
	}

	/**
	 * @return 当前应用的版本号
	 */
	public static String getVersion(Context context) {
		String versionName = "";
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			versionName = info.versionName;
		} catch (Exception e) {
			versionName = "";
		}
		return versionName;
	}

	/**
	 * @return 当前应用的版本号
	 */
	public static String getUmengVersion(Context context) {
		String versionName = "";
		try {
			ApplicationInfo appInfo = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			versionName = appInfo.metaData.getString("UMENG_CHANNEL");
			;
		} catch (Exception e) {
			versionName = "";
		}
		return versionName;
	}

//	//检查客户端是否安装了微信客户端
//		public static boolean checkInstallWX(Context context){
//			IWXAPI api = WXAPIFactory.createWXAPI(context, Constants.WEIXINAPPID, false);
//			api.registerApp(Constants.WEIXINAPPID);
//			return api.isWXAppInstalled();
//		}

	/**
	 * 版本号比较
	 */

	public static boolean compareVerson(String currentVerson,
			String serverVerson) {
		if (isEmpty(currentVerson) || isEmpty(serverVerson)
				|| currentVerson.length() < 3 || serverVerson.length() < 3) {
			return false;
		}
		int current, server;

		if (currentVerson.length() == 5) {
			currentVerson = new StringBuffer().append(currentVerson.charAt(0))
					.append(currentVerson.charAt(2))
					.append(currentVerson.charAt(4)).toString();
		} else if (currentVerson.length() == 3) {
			currentVerson = new StringBuffer().append(currentVerson.charAt(0))
					.append(currentVerson.charAt(2)).append("0").toString();
		}
		current = Integer.valueOf(currentVerson);

		if (serverVerson.length() == 5) {
			serverVerson = new StringBuffer().append(serverVerson.charAt(0))
					.append(serverVerson.charAt(2))
					.append(serverVerson.charAt(4)).toString();
		} else if (serverVerson.length() == 3) {
			serverVerson = new StringBuffer().append(serverVerson.charAt(0))
					.append(serverVerson.charAt(2)).append("0").toString();
		}

		server = Integer.valueOf(serverVerson);
		return server > current;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dipToPx(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 判断字符串是否为空或者内容为null
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		// boolean flag = false;
		// if (str == null || "".equals(str) || "null".equals(str)) {
		// flag = true;
		// }
		// str = str.trim();
		// if ("".equals(str)) {
		// flag = true;
		// }
		// return flag;
		return TextUtils.isEmpty(str);
	}

	// /**
	// * 通过浏览器下载最新百度地图APP
	// *
	// * @param context
	// */
	// public static void installBaiduMap(final Context context) {
	// Toast.makeText(context, "您尚未安装百度地图app或app版本过低，请安装最新版本百度地图!",
	// Toast.LENGTH_LONG).show();
	// Activity ac = (Activity) context;
	// BaiduMapNavigation.GetLatestBaiduMapApp(ac);
	// }

	/**
	 * 格式化URL,只取scheme后面部分
	 * 
	 * @param url
	 * @return
	 */
	public static String formatURL(String url) {
		if (!TextUtils.isEmpty(url)) {
			return url.substring(url.indexOf(":") + 3, url.length());
		} else {
			return "";
		}

	}

	/**
	 * Formats the given size as a String in bytes, KB, MB or GB with a single
	 * digit of precision. Ex: 12,315,000 = 12.3 MB
	 * 
	 * @author Kituri
	 */
	public static String formatSize(float size) {
		long kb = 1024;
		long mb = (kb * 1024);
		long gb = (mb * 1024);
		if (size < kb) {
			return String.format("%d bytes", (int) size);
		} else if (size < mb) {
			return String.format("%.1f KB", size / kb);
		} else if (size < gb) {
			return String.format("%.1f MB", size / mb);
		} else {
			return String.format("%.1f GB", size / gb);
		}
	}

	public static boolean isDelaySevenDay(long before, long now) {
		if (before <= 0 || now <= 0) {
			return true;
		}
		long temp = now - before;
		long offset = 7 * 24 * 3600;
		return temp >= offset ? true : false;
	}

	/**
	 * Returns true if the specified date is within today. Returns false
	 * otherwise.
	 */
	public static boolean isDateToday(long ms) {
		Date date = new Date(ms);
		Date today = new Date();
		if (date.getYear() == today.getYear()
				&& date.getMonth() == today.getMonth()
				&& date.getDate() == today.getDate()) {
			return true;
		}
		return false;
	}

	// 从数组中选择
	public static void setSingleChoiceItems(Context context,
			final TextView view, int arrayResId,
			DialogInterface.OnClickListener listener) {
		final String[] array = context.getResources()
				.getStringArray(arrayResId);
		int checkItem = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(view.getText().toString())) {
				checkItem = i;
			}
		}
		if (listener == null) {
			new AlertDialog.Builder(context).setSingleChoiceItems(array,
					checkItem, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							view.setText(array[which]);
							dialog.dismiss();
						}
					}).show();
		} else {
			new AlertDialog.Builder(context).setSingleChoiceItems(array,
					checkItem, listener).show();
		}
	}

	// 从数组中选择
	public static void setSingleChoiceItems(Context context,
			final TextView view, int arrayResId) {
		setSingleChoiceItems(context, view, arrayResId, null);
	}

	/**
	 * 判断设置日期是否超前
	 * 
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @return
	 */
	public static Boolean isFuture(int year, int monthOfYear, int dayOfMonth) {
		SimpleDateFormat sYearDateFormat = new SimpleDateFormat("yyyy",
				Locale.getDefault());
		final String todayYear = sYearDateFormat.format(new java.util.Date());
		SimpleDateFormat sMonthDateFormat = new SimpleDateFormat("MM",
				Locale.getDefault());
		final String todayMonth = sMonthDateFormat.format(new java.util.Date());
		SimpleDateFormat sDayDateFormat = new SimpleDateFormat("dd",
				Locale.getDefault());
		final String todayDay = sDayDateFormat.format(new java.util.Date());

		String n1 = String.valueOf(year);
		int month = monthOfYear + 1;
		if (month > 9) {
			n1 = n1 + month;
		} else {
			n1 = n1 + "0" + month;
		}

		if (dayOfMonth > 9) {
			n1 = n1 + dayOfMonth;
		} else {
			n1 = n1 + "0" + dayOfMonth;
		}
		if (Integer.parseInt(n1) > Integer.parseInt(todayYear + todayMonth
				+ todayDay)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断设置日期是否超前
	 * 
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @return
	 */
	public static Boolean isFuture(String str) {
		String strs[] = str.split("-");
		int year = Integer.valueOf(strs[0]);
		int monthOfYear = Integer.valueOf(strs[1]);
		int dayOfMonth = Integer.valueOf(strs[2]);

		SimpleDateFormat sYearDateFormat = new SimpleDateFormat("yyyy",
				Locale.getDefault());
		final String todayYear = sYearDateFormat.format(new java.util.Date());
		SimpleDateFormat sMonthDateFormat = new SimpleDateFormat("MM",
				Locale.getDefault());
		final String todayMonth = sMonthDateFormat.format(new java.util.Date());
		SimpleDateFormat sDayDateFormat = new SimpleDateFormat("dd",
				Locale.getDefault());
		final String todayDay = sDayDateFormat.format(new java.util.Date());

		String n1 = String.valueOf(year);
		int month = monthOfYear + 1;
		if (month > 9) {
			n1 = n1 + month;
		} else {
			n1 = n1 + "0" + month;
		}

		if (dayOfMonth > 9) {
			n1 = n1 + dayOfMonth;
		} else {
			n1 = n1 + "0" + dayOfMonth;
		}
		if (Integer.parseInt(n1) > Integer.parseInt(todayYear + todayMonth
				+ todayDay)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断设置日期是否为过去时间
	 * 
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @return
	 */
	public static Boolean isLater(int year, int monthOfYear, int dayOfMonth) {
		SimpleDateFormat sYearDateFormat = new SimpleDateFormat("yyyy",
				Locale.getDefault());
		final String todayYear = sYearDateFormat.format(new java.util.Date());
		SimpleDateFormat sMonthDateFormat = new SimpleDateFormat("MM",
				Locale.getDefault());
		final String todayMonth = sMonthDateFormat.format(new java.util.Date());
		SimpleDateFormat sDayDateFormat = new SimpleDateFormat("dd",
				Locale.getDefault());
		final String todayDay = sDayDateFormat.format(new java.util.Date());

		String n1 = String.valueOf(year);
		int month = monthOfYear + 1;
		if (month > 9) {
			n1 = n1 + month;
		} else {
			n1 = n1 + "0" + month;
		}

		if (dayOfMonth > 9) {
			n1 = n1 + dayOfMonth;
		} else {
			n1 = n1 + "0" + dayOfMonth;
		}
		if (Integer.parseInt(n1) < Integer.parseInt(todayYear + todayMonth
				+ todayDay)) {
			return true;
		}
		return false;
	}

	// public static String formatTodayDate(Context context, long ms) {
	// long second = 1000;
	// long minute = 60 * second;
	// long hour = 60 * minute;
	//
	// long before = new Date().getTime() - ms;
	// if (before < 0) {
	// return "0" + context.getResources().getString(R.string.second);
	// } else if (before < minute) {
	// return before/second + context.getResources().getString(R.string.second);
	// } else if (before < hour) {
	// return before/minute + context.getResources().getString(R.string.minute);
	// } else {
	// return before/hour + context.getResources().getString(R.string.hour);
	// }
	// }

	/**
	 * Formats the given milliseconds as a String in date and time.
	 * 
	 * @author Kituri
	 */
	public static String formatDate(Context context, long ms) {
		Date date = new Date(ms);
		DateFormat dateFormat = android.text.format.DateFormat
				.getDateFormat(context);
		return dateFormat.format(date);
	}

	public static String formatTime(Context context, long ms) {
		Date date = new Date(ms);
		DateFormat timeFormat = android.text.format.DateFormat
				.getTimeFormat(context);
		return timeFormat.format(date);
	}

	
	public static String formatTime(long millisTimes) {
//		SimpleDateFormat sdf = null;		
//		if(isToday(millisTimes)){
//			sdf = new SimpleDateFormat("MM月dd日 HH:mm");
//		}else{
//			sdf = new SimpleDateFormat("MM月dd日 HH:mm");
//		}		
//		String date = sdf.format(new Date(millisTimes * 1000));
//		// System.out.println(date);
//		return date;
		//Date date = new Date(millisTimes * 1000);
		return getTimeDiff(new Date(millisTimes * 1000));
		
	}

	public static String getTimeDiff(Date date) {
		Resources res = KituriApplication.getInstance().getResources();
		Calendar cal = Calendar.getInstance();
		long diff = 0;
		Date dnow = cal.getTime();
		String str = "";
		diff = dnow.getTime() - date.getTime();

		if (diff > 2592000000L) {// 30 * 24 * 60 * 60 * 1000=2592000000 毫秒
			str = String.format(res.getString(R.string.time_before_month_ago), 1);
		} else if (diff > 1814400000) {// 21 * 24 * 60 * 60 * 1000=1814400000 毫秒
			str = String.format(res.getString(R.string.time_before_week_ago), 3);
		} else if (diff > 1209600000) {// 14 * 24 * 60 * 60 * 1000=1209600000 毫秒
			str = String.format(res.getString(R.string.time_before_week_ago), 2);
		} else if (diff > 604800000) {// 7 * 24 * 60 * 60 * 1000=604800000 毫秒
			str = String.format(res.getString(R.string.time_before_week_ago), 1);
		} else if (diff > 86400000) { // 24 * 60 * 60 * 1000=86400000 毫秒
			// System.out.println("X天前");
			str = String.format(res.getString(R.string.time_before_day_ago), (int) Math.floor(diff / 86400000f));
		} else if (diff > 3600000) {// 5 * 60 * 60 * 1000=18000000 毫秒
			// System.out.println("X小时前");
			str = String.format(res.getString(R.string.time_before_hours_ago), (int) Math.floor(diff / 3600000f));
		} else if (diff > 60000) {// 1 * 60 * 1000=60000 毫秒
			// System.out.println("X分钟前");
			str = String.format(res.getString(R.string.time_before_minute_ago), (int) Math.floor(diff / 60000f));
		} else {
			//str = (int) Math.floor(diff / 1000) + "秒前";
			str = res.getString(R.string.time_just);
		}
		return str;
	}

//	public static Boolean isToday(long millisTimes){
//		SimpleDateFormat sdf = new SimpleDateFormat("dd").format(millisTimes);
//		String now = new SimpleDateFormat("dd").format(System.currentTimeMillis());
//		
//	}
	
	// public static void assertArgument(EntryList entries) {
	// if (entries == null) {
	// throw new IllegalArgumentException("The contents can't be null.");
	// }
	// }

	// public static boolean isUserActivity(ActivityInfo activity) {
	// return (activity instanceof ReviewActivity)
	// || (activity instanceof AppRecommendActivity)
	// || (activity instanceof NewAppActivity)
	// || (activity instanceof AppShareActivity)
	// || (activity instanceof ContinueShareActivity)
	// || (activity instanceof ReleaseShareActivity);
	// }
	//

	// public static String getScheme(String uri){
	// Uri mUri = Uri.parse(uri).getScheme();
	// return mUri.getScheme();
	// }

	public static void printHashMap(Map<String, Boolean> map) {
		Iterator<java.util.Map.Entry<String, Boolean>> iter = map.entrySet()
				.iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Boolean> entry = (Map.Entry<String, Boolean>) iter
					.next();
			String key = entry.getKey();
			Boolean value = entry.getValue();
			Logger.i("key:" + key);
			Logger.i("value:" + value);

		}
	}

	// public static void showShareAction(Context context,
	// ProductGetShareInfoContents contents){
	// if(!TextUtils.isEmpty(contents.getPic())){
	// ImageLoader.getInstance(context).display(contents.get, new
	// RequestListener() {
	//
	// @Override
	// public void onResult(int isSuccess, final Object data) {
	// // TODO Auto-generated method stub
	// if(isSuccess == RequestListener.REQUEST_SUCCESS){
	// LeHandler.getInstance().post(new Runnable() {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// }
	// });
	// }else{
	// KituriToast.toastShow(BroswerActivity.this, (String)data);
	// }
	// }
	// });
	// }
	//
	// }

	/**
	 * 
	 * 本地分享函数，返回值为分享是否成功
	 * 
	 * @param context
	 * @param packageName
	 * @param msgTitle
	 * @param msgText
	 * @param imgPath
	 * @return Boolean
	 */
	public static Boolean showShareAction(Context context, String packageName,
			String msgTitle, String msgText, String imgPath) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SEND);
		intent.setType("image/*");
		PackageManager packageManager = context.getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(
				intent, 0);
		if (activities.size() > 0) {
			for (ResolveInfo ri : activities) {
				// String packagename = ri.activityInfo.packageName;
				// String activityname = ri.activityInfo.name;
				if (packageName.equals(ri.activityInfo.packageName)) {
					shareMsg(context, packageName, ri.activityInfo.name,
							msgTitle, msgText, imgPath);
					return true;
				}
			}

		}
		return false;
	}

	/**
	 * 图文直接分享函数
	 * 
	 */
	private static void shareMsg(Context context, String packageName,
			String activityName, String msgTitle, String msgText, String imgPath) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		ComponentName component = new ComponentName(packageName, activityName);
		intent.setComponent(component);
		// intent.setAction(Intent.ACTION_SEND);
		if (imgPath == null || imgPath.equals("")) {
			intent.setType("text/plain"); // 纯文本
		} else {
			File f = new File(imgPath);
			if (f != null && f.exists() && f.isFile()) {
				intent.setType("image/png");
				Uri u = Uri.fromFile(f);
				intent.putExtra(Intent.EXTRA_STREAM, u);
			}
		}
		intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
		intent.putExtra(Intent.EXTRA_TEXT, msgText);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// context.startActivity(Intent.createChooser(intent, activityTitle));
		context.startActivity(intent);
	}

	/**
	 * 一键分享功能
	 * 
	 * @param Context
	 */
	static public void StartShare(Context context, String shareTitle,
			String msgTitle, String msgText, String imgPath) {
		// Intent intent = new Intent(Intent.ACTION_SEND);
		// intent.setType("text/plain");
		// intent.putExtra(Intent.EXTRA_SUBJECT, title);
		// intent.putExtra(Intent.EXTRA_TEXT, msg);
		// context.startActivity(Intent.createChooser(intent, szChooserTitle));

		Intent intent = new Intent(Intent.ACTION_SEND);
		// ComponentName component = new ComponentName(packageName,
		// activityName);
		// intent.setComponent(component);
		// intent.setAction(Intent.ACTION_SEND);
		if (TextUtils.isEmpty(imgPath)) {
			intent.setType("text/plain"); // 纯文本
		} else {
			intent.setType("image/*");
			Uri u = null;
			File f = new File(imgPath);
			if (f != null && f.exists() && f.isFile()) {

				File to = new File(imgPath + ".jpg");
				if (!to.exists()) {
					f.renameTo(to);// 重命名sd卡文件的
					u = Uri.fromFile(f);
				} else {
					u = Uri.fromFile(to);
				}
				intent.putExtra(Intent.EXTRA_STREAM, u);
			}
		}
		intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
		intent.putExtra(Intent.EXTRA_TEXT, msgText);
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Intent i = Intent.createChooser(intent, shareTitle);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		// context.startActivity(intent);

	}

	static public void StartShare(Context context, String shareTitle,
			String msgTitle, String msgText) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain"); // 纯文本
		intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
		intent.putExtra(Intent.EXTRA_TEXT, msgText);
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		Intent i = Intent.createChooser(intent, shareTitle);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		// context.startActivity(intent);

	}

	/**
	 * 讯飞听写结果的Json格式解析
	 * 
	 * @param json
	 * @return
	 */
	public static String parseXunfeiResult(String json) {
		if (TextUtils.isEmpty(json))
			return "";

		StringBuffer ret = new StringBuffer();
		try {
			JSONTokener tokener = new JSONTokener(json);
			JSONObject joResult = new JSONObject(tokener);

			JSONArray words = joResult.getJSONArray("ws");
			for (int i = 0; i < words.length(); i++) {
				// 听写结果词，默认使用第一个结果
				JSONArray items = words.getJSONObject(i).getJSONArray("cw");
				JSONObject obj = items.getJSONObject(0);
				ret.append(obj.getString("w"));
				// 如果需要多候选结果，解析数组其他字段
				// for(int j = 0; j < items.length(); j++)
				// {
				// JSONObject obj = items.getJSONObject(j);
				// ret.append(obj.getString("w"));
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret.toString();
	}

	// /**
	// * 将输入流转换成字符串
	// * @param inputStream
	// * @return
	// * @throws IOException
	// */
	// static public String inputStream2Byte(InputStream inputStream) throws
	// IOException{
	// ByteArrayOutputStream bos = new ByteArrayOutputStream();
	//
	// byte [] buffer = new byte[1024];
	// int len = -1;
	//
	// while((len = inputStream.read(buffer)) != -1){
	// bos.write(buffer, 0, len);
	// }
	//
	// bos.close();
	//
	// //指定编码格式为UIT-8
	// return convertCodeAndGetText(new String(bos.toByteArray()));
	// }

//	static public String convertCodeAndGetTextGb2312(InputStream is) {// 转码
//		// File file = new File(str_filepath);
//				BufferedReader reader;
//				String text = "";
//				try {
//					// FileReader f_reader = new FileReader(file);
//					// BufferedReader reader = new BufferedReader(f_reader);
//					// FileInputStream fis = new FileInputStream(file);
//					BufferedInputStream in = new BufferedInputStream(is);
//					in.mark(4);
//					byte[] first3bytes = new byte[3];
//					in.read(first3bytes);// 找到文档的前三个字节并自动判断文档类型。
//					in.reset();
//					reader = new BufferedReader(new InputStreamReader(in, "gb2312"));
//					String str = reader.readLine();
//
//					while (str != null) {
//						text = text + str + "\n";
//						str = reader.readLine();
//
//					}
//					reader.close();
//
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					// e.printStackTrace();
//				} catch (IOException e) {
//					// e.printStackTrace();
//				}
//				return text;
//	}
	
	static public String convertCodeAndGetText(InputStream is) {// 转码

		// File file = new File(str_filepath);
		BufferedReader reader;
		String text = "";
		try {
			// FileReader f_reader = new FileReader(file);
			// BufferedReader reader = new BufferedReader(f_reader);
			// FileInputStream fis = new FileInputStream(file);
			BufferedInputStream in = new BufferedInputStream(is);
			in.mark(4);
			byte[] first3bytes = new byte[3];
			in.read(first3bytes);// 找到文档的前三个字节并自动判断文档类型。
			in.reset();
			if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB
					&& first3bytes[2] == (byte) 0xBF) {// utf-8

				reader = new BufferedReader(new InputStreamReader(in, "utf-8"));

			} else if (first3bytes[0] == (byte) 0xFF
					&& first3bytes[1] == (byte) 0xFE) {

				reader = new BufferedReader(
						new InputStreamReader(in, "unicode"));
			} else if (first3bytes[0] == (byte) 0xFE
					&& first3bytes[1] == (byte) 0xFF) {

				reader = new BufferedReader(new InputStreamReader(in,
						"utf-16be"));
			} else if (first3bytes[0] == (byte) 0xFF
					&& first3bytes[1] == (byte) 0xFF) {

				reader = new BufferedReader(new InputStreamReader(in,
						"utf-16le"));
			} else {

				reader = new BufferedReader(new InputStreamReader(in, "GBK"));
			}
			String str = reader.readLine();

			while (str != null) {
				text = text + str + "\n";
				str = reader.readLine();

			}
			reader.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return text;
	}

	static public String convertCodeAndGetText(String str_filepath) {// 转码

		File file = new File(str_filepath);
		BufferedReader reader;
		String text = "";
		try {
			// FileReader f_reader = new FileReader(file);
			// BufferedReader reader = new BufferedReader(f_reader);
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream in = new BufferedInputStream(fis);
			in.mark(4);
			byte[] first3bytes = new byte[3];
			in.read(first3bytes);// 找到文档的前三个字节并自动判断文档类型。
			in.reset();
			if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB
					&& first3bytes[2] == (byte) 0xBF) {// utf-8

				reader = new BufferedReader(new InputStreamReader(in, "utf-8"));

			} else if (first3bytes[0] == (byte) 0xFF
					&& first3bytes[1] == (byte) 0xFE) {

				reader = new BufferedReader(
						new InputStreamReader(in, "unicode"));
			} else if (first3bytes[0] == (byte) 0xFE
					&& first3bytes[1] == (byte) 0xFF) {

				reader = new BufferedReader(new InputStreamReader(in,
						"utf-16be"));
			} else if (first3bytes[0] == (byte) 0xFF
					&& first3bytes[1] == (byte) 0xFF) {

				reader = new BufferedReader(new InputStreamReader(in,
						"utf-16le"));
			} else {

				reader = new BufferedReader(new InputStreamReader(in, "GBK"));
			}
			String str = reader.readLine();

			while (str != null) {
				text = text + str + "/n";
				str = reader.readLine();

			}
			reader.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return text;
	}

	// 用来遍历对象属性和属性值
	public static String getClassPrint(Object tb) {
		StringBuffer sb = new StringBuffer();
		Field[] fields = tb.getClass().getDeclaredFields();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				if (field.get(tb) != null
						&& !"".equals(field.get(tb).toString())) {
					sb.append(field.getName());
					sb.append(":");
					sb.append(field.get(tb).toString());
					sb.append("  ");
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			sb.append(e.getMessage());
		}
		return sb.toString();

	}

	/**
	 * 获取状态栏的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Activity activity) {
		int statusHeight = 0;
		Rect localRect = new Rect();
		activity.getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(localRect);
		statusHeight = localRect.top;
		if (0 == statusHeight) {
			Class<?> localClass;
			try {
				localClass = Class.forName("com.android.internal.R$dimen");
				Object localObject = localClass.newInstance();
				int i5 = Integer.parseInt(localClass
						.getField("status_bar_height").get(localObject)
						.toString());
				statusHeight = activity.getResources()
						.getDimensionPixelSize(i5);
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		return statusHeight;
	}

	/**
	 * Trace格式化函数，
	 * 
	 * 1 进界面 2 HOME 3 各种点击
	 * 
	 * @param context
	 * @return string
	 */
	static public String TraceFormat(Integer traceType, String name,
			String viewType) {
		// 1,2014-04-14 15:42:26,HotShopsViewController|
		StringBuffer sb = new StringBuffer();
		sb.append(traceType);
		sb.append(",");// 1,
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String date = sDateFormat.format(new java.util.Date());
		sb.append(date);
		sb.append(",");
		sb.append(name);
		if (!TextUtils.isEmpty(viewType)) {
			sb.append(",");
			sb.append(viewType);
		}
		sb.append("|");
		return sb.toString();
	}

	/**
	 * 实现文本复制功能
	 * 
	 * @param content
	 */
	public static void copy(String content, Context context) {
		// 得到剪贴板管理器
		android.text.ClipboardManager cmb = (android.text.ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		cmb.setText(content.trim());
		KituriToast.toastShow(context, R.string.tip_is_copyed);
	}

	/**
	 * 实现粘贴功能
	 * 
	 * @param context
	 * @return
	 */
	public static String paste(Context context) {
		// 得到剪贴板管理器
		android.text.ClipboardManager cmb = (android.text.ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		return cmb.getText().toString().trim();
	}

	// 判断系统版本号
	public static int getSDKVersionNumber() {
		int sdkVersion;
		try {
			sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK_INT);
		} catch (NumberFormatException e) {
			sdkVersion = 0;
		}
		return sdkVersion;
	}

	// 解决自动换行文字排版参差不齐
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	// 储存头像
	static public void saveAvatarBitmap(Bitmap mBitmap, String path_avatar)
			throws IOException {
		File f = new File(path_avatar);
		f.createNewFile();
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			// e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}

	// close://is_pay
	// ordercreate://id=131313

	// static public String getProtocolHeader(String url){
	// String[] sp = url.split("://");
	// if(sp.length > 0){
	// return sp[0];
	// }
	// return null;
	// }
	//
	// static public String getProtocolValue(String url,String key){
	// String[] sp = url.split("://");
	// if(sp.length > 1){
	// return sp[1].substring(sp[1].indexOf(key + "="));
	// }
	// return null;
	// }

	static public BroswerUrlData getBroswerUrlData(String url) {
		Logger.i("getBroswerUrlData: reday");
		BroswerUrlData data = new BroswerUrlData();
		String[] sp = url.split("://");
		if (sp.length > 0) {
			data.setProtocolHeader(sp[0]);
			if (sp.length > 1) {
				data.setProtocolContent(sp[1]);
				if (data.getProtocolContent().indexOf("=") > 0) {
					// data.setProtocolKey(data.getProtocolContent().substring(0,
					// data.getProtocolContent().indexOf("=")));
					// data.setProtocolValue(data.getProtocolContent().substring(
					// data.getProtocolContent().indexOf("=") + 1));
					String[] sp1 = data.getProtocolContent().split("&");
					Logger.i("sp1.length:" + sp1.length);
					for (int i = 0; i < sp1.length; i++) {
						Logger.i("put:"
								+ sp1[i].substring(0, sp1[i].indexOf("="))
								+ " ： "
								+ sp1[i].substring(sp1[i].indexOf("=") + 1));
						data.getProtocolData().put(
								sp1[i].substring(0, sp1[i].indexOf("=")),
								sp1[i].substring(sp1[i].indexOf("=") + 1));
					}
				}
			}
		}

		Logger.i("getBroswerUrlData: ok");
		Logger.i("getProtocolContent:" + data.getProtocolContent());
		Logger.i("data.getProtocolData().size(): "
				+ data.getProtocolData().size());

		return data;
	}

	// 获取当前版本号
	static public String getAppVersionName(Context context) {
		String versionName = "";
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					"com.guimialliance", 0);
			versionName = packageInfo.versionName;
			if (TextUtils.isEmpty(versionName)) {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionName;
	}

	//
	//
	// /**
	// * 一键分享到微信，微博，相册，邮件
	// *
	// * @param context
	// * @param intent
	// * @param file
	// * @return
	// */
	// public static void showShareDialog(final Context context, String imgpath,
	// ProductGetShareInfoContents contents) {
	// // 过滤数据
	// File file = new File(imgpath);
	// ArrayList<ShareItemInfo> share_infos = getShareIntent(context, file);
	// ArrayList<ShareItemInfo> open_infos = getOpenIntent(context, file);
	//
	// if (share_infos == null && open_infos == null) {
	// return;
	// }
	//
	// ArrayList<ShareItemInfo> infos = null;
	// if (share_infos != null && open_infos == null) {
	// infos = share_infos;
	// } else if (share_infos == null && open_infos != null) {
	// infos = open_infos;
	// } else if (share_infos != null && open_infos != null) {
	// infos = new ArrayList<ShareItemInfo>();
	// for (int i = 0; i < open_infos.size(); i++) {
	// infos.add(open_infos.get(i));
	// }
	// for (int i = 0; i < share_infos.size(); i++) {
	// infos.add(share_infos.get(i));
	// }
	//
	// }
	// if (infos != null && infos.size() > 0) {
	// // 弹窗
	// setShareDialog(context, infos, contents);
	// }
	//
	// }
	//
	// public static void showShareDialog(final Context context, Bitmap mBitmap,
	// ProductGetShareInfoContents contents) {
	//
	// File magazine = null;
	// File screenshot = null;
	// if (Environment.getExternalStorageState().equals(
	// Environment.MEDIA_MOUNTED)) {
	// magazine = new File(Environment.getExternalStorageDirectory(),
	// "magazine");
	// if (!magazine.exists()) {
	// magazine.mkdir();
	// }
	//
	// screenshot = new File(magazine.getAbsolutePath(), "screenshot");
	// if (screenshot.exists()) {
	// screenshot.delete();
	// }
	// try {
	//
	// BufferedOutputStream bos = new BufferedOutputStream(
	// new FileOutputStream(screenshot));
	//
	// mBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
	//
	// bos.flush();
	//
	// bos.close();
	// if (mBitmap != null && !mBitmap.isRecycled()) {
	// mBitmap.recycle();
	// mBitmap = null;
	// }
	// } catch (IOException e) {
	//
	// }
	// }
	//
	// if (screenshot == null || !screenshot.exists()) {
	// return;
	// }
	// // 过滤数据
	// ArrayList<ShareItemInfo> share_infos = getShareIntent(context,
	// screenshot);
	// ArrayList<ShareItemInfo> open_infos = getOpenIntent(context, screenshot);
	//
	// if (share_infos == null && open_infos == null) {
	// return;
	// }
	//
	// ArrayList<ShareItemInfo> infos = null;
	// if (share_infos != null && open_infos == null) {
	// infos = share_infos;
	// } else if (share_infos == null && open_infos != null) {
	// infos = open_infos;
	// } else if (share_infos != null && open_infos != null) {
	// infos = new ArrayList<ShareItemInfo>();
	// for (int i = 0; i < open_infos.size(); i++) {
	// infos.add(open_infos.get(i));
	// }
	// for (int i = 0; i < share_infos.size(); i++) {
	// infos.add(share_infos.get(i));
	// }
	//
	// }
	// if (infos != null && infos.size() > 0) {
	// // 弹窗
	// setShareDialog(context, infos, contents);
	// }
	//
	// }
	//
	// private static void setShareDialog(final Context context,
	// final ArrayList<ShareItemInfo> infos, final ProductGetShareInfoContents
	// contents) {
	// final Dialog dialog = new Dialog(context,
	// R.style.iphone_progress_dialog);
	// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	// dialog.setContentView(R.layout.dialog_share);
	// dialog.setCanceledOnTouchOutside(true);
	// GridView gr = (GridView) dialog.findViewById(R.id.intent_grid);
	// TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
	// tvTitle.setText(contents.getTitle());
	// Button close = (Button) dialog.findViewById(R.id.close_sharedialog);
	// close.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// if (dialog != null && dialog.isShowing()) {
	// dialog.dismiss();
	// }
	// }
	// });
	// gr.setAdapter(new BaseAdapter() {
	//
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// // TODO Auto-generated method stub
	// ShareItemInfo info = (ShareItemInfo) getItem(position);
	// if (convertView == null) {
	// convertView = LayoutInflater.from(context).inflate(
	// R.layout.item_dialog_share_, null);
	// }
	// ImageView icon = (ImageView) convertView
	// .findViewById(R.id.shareitem_icon);
	// TextView appname = (TextView) convertView
	// .findViewById(R.id.shareitem_name);
	// icon.setImageDrawable(info.getIcon());
	// appname.setText(info.getApp_name());
	//
	// return convertView;
	// }
	//
	// @Override
	// public long getItemId(int position) {
	// // TODO Auto-generated method stub
	// return position;
	// }
	//
	// @Override
	// public Object getItem(int position) {
	// // TODO Auto-generated method stub
	// return infos.get(position);
	// }
	//
	// @Override
	// public int getCount() {
	// // TODO Auto-generated method stub
	// return infos.size();
	// }
	// });
	// gr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	//
	// @Override
	// public void onItemClick(AdapterView<?> parent, View view,
	// int position, long id) {
	// // TODO Auto-generated method stub
	// ShareItemInfo info = infos.get(position);
//	 Intent intent = new Intent();
//	 intent.setAction(info.getAction_name());
//	 ComponentName component = new ComponentName(info
//	 .getPackage_name(), info.getActivity_name());
//	 intent.setComponent(component);
//	
//	 if (WEIXIN.equals(info.getPackage_name()) ||
//	 WEIXIN_FRIEND.equals(info.getPackage_name())||
//	 MOBILE_QQ.equals(info.getPackage_name())) {
//	 intent.setType("text/plain");
//	 }else{
//	 if (info.getAction_name().equals(Intent.ACTION_SEND)) {
//	 intent.setType("image/*");
//	 Uri u = Uri.fromFile(info.getFile());
//	 intent.putExtra(Intent.EXTRA_STREAM, u);
//	 } else {
//	 intent.setDataAndType(Uri.fromFile(info.getFile()),
//	 "image/*");
//	 }
//	 }
//	 intent.putExtra(Intent.EXTRA_TEXT, contents.getContent() + " " +
//	 contents.getUrl());
//	 intent.putExtra(Intent.EXTRA_SUBJECT, contents.getTitle());
//	 context.startActivity(intent);
	// if (dialog != null && dialog.isShowing()) {
	// dialog.dismiss();
	// }
	// }
	// });
	// dialog.show();
	//
	// }
	//
	public static Intent getShareIntent(Context context, String pakName) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SEND);
		intent.setType("image/*");
		PackageManager packageManager = context.getPackageManager();
		// 查询是否有该Intent的Activity
		List<ResolveInfo> activities = packageManager.queryIntentActivities(
				intent, 0);

		// Intent rIntent = new Intent();

		for (ResolveInfo ri : activities) {
			String packagename = ri.activityInfo.packageName;
			String activityname = ri.activityInfo.name;
			// ShareItemInfo info = new ShareItemInfo();
			// info.setApp_name(ri.loadLabel(packageManager).toString());
			// info.setFile(file);
			// info.setActivity_name(activityname);
			// info.setPackage_name(packagename);
			// info.setIcon(ri.loadIcon(packageManager));
			// info.setAction_name(Intent.ACTION_SEND);
			// infos.add(info);
			if (pakName.equals(packagename)) {
				ComponentName comp = new ComponentName(packagename,
						activityname);
				Intent rIntent = new Intent(Intent.ACTION_SEND);
				rIntent.setComponent(comp);
				return rIntent;
			}

		}
		return null;
	}
//	 ComponentName comp = new ComponentName("com.sina.weibo", "com.sina.weibo.EditActivity");
//	 Intent intent = new Intent(Intent.ACTION_SEND);
//	 intent.setType("image/*");
//	 intent.putExtra(Intent.EXTRA_TEXT, value);
//	 intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
//	 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//	 intent.setComponent(comp);
	 
	 
	
	//
	// }
	// return infos;
	// }
	//
	// private static ArrayList<ShareItemInfo> getOpenIntent(Context context,
	// File file) {
	// Intent intent = new Intent();
	// intent.setAction(Intent.ACTION_VIEW);
	// intent.setType("image/*");
	// PackageManager packageManager = context.getPackageManager();
	// // 查询是否有该Intent的Activity
	// List<ResolveInfo> activities = packageManager.queryIntentActivities(
	// intent, 0);
	// ArrayList<ShareItemInfo> infos = null;
	// if (activities.size() > 0) {
	// infos = new ArrayList<ShareItemInfo>();
	// for (ResolveInfo ri : activities) {
	// String packagename = ri.activityInfo.packageName;
	// String activityname = ri.activityInfo.name;
	// ShareItemInfo info = new ShareItemInfo();
	// info.setApp_name(ri.loadLabel(packageManager).toString());
	// info.setFile(file);
	// info.setActivity_name(activityname);
	// info.setPackage_name(packagename);
	// info.setIcon(ri.loadIcon(packageManager));
	// info.setAction_name(Intent.ACTION_VIEW);
	// infos.add(info);
	//
	// }
	//
	// }
	// return infos;
	// }

	static public void displayBriefMemory(Context context) {

		final ActivityManager activityManager = (ActivityManager) context
				.getApplicationContext().getSystemService(
						Activity.ACTIVITY_SERVICE);

		ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();

		activityManager.getMemoryInfo(info);

		// Logger.e("", "系统剩余内存:" + (info.availMem >> 10) + "k");

		// Logger.e("", "系统是否处于低内存运行：" + info.lowMemory);

		// Logger.e("", "当系统剩余内存低于" + info.threshold + "时就看成低内存运行");

	}

	// 获取版本号和友盟渠道号
	public static String getVersionAndUmengValue(Activity activity) {
		String msg = "";
		try {
			PackageManager manager = activity.getPackageManager();
			PackageInfo info = manager.getPackageInfo(
					activity.getPackageName(), 0);
			msg = info.versionName;
			ApplicationInfo appInfo = manager.getApplicationInfo(
					activity.getPackageName(), PackageManager.GET_META_DATA);
			msg = "版本号:" + msg + "\n" + "渠道号:"
					+ appInfo.metaData.getString("UMENG_CHANNEL");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 判断是否有符合该Intent相关的APP
	 * 
	 * @param context
	 * @param intent
	 * @return
	 */
	public static boolean hasApplication(Context context, Intent intent) {
		PackageManager packageManager = context.getPackageManager();
		// 查询是否有该Intent的Activity
		List<ResolveInfo> activities = packageManager.queryIntentActivities(
				intent, 0);

		// activities里面不为空就有，否则就没有
		return activities.size() > 0 ? true : false;
	}

	public static Boolean isHasPackageName(Context context, String packageName) {
		// PackageManager packageManager = context.getPackageManager();
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> pakageinfos = pm
				.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		for (PackageInfo packageInfo : pakageinfos) {
			if (packageInfo.packageName.equals(packageName)) {
				return true;
			}
		}
		return false;
	}

	// /**
	// * 直接调用专家用户的接口， 如果成功获取专家用户信息，跳转至专家主页 如果未成功获取用户信息，跳转至普通用户
	// *
	// * @param userid
	// * @param context
	// *
	// * String userid = String.valueOf(v.getTag());
	// * Utility.redirectPersonTimeLine(userid, mContext);
	// */
	// public static void redirectPersonTimeLine(final Activity activity,
	// final String userid) {
	// // ExpertManager.getInstance(context);
	// final LoadingProgressDialog mProgressDialog;
	// mProgressDialog = new LoadingProgressDialog(activity);
	// mProgressDialog.setCancelable(true);
	// mProgressDialog.show();
	// mProgressDialog.setContentView(R.layout.loading_transparent);
	// User user = new User();
	// user.setUserId(String.valueOf(userid));
	// ExpertManager.getExpertInfoRequest(activity, user,
	// new RequestListener() {
	//
	// @Override
	// public void onResult(int isSuccess, Object data) {
	// // TODO Auto-generated method stub
	// if (isSuccess == RequestListener.REQUEST_SUCCESS) {
	// if (data instanceof ExpertData) {
	// final ExpertData datas = (ExpertData) data;
	// Intent intent = new Intent();
	// intent.setClass(activity,
	// ExpertCenterActivity.class);
	// intent.putExtra(
	// com.kituri.app.model.Intent.EXTRA_EXPERT_DATA,
	// datas);
	// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	// activity.startActivity(intent);
	// }
	// } else {
	// Intent todetail = new Intent();
	// todetail.setClass(activity,
	// PersonalCenterActivity.class);
	// todetail.putExtra(
	// com.kituri.app.model.Intent.EXTRA_USER_ID,
	// userid);
	// todetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	// activity.startActivity(todetail);
	//
	// }
	// // LeHandler.getInstance().post(new Runnable() {
	// //
	// // @Override
	// // public void run() {
	// // TODO Auto-generated method stub
	// mProgressDialog.dismiss();
	// // }
	// // });
	// }
	// });
	// }
	//
	// public static void redirectPersonTimeLine(Activity activity, final User
	// user) {
	// if (user != null) {
	// if (user.getCategory() == null && user.getULevel() == null) {
	// redirectPersonTimeLine(activity, user.getUserId());
	// } else {
	// if (user.getCategory() != null || user.getCategory() == 5) {
	// redirectPersonTimeLine(activity, user.getUserId());
	// } else if (user.getULevel() != null || user.getULevel() > 0) {
	// redirectPersonTimeLine(activity, user.getUserId());
	// } else {
	// Intent todetail = new Intent();
	// todetail.setClass(activity, PersonalCenterActivity.class);
	// todetail.putExtra(
	// com.kituri.app.model.Intent.EXTRA_USER_ID,
	// user.getUserId());
	// todetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	// activity.startActivity(todetail);
	// }
	// }
	// }
	//
	// }

	// 老版本获取用户名和密码（兼容老版本使用 和 老版本的登录页记住密码同一方法）
	static public ArrayList<String[]> getUserList(Context mContext) {
		SharedPreferences mSharedPreferences = mContext.getSharedPreferences(
				"utan_login_user_list", Context.MODE_PRIVATE);
		String userList = mSharedPreferences.getString("userList", "");
		ArrayList<String[]> lists = new ArrayList<String[]>();
		if (!userList.equals("")) {
			String[] userItem = userList.split("~");
			for (String item : userItem) {
				lists.add(item.split("\\|"));
			}

		}
		return lists;
	}

	// 从老版本获取用户名和密码（单条）
	static public String[] getAccount(Context mContext) {
		ArrayList<String[]> lists = getUserList(mContext);
		if (lists.size() > 0) {
			String[] accountArray = lists.get(0);
			if (accountArray != null) {
				if (accountArray.length == 2) {
					if (!TextUtils.isEmpty(accountArray[0])
							&& !TextUtils.isEmpty(accountArray[1])) {
						return accountArray;
					}
				}
			}
		}
		return null;
	}

	static public void SaveParcelable(Context context, String tag,
			Parcelable parcelable) {
		FileOutputStream fos = null;
		ObjectOutputStream dos = null;
		// Bundle Bundle = new GameSaveData();
		// gameSaveData.InitData();
		try {
			// ------单纯用file来写入的方式--------------
			// fos = new FileOutputStream(f);
			// fos.write(et_login.getText().toString().getBytes());
			// fos.write(et_password.getText().toString().getBytes());
			// ------data包装后来写入的方式--------------
			fos = context.openFileOutput(tag, Context.MODE_PRIVATE);// 备注2
			dos = new ObjectOutputStream(fos);

			// gameSaveData.InitData();

			dos.writeObject(parcelable);

			// Log.i("Save", "Save IS OK");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 在finally中关闭流 这样即使try中有异常我们也能对其进行关闭操作 ;
			try {
				dos.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static String getCurrentDate() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());

		return sdf.format(c.getTime());
	}

	static public boolean CanLoadParcelable(Context context, String tag) {
		try {
			if (context.openFileInput(tag) == null) {
				return false;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}

	static public Parcelable LoadParcelable(Context context, String tag) {
		// GameSaveData gameSaveData = null;
		// gameSaveData.InitData();
		Parcelable saveData = null;

		FileInputStream fis = null;

		ObjectInputStream dis = null;

		// Log.i("Save", "LoadGame try");

		try {
			// openFileInput 不像 sharedPreference 中
			// getSharedPreferences的方法那样找不到会返回默认值,
			// 这里找不到数据文件就会报异常,所以finally里关闭流尤为重要!!!
			if (context.openFileInput(tag) != null) {
				// Log.i("Save","context.openFileInput(Constants.FileName) != null");
				fis = context.openFileInput(tag);// 备注1
				dis = new ObjectInputStream(fis);

				// Log.i("Save", "(GameSaveData) dis.readObject() reday");

				saveData = (Parcelable) dis.readObject();

				// Log.i("Save", "(GameSaveData) dis.readObject() over");

				// Log.i("Save", "gameSaveData:" + gameSaveData.playerIndex);
				// if (gameSaveData.player == null) {
				// Log.i("Save", "gameSaveData.player == null");
				// }else{
				// Log.i("Save", "gameSaveData.player.length :" +
				// gameSaveData.player.length);
				// }
				// 这里也是在刚启动程序的时候去读入存储的数据
				// 读的时候要注意顺序; 例如我们写入数据的时候
				// 先写的字符串类型,我们也要先读取字符串类型,一一对应！
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// Log.i("Save", "FileNotFoundException e" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// Log.i("Save", "IOException e" + e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			// Log.i("Save", "ClassNotFoundException e" + e.getMessage());
			e.printStackTrace();
		} finally {
			// 在finally中关闭流!因为如果找不到数据就会异常我们也能对其进行关闭操作 ;
			try {
				if (context.openFileInput(tag) != null) {
					// 这里也要判断，因为找不到的情况下，两种流也不会实例化。
					// 既然没有实例化，还去调用close关闭它,肯定"空指针"异常！！！
					fis.close();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return saveData;
	}

	// public static void setTextViewTypeface(ViewGroup viewGroup, Context
	// context){
	// Typeface typeface =
	// Typeface.createFromAsset(context.getAssets(),"font/costom_font.ttf");
	// for(int i = 0; i<viewGroup.getChildCount(); i++ ){
	// View v = viewGroup.getChildAt(i);
	// if(v instanceof ViewGroup){
	// setTextViewTypeface((ViewGroup)v, context);
	// }else if(v instanceof
	// Button){//按钮加大这个一定要放在TextView上面，因为Button也继承了TextView
	// ((Button)v).setTypeface(typeface);
	// }else if(v instanceof TextView){
	// ((TextView)v).setTypeface(typeface);
	// }
	// }
	// }

	// public static void setTextViewTypeface(ViewGroup root, Activity act) {
	//
	// Typeface tf = Typeface.createFromAsset(act.getAssets(),
	// "fonts/costomfont.ttf");
	//
	// for (int i = 0; i < root.getChildCount(); i++) {
	// View v = root.getChildAt(i);
	// if (v instanceof TextView) {
	// ((TextView) v).setTypeface(tf);
	// } else if (v instanceof Button) {
	// ((Button) v).setTypeface(tf);
	// } else if (v instanceof EditText) {
	// ((EditText) v).setTypeface(tf);
	// } else if (v instanceof ViewGroup) {
	// setTextViewTypeface((ViewGroup) v, act);
	// }
	// }
	//
	// }

	public static void setTextViewTypeface(View v, Context context) {

		Typeface tf = Typeface.createFromAsset(context.getAssets(),
				"fonts/costomfont.ttf");
		if (v instanceof TextView) {
			((TextView) v).setTypeface(tf);
		} else if (v instanceof Button) {
			((Button) v).setTypeface(tf);
		} else if (v instanceof EditText) {
			((EditText) v).setTypeface(tf);
		}
	}

	public static void changeViewSize(ViewGroup viewGroup, int screenWidth,
			int screenHeight) {// 传入Activity顶层Layout,屏幕宽,屏幕高

		int adjustFontSize = adjustFontSize(screenWidth, screenHeight);

		for (int i = 0; i < viewGroup.getChildCount(); i++) {

			View v = viewGroup.getChildAt(i);

			if (v instanceof ViewGroup) {

				changeViewSize((ViewGroup) v, screenWidth, screenHeight);

			} else if (v instanceof Button) {// 按钮加大这个一定要放在TextView上面，因为Button也继承了TextView

				((Button) v).setTextSize(adjustFontSize + 2);

			} else if (v instanceof TextView) {

				// if(v.getId()== R.id.title_msg){//顶部标题
				//
				// ( (TextView)v ).setTextSize(adjustFontSize+4);
				//
				// }else{
				//
				// ( (TextView)v ).setTextSize(adjustFontSize);
				//
				// }

				((TextView) v).setTextSize(adjustFontSize);

			}

		}

	}

	// 获取字体大小

	public static int adjustFontSize(int screenWidth, int screenHeight) {

		screenWidth = screenWidth > screenHeight ? screenWidth : screenHeight;

		/**
		 * 
		 * 1. 在视图的 onsizechanged里获取视图宽度，一般情况下默认宽度是320，所以计算一个缩放比率
		 * 
		 * rate = (float) w/320 w是实际宽度
		 * 
		 * 2.然后在设置字体尺寸时 paint.setTextSize((int)(8*rate)); 8是在分辨率宽为320 下需要设置的字体大小
		 * 
		 * 实际字体大小 = 默认字体大小 x rate
		 */

		int rate = (int) (5 * (float) screenWidth / 320); // 我自己测试这个倍数比较适合，当然你可以测试后再修改

		return rate < 15 ? 15 : rate; // 字体太小也不好看的

	}

	public static int dip2px(int dipValue) {
		float reSize = KituriApplication.getApplication().getResources()
				.getDisplayMetrics().density;
		return (int) ((dipValue * reSize) + 0.5);
	}

	public static int px2dip(int pxValue) {
		float reSize = KituriApplication.getApplication().getResources()
				.getDisplayMetrics().density;
		return (int) ((pxValue / reSize) + 0.5);
	}

	public static float sp2px(int spValue) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
				KituriApplication.getApplication().getResources()
						.getDisplayMetrics());
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static void recycleViewGroupAndChildViews(ViewGroup viewGroup,
			boolean recycleBitmap) {
		for (int i = 0; i < viewGroup.getChildCount(); i++) {

			View child = viewGroup.getChildAt(i);

			if (child instanceof WebView) {
				WebView webView = (WebView) child;
				webView.loadUrl("about:blank");
				webView.stopLoading();
				continue;
			}

			if (child instanceof ViewGroup) {
				recycleViewGroupAndChildViews((ViewGroup) child, true);
				continue;
			}

			if (child instanceof ImageView) {
				ImageView iv = (ImageView) child;
				Drawable drawable = iv.getDrawable();
				if (drawable instanceof BitmapDrawable) {
					BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
					Bitmap bitmap = bitmapDrawable.getBitmap();
					if (recycleBitmap && bitmap != null) {
						bitmap.recycle();
					}
				}
				iv.setImageBitmap(null);
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
					iv.setBackground(null);
				} else {
					iv.setBackgroundDrawable(null);
				}

				continue;
			}

			if (child instanceof TextView) {
				TextView tv = (TextView) child;
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
					tv.setBackground(null);
				} else {
					tv.setBackgroundDrawable(null);
				}
				continue;
			}

			// child.setBackground(null);
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
				child.setBackground(null);
			} else {
				child.setBackgroundDrawable(null);
			}
		}

		// viewGroup.setBackground(null);
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
			viewGroup.setBackground(null);
		} else {
			viewGroup.setBackgroundDrawable(null);
		}
	}

	@SuppressLint("NewApi")
	public static void recycleView(View child, boolean recycleBitmap) {
		// View child = viewGroup.getChildAt(i);

		if (child instanceof WebView) {
			WebView webView = (WebView) child;
			webView.loadUrl("about:blank");
			webView.stopLoading();
			// continue;
		}

		if (child instanceof ViewGroup) {
			recycleViewGroupAndChildViews((ViewGroup) child, true);
			// continue;
		}

		if (child instanceof ImageView) {
			ImageView iv = (ImageView) child;
			Drawable drawable = iv.getDrawable();
			if (drawable instanceof BitmapDrawable) {
				BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
				Bitmap bitmap = bitmapDrawable.getBitmap();
				if (recycleBitmap && bitmap != null) {
					bitmap.recycle();
				}
			}
			iv.setImageBitmap(null);
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
				iv.setBackground(null);
			} else {
				iv.setBackgroundDrawable(null);
			}

			// continue;
		}

		// child.setBackground(null);
		// if(android.os.Build.VERSION.SDK_INT >=
		// android.os.Build.VERSION_CODES.JELLY_BEAN){
		// child.setBackground(null);
		// }else{
		// child.setBackgroundDrawable(null);
		// }
	}

	@SuppressLint("NewApi")
	public static boolean doThisDeviceOwnNavigationBar(Context context) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			return false;
		}
		boolean hasMenuKey = ViewConfiguration.get(context)
				.hasPermanentMenuKey();
		boolean hasBackKey = KeyCharacterMap
				.deviceHasKey(KeyEvent.KEYCODE_BACK);

		return !hasMenuKey && !hasBackKey;
	}

	public static String encodeUrl(Map<String, String> param) {
		if (param == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		Set<String> keys = param.keySet();
		boolean first = true;

		for (String key : keys) {
			String value = param.get(key);
			// pain...EditMyProfileDao params' values can be empty
			if (!TextUtils.isEmpty(value) || key.equals("description")
					|| key.equals("url")) {
				if (first) {
					first = false;
				} else {
					sb.append("&");
				}
				try {
					sb.append(URLEncoder.encode(key, "UTF-8")).append("=")
							.append(URLEncoder.encode(param.get(key), "UTF-8"));
				} catch (UnsupportedEncodingException e) {

				}
			}

		}

		return sb.toString();
	}

	public static Bundle decodeUrl(String s) {
		Bundle params = new Bundle();
		if (s != null) {
			String array[] = s.split("&");
			for (String parameter : array) {
				String v[] = parameter.split("=");
				try {
					params.putString(URLDecoder.decode(v[0], "UTF-8"),
							URLDecoder.decode(v[1], "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();

				}
			}
		}
		return params;
	}

	public static void closeSilently(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException ignored) {

			}
		}
	}

	/**
	 * Parse a URL query and fragment parameters into a key-value bundle.
	 */
	public static Bundle parseUrl(String url) {
		// hack to prevent MalformedURLException
		url = url.replace("weiboconnect", "http");
		try {
			URL u = new URL(url);
			Bundle b = decodeUrl(u.getQuery());
			b.putAll(decodeUrl(u.getRef()));
			return b;
		} catch (MalformedURLException e) {
			return new Bundle();
		}
	}

	public static void cancelTasks(MyAsyncTask... tasks) {
		for (MyAsyncTask task : tasks) {
			if (task != null) {
				task.cancel(true);
			}
		}
	}

	public static boolean isTaskStopped(MyAsyncTask task) {
		return task == null || task.getStatus() == MyAsyncTask.Status.FINISHED
				|| task.getStatus() == MyAsyncTask.Status.PENDING;
	}

	// public static void stopListViewScrollingAndScrollToTop(ListView listView)
	// {
	// if (listView instanceof AutoScrollListView) {
	// ((AutoScrollListView) listView).requestPositionToScreen(0, true);
	// } else {
	// listView.smoothScrollToPosition(0, 0);
	// }
	// }
	//
	// public static int dip2px(int dipValue) {
	// float reSize =
	// GlobalContext.getInstance().getResources().getDisplayMetrics().density;
	// return (int) ((dipValue * reSize) + 0.5);
	// }
	//
	// public static int px2dip(int pxValue) {
	// float reSize =
	// GlobalContext.getInstance().getResources().getDisplayMetrics().density;
	// return (int) ((pxValue / reSize) + 0.5);
	// }
	//
	// public static float sp2px(int spValue) {
	// return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
	// GlobalContext.getInstance().getResources().getDisplayMetrics());
	// }

	public static int length(String paramString) {
		int i = 0;
		for (int j = 0; j < paramString.length(); j++) {
			if (paramString.substring(j, j + 1).matches("[Α-￥]")) {
				i += 2;
			} else {
				i++;
			}
		}

		if (i % 2 > 0) {
			i = 1 + i / 2;
		} else {
			i = i / 2;
		}

		return i;
	}

	public static boolean isConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();

		return networkInfo != null && networkInfo.isConnected();
	}

	public static boolean isWifi(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				return true;
			}
		}
		return false;
	}

	public static int getNetType(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return networkInfo.getType();
		}
		return -1;
	}

	public static boolean isGprs(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			if (networkInfo.getType() != ConnectivityManager.TYPE_WIFI) {
				return true;
			}
		}
		return false;
	}

	public static boolean isSystemRinger(Context context) {
		AudioManager manager = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		return manager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL;
	}

	/**
	 * 验证输入的号码是否为手机号
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobile(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	// public static void configVibrateLedRingTone(Notification.Builder builder)
	// {
	// configRingTone(builder);
	// configLed(builder);
	// configVibrate(builder);
	// }
	//
	// private static void configVibrate(Notification.Builder builder) {
	// if (SettingUtility.allowVibrate()) {
	// long[] pattern = {0, 200, 500};
	// builder.setVibrate(pattern);
	// }
	// }
	//
	// private static void configRingTone(Notification.Builder builder) {
	// Uri uri = null;
	//
	// if (!TextUtils.isEmpty(SettingUtility.getRingtone())) {
	// uri = Uri.parse(SettingUtility.getRingtone());
	// }
	//
	// if (uri != null && isSystemRinger(GlobalContext.getInstance())) {
	// builder.setSound(uri);
	// }
	// }
	//
	// private static void configLed(Notification.Builder builder) {
	// if (SettingUtility.allowLed()) {
	// builder.setLights(Color.WHITE, 2000, 2000);
	// }
	//
	// }

	public static String getPicPathFromUri(Uri uri, Activity activity) {
		String value = uri.getPath();

		if (value.startsWith("/external")) {
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = activity.managedQuery(uri, proj, null, null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} else {
			return value;
		}
	}

	public static boolean isAllNotNull(Object... obs) {
		for (int i = 0; i < obs.length; i++) {
			if (obs[i] == null) {
				return false;
			}
		}
		return true;
	}

	// public static boolean isGPSLocationCorrect(GeoBean geoBean) {
	// double latitude = geoBean.getLat();
	// double longitude = geoBean.getLon();
	// if (latitude < -90.0 || latitude > 90.0) {
	// return false;
	// }
	// if (longitude < -180.0 || longitude > 180.0) {
	// return false;
	// }
	// return true;
	// }

	public static boolean isIntentSafe(Activity activity, Uri uri) {
		Intent mapCall = new Intent(Intent.ACTION_VIEW, uri);
		PackageManager packageManager = activity.getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(
				mapCall, 0);
		return activities.size() > 0;
	}

	public static boolean isIntentSafe(Activity activity, Intent intent) {
		PackageManager packageManager = activity.getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(
				intent, 0);
		return activities.size() > 0;
	}

	public static boolean isGooglePlaySafe(Activity activity) {
		Uri uri = Uri
				.parse("http://play.google.com/store/apps/details?id=com.google.android.gms");
		Intent mapCall = new Intent(Intent.ACTION_VIEW, uri);
		mapCall.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
		mapCall.setPackage("com.android.vending");
		PackageManager packageManager = activity.getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(
				mapCall, 0);
		return activities.size() > 0;
	}

	public static boolean isSinaWeiboSafe(Activity activity) {
		Intent mapCall = new Intent("com.sina.weibo.remotessoservice");
		PackageManager packageManager = activity.getPackageManager();
		List<ResolveInfo> services = packageManager.queryIntentServices(
				mapCall, 0);
		return services.size() > 0;
	}
	/**
	 * 
	 * 
	 * @param context
	 * @param pakgName
	 * @return
	 */
	public static boolean checkHasPackage(Context context, String pakgName) {
		
	      Intent intent = new Intent(Intent.ACTION_SEND);
	      intent.setType("text/plain");
	      List<ResolveInfo> resInfo =context.getPackageManager().queryIntentActivities( intent, 0);
	      if (!resInfo.isEmpty()) {
	        for (ResolveInfo info : resInfo) {
	           ActivityInfo activityInfo = info.activityInfo;
	           if (activityInfo.packageName.contains(pakgName)) {
	             return true;
	           }
	        }
	      }
	      return false;
	   }

	public static String buildTabText(int number) {

		if (number == 0) {
			return null;
		}

		String num;
		if (number < 99) {
			num = "(" + number + ")";
		} else {
			num = "(99+)";
		}
		return num;

	}

	public static boolean isJB() {
		return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}

	public static boolean isKK() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
	}

	public static int getScreenWidth() {
		Activity activity = KituriApplication.getInstance().getActivity();
		if (activity != null) {
			Display display = activity.getWindowManager().getDefaultDisplay();
			DisplayMetrics metrics = new DisplayMetrics();
			display.getMetrics(metrics);
			return metrics.widthPixels;
		}

		return 480;
	}

	public static int getScreenHeight() {
		Activity activity = KituriApplication.getInstance().getActivity();
		if (activity != null) {
			Display display = activity.getWindowManager().getDefaultDisplay();
			DisplayMetrics metrics = new DisplayMetrics();
			display.getMetrics(metrics);
			return metrics.heightPixels;
		}
		return 800;
	}

	public static String getLatestCameraPicture(Activity activity) {
		String[] projection = new String[] {
				MediaStore.Images.ImageColumns._ID,
				MediaStore.Images.ImageColumns.DATA,
				MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
				MediaStore.Images.ImageColumns.DATE_TAKEN,
				MediaStore.Images.ImageColumns.MIME_TYPE };
		final Cursor cursor = activity.getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
				null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
		if (cursor.moveToFirst()) {
			String path = cursor.getString(1);
			return path;
		}
		return null;
	}

	public static void copyFile(InputStream in, File destFile)
			throws IOException {
		BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
		FileOutputStream outputStream = new FileOutputStream(destFile);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
				outputStream);
		byte[] buffer = new byte[1024];
		int len;
		while ((len = bufferedInputStream.read(buffer)) != -1) {
			bufferedOutputStream.write(buffer, 0, len);
		}
		closeSilently(bufferedInputStream);
		closeSilently(bufferedOutputStream);
	}

	public static Rect locateView(View v) {
		int[] location = new int[2];
		if (v == null) {
			return null;
		}
		try {
			v.getLocationOnScreen(location);
		} catch (NullPointerException npe) {
			// Happens when the view doesn't exist on screen anymore.
			return null;
		}
		Rect locationRect = new Rect();
		locationRect.left = location[0];
		locationRect.top = location[1];
		locationRect.right = locationRect.left + v.getWidth();
		locationRect.bottom = locationRect.top + v.getHeight();
		return locationRect;
	}

	public static int countWord(String content, String word, int preCount) {
		int count = preCount;
		int index = content.indexOf(word);
		if (index == -1) {
			return count;
		} else {
			count++;
			return countWord(content.substring(index + word.length()), word,
					count);
		}
	}

	// public static void setShareIntent(Activity activity, ShareActionProvider
	// mShareActionProvider,
	// MessageBean msg) {
	// Intent shareIntent = new Intent(Intent.ACTION_SEND);
	// if (msg != null && msg.getUser() != null) {
	// shareIntent.setType("text/plain");
	// shareIntent.putExtra(Intent.EXTRA_TEXT,
	// "@" + msg.getUser().getScreen_name() + "：" + msg.getText());
	// if (!TextUtils.isEmpty(msg.getThumbnail_pic())) {
	// Uri picUrl = null;
	// String smallPath = FileManager.getFilePathFromUrl(msg.getThumbnail_pic(),
	// FileLocationMethod.picture_thumbnail);
	// String middlePath = FileManager.getFilePathFromUrl(msg.getBmiddle_pic(),
	// FileLocationMethod.picture_bmiddle);
	// String largePath = FileManager.getFilePathFromUrl(msg.getOriginal_pic(),
	// FileLocationMethod.picture_large);
	// if (new File(largePath).exists()) {
	// picUrl = Uri.fromFile(new File(largePath));
	// } else if (new File(middlePath).exists()) {
	// picUrl = Uri.fromFile(new File(middlePath));
	// } else if (new File(smallPath).exists()) {
	// picUrl = Uri.fromFile(new File(smallPath));
	// }
	// if (picUrl != null) {
	// shareIntent.putExtra(Intent.EXTRA_STREAM, picUrl);
	// shareIntent.setType("image/*");
	// }
	// }
	// if (Utility.isIntentSafe(activity, shareIntent) && mShareActionProvider
	// != null) {
	// mShareActionProvider.setShareIntent(shareIntent);
	// }
	// }
	// }
	//
	// public static void setShareIntent(Activity activity, ShareActionProvider
	// mShareActionProvider,
	// String content) {
	// Intent shareIntent = new Intent(Intent.ACTION_SEND);
	// shareIntent.setType("text/plain");
	// shareIntent.putExtra(Intent.EXTRA_TEXT, content);
	// if (Utility.isIntentSafe(activity, shareIntent) && mShareActionProvider
	// != null) {
	// mShareActionProvider.setShareIntent(shareIntent);
	// }
	//
	// }

	// public static void buildTabCount(ActionBar.Tab tab, String tabStrRes, int
	// count) {
	// if (tab == null) {
	// return;
	// }
	// String content = tab.getText().toString();
	// int value = 0;
	// int start = content.indexOf("(");
	// int end = content.lastIndexOf(")");
	// if (start > 0) {
	// String result = content.substring(start + 1, end);
	// value = Integer.valueOf(result);
	// }
	// if (value <= count) {
	// tab.setText(tabStrRes + "(" + count + ")");
	// }
	// }
	//
	// public static void buildTabCount(TextView tab, String tabStrRes, int
	// count) {
	// if (tab == null) {
	// return;
	// }
	// // String content = tab.getText().toString();
	// // int value = 0;
	// // int start = content.indexOf("(");
	// // int end = content.lastIndexOf(")");
	// // if (start > 0) {
	// // String result = content.substring(start + 1, end);
	// // value = Integer.valueOf(result);
	// // }
	// // if (value <= count) {
	// tab.setText(" " + count + " " + tabStrRes);
	// // }
	// }
	//
	// //to do getChildAt(0)
	// public static TimeLinePosition getCurrentPositionFromListView(ListView
	// listView) {
	// View view = listView.getChildAt(1);
	// int top = (view != null ? view.getTop() : 0);
	// return new TimeLinePosition(listView.getFirstVisiblePosition(), top);
	// }

	public static void vibrate(Context context, View view) {
		// Vibrator vibrator = (Vibrator)
		// context.getSystemService(Context.VIBRATOR_SERVICE);
		// vibrator.vibrate(30);
		view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
	}

	public static void playClickSound(View view) {
		view.playSoundEffect(SoundEffectConstants.CLICK);
	}

	public static View getListViewItemViewFromPosition(ListView listView,
			int position) {
		return listView.getChildAt(position
				- listView.getFirstVisiblePosition());
	}

	public static String getMotionEventStringName(MotionEvent event) {
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			return "MotionEvent.ACTION_DOWN";
		case MotionEvent.ACTION_UP:
			return "MotionEvent.ACTION_UP";
		case MotionEvent.ACTION_CANCEL:
			return "MotionEvent.ACTION_CANCEL";
		case MotionEvent.ACTION_MOVE:
			return "MotionEvent.ACTION_MOVE";
		default:
			return "Other";
		}
	}

	public static int getMaxLeftWidthOrHeightImageViewCanRead(int heightOrWidth) {
		// 1pixel==4bytes
		// http://stackoverflow.com/questions/13536042/android-bitmap-allocating-16-bytes-per-pixel
		// http://stackoverflow.com/questions/15313807/android-maximum-allowed-width-height-of-bitmap
		int[] maxSizeArray = new int[1];
		GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxSizeArray, 0);

		if (maxSizeArray[0] == 0) {
			GLES10.glGetIntegerv(GL11.GL_MAX_TEXTURE_SIZE, maxSizeArray, 0);
		}
		int maxHeight = maxSizeArray[0];
		int maxWidth = maxSizeArray[0];

		return (maxHeight * maxWidth) / heightOrWidth;
	}

	// sometime can get value, sometime can't, so I define it is 2048x2048
	public static int getBitmapMaxWidthAndMaxHeight() {
		int[] maxSizeArray = new int[1];
		GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxSizeArray, 0);

		if (maxSizeArray[0] == 0) {
			GLES10.glGetIntegerv(GL11.GL_MAX_TEXTURE_SIZE, maxSizeArray, 0);
		}
		// return maxSizeArray[0];
		return 2048;
	}

	public static Rect getImageViewRect(ImageView imageView) {
		// ImageView imageView = view.getImageView();
		Rect rect = new Rect();
		// if(imageView.getScaleType() == ScaleType.FIT_CENTER){
		Drawable drawable = imageView.getDrawable();
		Bitmap bitmap = null;
		if (drawable instanceof BitmapDrawable) {
			bitmap = ((BitmapDrawable) drawable).getBitmap();
		}

		boolean result = imageView.getGlobalVisibleRect(rect);

		boolean checkWidth = rect.width() < imageView.getWidth();
		boolean checkHeight = rect.height() < imageView.getHeight();

		boolean clipped = !result || checkWidth || checkHeight;

		if (bitmap != null && !clipped) {

			int bitmapWidth = bitmap.getWidth();
			int bitmapHeight = bitmap.getHeight();

			int imageViewWidth = imageView.getWidth();
			int imageviewHeight = imageView.getHeight();

			float startScale;
			if ((float) imageViewWidth / bitmapWidth > (float) imageviewHeight
					/ bitmapHeight) {
				// Extend start bounds horizontally
				startScale = (float) imageviewHeight / bitmapHeight;

			} else {
				startScale = (float) imageViewWidth / bitmapWidth;

			}

			bitmapHeight = (int) (bitmapHeight * startScale);
			bitmapWidth = (int) (bitmapWidth * startScale);

			int deltaX = (imageViewWidth - bitmapWidth) / 2;
			int deltaY = (imageviewHeight - bitmapHeight) / 2;

			rect.set(rect.left + deltaX, rect.top + deltaY,
					rect.right - deltaX, rect.bottom - deltaY);

		}

		if (!clipped && bitmap != null) {
			return rect;
		}
		return null;
		// }else{
		// boolean result = imageView.getGlobalVisibleRect(rect);
		// if(result){
		// // LayoutParams lp = imageView.getLayoutParams();
		// // if(lp instanceof RelativeLayout.LayoutParams){
		// // RelativeLayout.LayoutParams llp = (RelativeLayout.LayoutParams)
		// lp;
		// // rect.set(rect.left - llp.leftMargin, rect.top - llp.topMargin,
		// // rect.right + llp.rightMargin, rect.bottom + llp.bottomMargin);
		// // }else if(lp instanceof LinearLayout.LayoutParams){
		// // LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) lp;
		// // rect.set(rect.left - llp.leftMargin, rect.top - llp.topMargin,
		// // rect.right + llp.rightMargin, rect.bottom + llp.bottomMargin);
		// // }else if(lp instanceof FrameLayout.LayoutParams){
		// // FrameLayout.LayoutParams llp = (FrameLayout.LayoutParams) lp;
		// // rect.set(rect.left - llp.leftMargin, rect.top - llp.topMargin,
		// // rect.right + llp.rightMargin, rect.bottom + llp.bottomMargin);
		// // }
		// return rect;
		// }else{
		// return null;
		// }
		// }

	}

}
