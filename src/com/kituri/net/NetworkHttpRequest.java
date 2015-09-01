package com.kituri.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.kituri.ams.AmsRequestParameters;
import com.kituri.app.push.PsPushUserData;
//import com.umeng.analytics.//MobclickAgent;



//import com.kituri.leos.sns.SnsRegistClientInfoRequest.SnsRegistClientInfoResponse;
//import com.kituri.leos.sns.SnsRequest;

/**
 * HTTP request toolkit class
 * 
 */
public class NetworkHttpRequest {
	private static final String TAG = "NetworkHttpRequest";

	static final public class HttpReturn {
		public int code;
		public byte[] bytes;
		public JSONObject mJSONObject;
		public VolleyError mVolleyError;

		public HttpReturn() {
			code = -1;
			bytes = new byte[0];
			mJSONObject = new JSONObject();
			mVolleyError = new VolleyError();
		}
	}

	private static final int DEFAULTTIMEOUT = 10000;

	public HttpReturn executeHttpGet(String url,
			boolean visiable , final AmsRequestParameters parameters) {
		HttpGet httpget = null;
		HttpReturn ret = new HttpReturn();
		long startTime = System.currentTimeMillis();
		try {
			//Logger.i(TAG, url);
			//Logger.i(TAG, "Request start time:" + startTime);
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, DEFAULTTIMEOUT);
			HttpConnectionParams.setSoTimeout(params, DEFAULTTIMEOUT);
			HttpClientParams.setRedirecting(params, true);

			DefaultHttpClient httpClient = new DefaultHttpClient(params);
			httpget = new HttpGet(url);
			if(parameters != null){
				Iterator<Entry<String, String>> iter = parameters.getRequestParameters().entrySet().iterator();
				Entry<String, String> entry = null;
				while (iter.hasNext()) {
					entry = (Entry<String, String>) iter.next();	
					httpget.addHeader(entry.getKey(), entry.getValue());
				}
			}
			
			//httpget.addHeader(header);
			HttpResponse response = httpClient.execute(httpget);
			int code = response.getStatusLine().getStatusCode();
			byte[] bytes = EntityUtils.toByteArray(response.getEntity());
			httpClient.getConnectionManager().shutdown();

			//Log.i(TAG, "ResponseCode: " + code);
			//Log.i(TAG, "Responsebody: " + bytes);

			ret.code = code;
			ret.bytes = bytes;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(httpget != null){
				httpget.abort();
			}
		}
		long endTime = System.currentTimeMillis();
		//Logger.i(TAG, "Request end time:" + endTime);
		//Logger.i(TAG, "Request time consuming = " + (endTime - startTime));
		return ret;
	}

	public HttpReturn httpURLConnectionGet(String _url,Boolean visiable){
		return httpURLConnectionGet(_url);
	}
	
	public HttpReturn httpURLConnectionGet(String _url) {
		//Logger.i(TAG, "httpURLConnectionGet");
	    HttpURLConnection conn = null;
	    HttpReturn ret = new HttpReturn();
	    long startTime = System.currentTimeMillis();
	    try {
			//Logger.i(TAG, _url);
			//Logger.i(TAG, "Request start time:" + startTime);
	      if (_url != null) {
	        _url = _url.replaceAll(" ", "%20");
	      }
	      URL url = new URL(_url);
	      
	        conn = (HttpURLConnection)url.openConnection();
	      
	      conn.setInstanceFollowRedirects(true);
	      conn.setDoInput(true);

	      conn.setConnectTimeout(30000);
	      conn.setReadTimeout(30000);

	      conn.setRequestMethod("GET");
	      conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
	      conn.setRequestProperty("Accept-Language", "zh-CN");
	      //conn.setRequestProperty("Referer", _url); 
	      conn.setRequestProperty("Charset", "UTF-8");

	      conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
	      conn.setRequestProperty("Connection", "Keep-Alive");

	      InputStream ins = conn.getInputStream();
	      if (ins != null) {
	        InputStreamReader in = new InputStreamReader(ins);
	        BufferedReader buffer = new BufferedReader(in, 8192);
	        String inputLine = "";
	        String resultData = null;
	        while ((inputLine = buffer.readLine()) != null) {
	          resultData = resultData + inputLine;
	        }
	        buffer.close();
	        in.close();
	        ins.close();
	        if ((resultData != null) && (resultData.startsWith("null"))) {
	          resultData = resultData.substring(4);
	        }
	        //String str1 = resultData;
			ret.code = conn.getResponseCode();
			ret.bytes = resultData.getBytes();
	        //return str1;
	      }
	    }
	    catch (MalformedURLException e) {
	     
	    } catch (IOException e) {
	      
	    }
	    
		//long endTime = System.currentTimeMillis();
		//Logger.i(TAG, "Request end time:" + endTime);
		//Logger.i(TAG, "Request time consuming = " + (endTime - startTime));
	    
	    return ret;
	    
	}
	
	public HttpReturn httpURLConnectionPost(String _url, String _postData, AmsRequestParameters parameters){
		HttpReturn ret = new HttpReturn();
//		ret.code = conn.getResponseCode();
//		ret.bytes = resultData.getBytes();
		// Post方式请求
		try {
			//String path = "https://reg.163.com/logins.jsp";
			// 请求的参数转换为byte数组
			byte[] postData = _postData.getBytes();
			// 新建一个URL对象
			URL url = new URL(_url);
			// 打开一个HttpURLConnection连接
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			// 设置连接超时时间
			urlConn.setConnectTimeout(5 * 1000);
			// Post请求必须设置允许输出
			urlConn.setDoOutput(true);
			// Post请求不能使用缓存
			urlConn.setUseCaches(false);
			// 设置为Post请求
			urlConn.setRequestMethod("POST");
			urlConn.setInstanceFollowRedirects(true);
			// 配置请求Content-Type
			urlConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencode");
			if(parameters != null){
				Iterator<Entry<String, String>> iter = parameters.getRequestParameters().entrySet().iterator();
				Entry<String, String> entry = null;
				while (iter.hasNext()) {
					entry = (Entry<String, String>) iter.next();	
					//httppost.addHeader(entry.getKey(), entry.getValue());
					urlConn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			// 开始连接
			urlConn.connect();
			// 发送请求参数
			DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
			dos.write(postData);
			dos.flush();
			dos.close();
			// 判断请求是否成功
			InputStream ins = urlConn.getInputStream();
			 if (ins != null) {
			        InputStreamReader in = new InputStreamReader(ins);
			        BufferedReader buffer = new BufferedReader(in, 8192);
			        String inputLine = "";
			        String resultData = null;
			        while ((inputLine = buffer.readLine()) != null) {
			          resultData = resultData + inputLine;
			        }
			        buffer.close();
			        in.close();
			        ins.close();
			        if ((resultData != null) && (resultData.startsWith("null"))) {
			        	resultData = resultData.substring(4);
			        }
			        //String str1 = resultData;
					
					ret.code = urlConn.getResponseCode();
					if(resultData!=null){
						ret.bytes = resultData.getBytes();
					}
			        //return str1;
			      }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//Logger.e("net error", e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}
	
	public HttpReturn executeHttpPost222(Context context, String url, String post,
			int requestFrom, boolean visiable, final AmsRequestParameters parameters)
	{
		HttpReturn ret = new HttpReturn();
	    //URL= your url
	    InputStream is = null;
//	    String val1;
//	    String val2;
	    List<BasicNameValuePair> namevaluepair = new  ArrayList<BasicNameValuePair>();
	    HttpResponse httpResponse = null;
	    //Making HTTP request
	    try {
	        // defaultHttpClient
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	  
//	        namevaluepair.add(new BasicNameValuePair("val1",val1));
//	        namevaluepair.add(new BasicNameValuePair("val2",val2));
	        Iterator<Entry<String, String>> iter = parameters.getRequestParameters().entrySet().iterator();
			Entry<String, String> entry = null;
			while (iter.hasNext()) {
				entry = (Entry<String, String>) iter.next();	
				namevaluepair.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
	        //String enc_url = urlEncode("");
	        HttpPost httpPost = new HttpPost(url);
	  
	         httpPost.setEntity(new UrlEncodedFormEntity(namevaluepair));
	         httpResponse = httpClient.execute(httpPost);
	         //HttpEntity httpEntity = httpResponse.getEntity();
	        //is = httpEntity.getContent();            
	  
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	  
	    try {
//	        BufferedReader reader = new BufferedReader(new InputStreamReader(
//	                is, "iso-8859-1"), 8);
//	        StringBuilder sb = new StringBuilder();
//	        String line = null;
//	        while ((line = reader.readLine()) != null) {
//	            sb.append(line);
//	        }
//	        is.close();
//	        String responseFromServer = sb.toString();
			int code = httpResponse.getStatusLine().getStatusCode();
			
	    	byte[] bytes = EntityUtils.toByteArray(httpResponse.getEntity());

			//Log.i(TAG, "ResponseCode: " + code);
			//Log.i(TAG, "Responsebody: " + bytes);

			ret.code = code;
			ret.bytes = bytes;
	  
	    } catch (Exception e) {
	        //Logger.e("Buffer Error", "Error converting result " + e.toString());
	    }
	    return ret;
	}
	
//	public HttpReturn executeHttpPost333(Context context, String url, String post,
//			int requestFrom, boolean visiable, final AmsRequestParameters parameters)
//	{
//		HttpReturn ret = new HttpReturn();
//	    InputStream is = null;
//	    List<BasicNameValuePair> namevaluepair = new  ArrayList<BasicNameValuePair>();
//	  
//	    //Making HTTP request
//	    try {
//	        // defaultHttpClient
//	        DefaultHttpClient httpClient = new DefaultHttpClient();
//	  
//	        Iterator<Entry<String, String>> iter = parameters.getRequestParameters().entrySet().iterator();
//			Entry<String, String> entry = null;
//			while (iter.hasNext()) {
//				entry = (Entry<String, String>) iter.next();	
//				namevaluepair.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//			}
//	  
//	  
//	       
//	        HttpPost httpPost = new HttpPost(url);
//	  
//	         httpPost.setEntity(new UrlEncodedFormEntity(namevaluepair));
//	         HttpResponse httpResponse = httpClient.execute(httpPost);
//	         HttpEntity httpEntity = httpResponse.getEntity();
//	         ret.code = httpResponse.getStatusLine().getStatusCode();
//	        is = httpEntity.getContent();            
//	  
//	    } catch (UnsupportedEncodingException e) {
//	    	Logger.e("executeHttpPost", e.getMessage());
//	        e.printStackTrace();
//	    } catch (ClientProtocolException e) {
//	    	Logger.e("executeHttpPost", e.getMessage());
//	        e.printStackTrace();
//	    } catch (IOException e) {
//	    	Logger.e("executeHttpPost", e.getMessage());
//	        e.printStackTrace();
//	    }
//	  
//	    try {
//	        BufferedReader reader = new BufferedReader(new InputStreamReader(
//	                is, "iso-8859-1"), 8);
//	        StringBuilder sb = new StringBuilder();
//	        String line = null;
//	        while ((line = reader.readLine()) != null) {
//	            sb.append(line);
//	        }
//	        is.close();
//	        String responseFromServer = sb.toString();
//	        
//			
//			ret.bytes = responseFromServer.getBytes();
//	    } catch (Exception e) {
//	        Logger.e("Buffer Error", "Error converting result " + e.toString());
//	    }
//	  return ret;
//	}
	
	public HttpReturn executeHttpPost(Context context, String url, String post,
			int requestFrom, boolean visiable, final AmsRequestParameters parameters) {
		HttpReturn ret = new HttpReturn();
		HttpPost httppost = null;
//		long startTime = System.currentTimeMillis();
		try {
			//Log.i(TAG, url);
			//Log.i(TAG, post);
			//Log.i(TAG, "Request start time:" + startTime);
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, DEFAULTTIMEOUT);
			HttpConnectionParams.setSoTimeout(params, DEFAULTTIMEOUT);
			HttpClientParams.setRedirecting(params, true);

			DefaultHttpClient httpClient = new DefaultHttpClient();
			httppost = new HttpPost(url);
			httppost.setParams(params);
			httppost.addHeader("Content-Type",
					"application/x-www-form-urlencoded");
			
			if(parameters != null){
				Iterator<Entry<String, String>> iter = parameters.getRequestParameters().entrySet().iterator();
				Entry<String, String> entry = null;
				while (iter.hasNext()) {
					entry = (Entry<String, String>) iter.next();	
					httppost.addHeader(entry.getKey(), entry.getValue());
				}
			}
			
			//用户模式和注册模式之间的选择，暂时注释用户方面
			//去掉Header
			
//			if (requestFrom == 1) {
//				httppost.addHeader(
//						"Cookie",
//						"lpsust="
//								+ PsAuthenServiceL.getStData(context,
//										AmsRequest.RID, visiable)
//								+ ";clientid="
//								+ RegistClientInfoResponse.getClientId());
//			} 
//			else {
//				httppost.addHeader(
//						"Cookie",
//						"lpsust="
//								+ PsAuthenServiceL.getStData(context,
//										SnsRequest.RID, visiable)
//								+ ";clientid="
//								+ SnsRegistClientInfoResponse.getClientId());
//			}
			StringEntity reqEntity = new StringEntity(post, HTTP.UTF_8);
			httppost.setEntity(reqEntity);

			HttpResponse response = httpClient.execute(httppost);
			int code = response.getStatusLine().getStatusCode();
			byte[] bytes = EntityUtils.toByteArray(response.getEntity());
			httpClient.getConnectionManager().shutdown();

			//Log.i(TAG, "ResponseCode: " + code);
			//Log.i(TAG, "Responsebody: " + bytes);

			ret.code = code;
			ret.bytes = bytes;
		} catch (Exception e) {
			//MobclickAgent.reportError(context,"NetworkHttpRequest："+e.getMessage()) ;
			e.printStackTrace();
		}finally{
			if(httppost != null){
				httppost.abort();
			}
		}
//		long endTime = System.currentTimeMillis();
		//Log.i(TAG, "Request end time:" + endTime);
		//Log.i(TAG, "Request time consuming = " + (endTime - startTime));
		return ret;
	}
//
//	public HttpReturn executeFileHttpPost(Context context, String url, String fileName,
//			int requestFrom, boolean visiable, final AmsRequestParameters parameters) {
//		HttpReturn ret = new HttpReturn();
//		try {
//			DefaultHttpClient httpclient = new DefaultHttpClient();
//			// 设置通信协议版本
//			httpclient.getParams().setParameter(
//					CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//
////			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000); 
////			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
//			HttpPost httppost = new HttpPost(url);
//			File file = new File(fileName);
//
//			if (parameters != null) {
//				Iterator<Entry<String, String>> iter = parameters.getRequestParameters().entrySet().iterator();
//				Entry<String, String> entry = null;
//				while (iter.hasNext()) {
//					entry = (Entry<String, String>) iter.next();
//					// filePost.addParameter(entry.getKey(), entry.getValue());
//					httppost.addHeader(entry.getKey(), entry.getValue());
//				}
//			}
//			String cookie = PsPushUserData.getCurrentUtanCookie(context);
//			if (!TextUtils.isEmpty(cookie)) {
//				httppost.addHeader("cookie", cookie);
//			}
//			MultipartEntity mpEntity = new MultipartEntity(); // 文件传输
//			ContentBody cbFile = new FileBody(file);
//			mpEntity.addPart("Filedata", cbFile); // <input type="file"//
//													// name="userfile" /> 对应的
//			httppost.setEntity(mpEntity);
//
//			HttpResponse response = httpclient.execute(httppost);
//			// Header header=response.getFirstHeader("");
//			HttpEntity resEntity = response.getEntity();
//
//			ret.code = response.getStatusLine().getStatusCode();
//			// String string = new String(EntityUtils.toByteArray(resEntity));
//			ret.bytes = EntityUtils.toByteArray(resEntity);
//			
//
//			httpclient.getConnectionManager().shutdown();
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return ret;
//		//return executeFileHttpPost(context, url, new String[]{fileName}, requestFrom, visiable, parameters);
//	}
//	
//	public HttpReturn executeFileHttpPost(Context context, String url, String fileName[],
//			int requestFrom, boolean visiable, final AmsRequestParameters parameters) {
//		HttpReturn ret = new HttpReturn();
////		long startTime = System.currentTimeMillis();
//		try {
//			//Log.i(TAG, url);
//			//Log.i(TAG, "request start time:" + startTime);
//			HttpParams params = new BasicHttpParams();
//			HttpConnectionParams.setConnectionTimeout(params, DEFAULTTIMEOUT);
//			HttpConnectionParams.setSoTimeout(params, DEFAULTTIMEOUT);
//			HttpClientParams.setRedirecting(params, true);
//
//			//DefaultHttpClient httpClient = new DefaultHttpClient(params);
//			HttpClient clients = new HttpClient();
//			PostMethod filePost = new PostMethod(url);
//			
//			
//			//HttpPost httppost = new HttpPost(url);
//			if(parameters != null){
//				Iterator<Entry<String, String>> iter = parameters.getRequestParameters().entrySet().iterator();
//				Entry<String, String> entry = null;
//				while (iter.hasNext()) {
//					entry = (Entry<String, String>) iter.next();	
//					filePost.addParameter(entry.getKey(), entry.getValue());
//					//httppost.addHeader(entry.getKey(), entry.getValue());
//				}
//			}
//			
//			//httpClient.executeMethod(httppost);
//			
//			//用户模式改变，替换成注册模式……（一时屏蔽
//			//去掉Header
////			httppost.addHeader(
////					"Cookie",
////					"lpsust="
////							+ PsAuthenServiceL.getStData(context,
////									SnsRequest.RID, visiable) + ";clientid="
////							+ SnsRegistClientInfoResponse.getClientId());
////			httppost.addHeader(
////					"Cookie",
////					"lpsust="
////							+ PsAuthenServiceL.getStData(context,
////									AmsRequest.RID, visiable)
////							+ ";clientid="
////							+ RegistClientInfoResponse.getClientId());
//			//File file = new File(fileName);
//			//Part[] parts = { new FilePart("Filedata", new File(fileName)) };
//
//			Part[] parts = new Part[fileName.length];
//			for(int i = 0; i < fileName.length; i++){
//				parts[i] = new FilePart("Filedata", new File(fileName[i]));
//			}
//			
////			FileEntity reqEntity = new FileEntity(file, "Filedata");
////
////			httppost.setEntity(reqEntity);
//			//reqEntity.setContentType("binary/octet-stream");
//			
//			filePost.setRequestEntity(new MultipartRequestEntity(parts,
//					filePost.getParams()));
//			
//			//HttpResponse response = httpClient.execute(httppost);
//			clients.executeMethod(filePost);
//			//int code = response.getStatusLine().getStatusCode();
//			int code = clients.executeMethod(filePost);
//			//byte[] bytes = EntityUtils.toByteArray(filePost.getResponseBodyAsStream().);
//			StringBuffer stringBuffer;
//			
//			BufferedReader rd = new BufferedReader(new InputStreamReader( 
//		            filePost.getResponseBodyAsStream(), "UTF-8")); 
//		    stringBuffer=new StringBuffer(); 
//		    String line; 
//		    
//		    while ((line = rd.readLine()) != null) { 
//		        stringBuffer .append(line); 
//		    } 
//		    rd.close(); 
//			
//			//httpClient.getConnectionManager().shutdown();
//
//			// Log.i(TAG, "ResponseCode: " + code);
//			// Log.i(TAG, "Responsebody: " + bytes);
//
//			ret.code = code;
//			ret.bytes = stringBuffer.toString().getBytes();
//		} catch (Exception e) {
//			//MobclickAgent.reportError(context,"NetworkHttpRequest："+e.getMessage());
//			e.printStackTrace();
//		}
////		long endTime = System.currentTimeMillis();
//		//Log.i(TAG, "Request end time:" + endTime);
//		//Log.i(TAG, "Request time consuming = " + (endTime - startTime));
//		return ret;
//	}
//	public HttpReturn executeHttpPost(final Context context, String url,
//			final String postData, final String fileName,
//			final ILeHttpCallback response) {
//		// TODO Auto-generated method stub
//		NetworkHttpRequest request = new NetworkHttpRequest();
//
//		NetworkHttpRequest.HttpReturn ret = request.executeHttpPost(context,
//				url, fileName, false);
//		if (ret.code == 401) {
//			mVisiable = true;
//			registClientInfo(context, request, response);
//			ret = request.executeHttpPost(context, url, fileName, true);
//			if (ret.code == 401) {
//				response.onReturn(ret.code, ret.bytes);
//			}
//		}
//		response.onReturn(ret.code, ret.bytes);
//	}
	
}
