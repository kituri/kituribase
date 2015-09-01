//package com.kituri.net;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class ImageCacheHelper extends SQLiteOpenHelper {
//	private static ImageCacheHelper mDateBaseHelper;
//
//	private ImageCacheHelper(Context context, String name,
//			CursorFactory factory, int version) {
//		super(context, name, factory, version);
//	}
//
//	public static ImageCacheHelper getInstance(Context context, String name) {
//		if (mDateBaseHelper == null) {
//			mDateBaseHelper = new ImageCacheHelper(context, name);
//
//		}
//		return mDateBaseHelper;
//	}
//
//	private ImageCacheHelper(Context context, String name) {
//		this(context, name, null, DATABASE_VERSION);
//	}
//
//	public static final String DATABASE_NAME = "cache.db";
//	public static final int DATABASE_VERSION = 1;
//
//	public static final String TAB_NAME = CacheManager.CacheFolder + "Cache";
//
//	public static final String URL = "url";
//	public static final String POSTDATA = "postdata";
//	public static final String FILENAME = "filename";
//	public static final String LASTACCESSTIME = "lastaccesstime";
//	public static final String RESERVED = "reserved";
//
//	@Override
//	public void onCreate(SQLiteDatabase db) {
//		db.execSQL("CREATE TABLE " + TAB_NAME + " ( " + "_id"
//				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + URL
//				+ " TEXT UNIQUE ON CONFLICT REPLACE," + POSTDATA + " TEXT,"
//				+ FILENAME + " TEXT," + LASTACCESSTIME + " TEXT," + RESERVED
//				+ " TEXT," + "CONSTRAINT UNKEY UNIQUE(" + URL + ")" + ");");
//	}
//
//	@Override
//	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		db.execSQL("DROP TABLE IF EXISTS " + TAB_NAME);
//		onCreate(db);
//	}
//
//}
