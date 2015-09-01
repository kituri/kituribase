package com.kituri.ams;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;

public interface AmsRequest {
//	public static final String SID = "rapp001";
//	public static final String RID = "appstore.lps.lenovo.com";

	static public final int REQUEST_OTHER = 0;
	static public final int REQUEST_MALL = 1;
    public interface Method {
        int DEPRECATED_GET_OR_POST = -1;
        int GET = 0;
        int POST = 1;
        /**
         * 默认是GET，拿到的是JSON字符串
         * GET_STRING 那到的是原网页的HTML字符串
         */
        int GET_STRING = 3;
//        int PUT = 2;
//        int DELETE = 3;
//        int HEAD = 4;
//        int OPTIONS = 5;
//        int TRACE = 6;
//        int PATCH = 7;
    }
	
    public interface PRIORITY{
    	String DEFAULT = "high";
    	String IMAGE = "low";
    	String FILE = "file";
    	String FILES = "files";
    }
    
	public String getUrl();

	public JSONObject getPost();

	public int getHttpMode();

	public String getPriority();

	public String getRequestMethod();
	
	public Boolean isUseUICache();
	
	public String getFileName();
	
}
