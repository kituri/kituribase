package com.kituri.ams;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.InputStreamRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;



public class AmsNetworkHandler {
	//private static boolean mVisiable = false;

//	private ExecutorService executorService = Executors.newFixedThreadPool(4);
	
	private static AmsNetworkHandler mInstance;

	private RequestQueue mRequestQueue;
	
	private Context mContext;
	
	static public final int SOCKET_TIMEOUT = 15000;
	static public final int MAX_RETRIES = 1;
	
//	static private int pollingTimeout = 0;
	
	//executorService.submit 
	
	public static synchronized AmsNetworkHandler getInstance(Context context) {
		if (mInstance == null) {
			//init(context);
			mInstance = new AmsNetworkHandler(context);
		}
		return mInstance;
	}
	
	public AmsNetworkHandler(Context context){
		this.mContext = context;
		getRequestQueue(context);
	}
	
	public RequestQueue getRequestQueue(Context context){
	    if(mRequestQueue == null){
	        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
	    }
	    return mRequestQueue;
	}
	
	public void stop(Object object){
		getRequestQueue(mContext).cancelAll(object);
	}
	
	public void executeHttpGet(final AmsRequest amsRequest, final AmsRequestParameters parameters ,final Listener<JSONObject> response, final ErrorListener errorListener) {
		getRequestQueue(mContext).add(new JsonObjectRequest(Request.Method.GET, amsRequest.getUrl(), null, response, errorListener){
					@Override
					public Map<String, String> getHeaders()
							throws AuthFailureError {
						return AmsNetworkHandler.getHeaders(parameters);
					}
			
//					@Override
//					public RetryPolicy getRetryPolicy() {
//						return AmsNetworkHandler.getRetryPolicy();
//					}
					
//				    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
//				    	return super.setRetryPolicy(getRetryPolicy());
//				    }
			
		});  
		getRequestQueue(mContext).start();
	}
	
	public void executeHttpGetString(final AmsRequest amsRequest, final AmsRequestParameters parameters, final Listener<String> response, final ErrorListener errorListener) {
		getRequestQueue(mContext).add(new StringRequest(amsRequest.getUrl(), response, errorListener));
		getRequestQueue(mContext).start();
	}
	
	public void executeHttpGetInputStream(final AmsRequest amsRequest, final AmsRequestParameters parameters, final Listener<ByteArrayInputStream> response, final ErrorListener errorListener) {
		getRequestQueue(mContext).add(new InputStreamRequest(amsRequest.getUrl(), response, errorListener));
		getRequestQueue(mContext).start();
	}
	
	public void executeHttpPost(
			final AmsRequest amsRequest, final AmsRequestParameters parameters ,
			final Listener<JSONObject> response, final ErrorListener errorListener) {
		
		
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Method.POST, amsRequest.getUrl(), amsRequest.getPost(),
				response, errorListener)
			    {
			    //注意此处override的getParams()方法,在此处设置post需要提交的参数根本不起作用
			    //必须象上面那样,构成JSONObject当做实参传入JsonObjectRequest对象里
			    //所以这个方法在此处是不需要的
//			    @Override
//			    protected Map<String, String> getParams() {                
//			          Map<String, String> map = new HashMap<String, String>();  
//			            map.put("name1", "value1");  
//			            map.put("name2", "value2");  
			                 
//			        return params;
//			    }
			             
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				return AmsNetworkHandler.getHeaders(parameters);
			}
			};
//		
//		getRequestQueue(mContext).add(new NormalPostRequest(amsRequest.getUrl(), response, errorListener, post){
//			@Override
//			public Map<String, String> getHeaders() throws AuthFailureError {
//				return AmsNetworkHandler.getHeaders(parameters);
//			}
//			
////			@Override
////			public RetryPolicy getRetryPolicy() {
////				return AmsNetworkHandler.getRetryPolicy();
////			}
////			
////		    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
////		    	return super.setRetryPolicy(getRetryPolicy());
////		    }
//			
//			@Override
//			protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
//				return AmsNetworkHandler.parseNetworkResponse(response);
//			}
//			
//		});  
		getRequestQueue(mContext).add(jsonRequest);
		//getRequestQueue(mContext).s
	}
	
	private static Map<String, String> getHeaders(final AmsRequestParameters parameters) throws AuthFailureError {
		if(parameters != null){
			Map<String, String> headers = parameters.getRequestParameters();
//			headers.put("Charset", "UTF-8");  
//			headers.put("Content-Type", "application/x-javascript");  
//			headers.put("Accept-Encoding", "gzip,deflate");  
			return headers;
		}
		return Collections.emptyMap();
	}	
	
	
    public static Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

//	private static RetryPolicy getRetryPolicy() {
//		if(pollingTimeout == 0){
//			pollingTimeout = PsPushUserData.getControlParamsPollingTimeout(context);
//		}
//		RetryPolicy retryPolicy = new DefaultRetryPolicy(
//				pollingTimeout > SOCKET_TIMEOUT ? pollingTimeout : SOCKET_TIMEOUT,
//				MAX_RETRIES,
//				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//		return retryPolicy;
//	}
	
//	private Map<String,String> getParams(String post){
//		Map<String,String> params = new HashMap<String, String>();
//		String[] s = post.split("&");
//		if(s != null){
//			for(String str : s){
//				String[] s1 = str.split("=");
//				if(s1.length == 2){
//					params.put(s1[0], s1[1]);
//				}
//				
//			}
//		}
//		return params;
//	}	
//	
//	public Future<Boolean> executeHttpPost(final Context context, final String url,
//			final String post, final ILeHttpCallback response) {
//		Future<Boolean> future =  executorService.submit(new Callable<Boolean>() {
//		
//		@Override
//		public Boolean call() throws Exception {
//			// TODO Auto-generated method stub
//			NetworkHttpRequest request = new NetworkHttpRequest();
////			if (url.startsWith("null")) {
////				url = PsServerInfo.queryServerUrl(context, AmsRequest.SID)
////						+ url.substring(4);
////			}
//			NetworkHttpRequest.HttpReturn ret = request.executeHttpPost(context,
//					url, post, 1, false, null);
//			if (ret.code == 308) {
//				//registClientInfo(context, request, response);
//				ret = request.executeHttpPost(context, url, post, 1, false, null);
//				if (ret.code == 308) {
//					response.onReturn(ret.code, ret.bytes);
//				}
//			}
//			if (ret.code == 401) {
//				//mVisiable = true;
//				ret = request.executeHttpPost(context, url, post, 1, true, null);
//			}
//			response.onReturn(ret.code, ret.bytes);
//			return true;
//		}
//		
//		});
//		return future;
//	}
//	
//	public Future<Boolean> executeHttpGet(final String url,
//			final ILeHttpCallback response) {
//		// TODO Auto-generated method stub
//		
//		Future<Boolean> future =  executorService.submit(new Callable<Boolean>() {
//			
////			@Override
////			public void run() {
////				// TODO Auto-generated method stub
////				
////			}
//
//			@Override
//			public Boolean call() throws Exception {
//				// TODO Auto-generated method stub
//				NetworkHttpRequest request = new NetworkHttpRequest();
////				if (url.startsWith("null")) {
////					url = PsServerInfo.queryServerUrl(context, AmsRequest.SID)
////							+ url.substring(4);
////				} 
//				NetworkHttpRequest.HttpReturn ret = request.executeHttpGet(
//						url, false, null);
//				if (ret.code == 308) {
//					//registClientInfo(context, request, response);
//					ret = request.executeHttpGet( url, false, null);
//					if (ret.code == 308) {
//						response.onReturn(ret.code, ret.bytes);
//					}
//				}
//				if (ret.code == 401) {
//					//mVisiable = true;
//					ret = request.executeHttpGet( url, true, null);
//				}
//				response.onReturn(ret.code, ret.bytes);
//				return true;
//			}
//		});
//		return future;
//		
//	}
//	
//	public Future<Boolean> executeFileHttpPost(final Context context,
//			final AmsRequest amsRequest, final String post,final AmsRequestParameters parameters ,
//			final ILeHttpCallback response) {
//		// TODO Auto-generated method stub
//		Future<Boolean> future =  executorService.submit(new Callable<Boolean>() {
//			
//			@Override
//			public Boolean call() throws Exception {
//		String url = amsRequest.getUrl();
//		NetworkHttpRequest request = new NetworkHttpRequest();
////		if (url.startsWith("null")) {
////			url = PsServerInfo.queryServerUrl(context, AmsRequest.SID)
////					+ url.substring(4);
////		}
//		NetworkHttpRequest.HttpReturn ret = request.executeFileHttpPost(context,
//				url, post, 1, false, parameters);
//		if (ret.code == 308) {
//			//registClientInfo(context, request, response);
//			ret = request.executeFileHttpPost(context, url, post, 1, false, parameters);
//			if (ret.code == 308) {
//				response.onReturn(ret.code, ret.bytes);
//			}
//		}
//		if (ret.code == 401) {
//			//mVisiable = true;
//			ret = request.executeFileHttpPost(context, url, post, 1, true, parameters);
//		}
//		response.onReturn(ret.code, ret.bytes);
//		return true;
//			}
//			
//		});
//		return future;
//	}
//	
//	public Future<Boolean> executeFileHttpPosts(final Context context,
//			final AmsRequest amsRequest, final String[] post,final AmsRequestParameters parameters ,
//			final ILeHttpCallback response) {
//		// TODO Auto-generated method stub
//		Future<Boolean> future =  executorService.submit(new Callable<Boolean>() {
//			
//			@Override
//			public Boolean call() throws Exception {
//		String url = amsRequest.getUrl();
//		NetworkHttpRequest request = new NetworkHttpRequest();
////		if (url.startsWith("null")) {
////			url = PsServerInfo.queryServerUrl(context, AmsRequest.SID)
////					+ url.substring(4);
////		}
//		NetworkHttpRequest.HttpReturn ret = request.executeFileHttpPost(context,
//				url, post, 1, false, parameters);
//		if (ret.code == 308) {
//			//registClientInfo(context, request, response);
//			ret = request.executeFileHttpPost(context, url, post, 1, false, parameters);
//			if (ret.code == 308) {
//				response.onReturn(ret.code, ret.bytes);
//			}
//		}
//		if (ret.code == 401) {
//			//mVisiable = true;
//			ret = request.executeFileHttpPost(context, url, post, 1, true, parameters);
//		}
//		response.onReturn(ret.code, ret.bytes);
//		return true;
//			}
//			
//		});
//		return future;
//	}
}
