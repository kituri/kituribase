package com.kituri.ams;

import java.io.ByteArrayInputStream;

import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.VolleyError;

public class AmsResult {
    public interface PRIORITY{
    	String DEFAULT = AmsRequest.PRIORITY.DEFAULT;
    	String IMAGE = AmsRequest.PRIORITY.IMAGE;
    	String FILE = AmsRequest.PRIORITY.FILE;
    	String FILES = AmsRequest.PRIORITY.FILES;
    }

	public AmsResult(String priority) {
		this.mPriority = priority;
	}

	private ByteArrayInputStream is;
	public ByteArrayInputStream getIs() {
		return is;
	}

	public void setIs(ByteArrayInputStream is) {
		this.is = is;
	}

	private byte[] mBytes;
	private JSONObject mBody;
	private String stringBody;
	private String mPriority;
	private VolleyError error;
	
	@Override
	public String toString(){
		if(mBody != null){
			return mBody.toString();
		}else if(mBytes != null){
			return new String(mBytes);
		}else{
			return "";
		}
	}

	public String getPriority() {
		return mPriority;
	}

	public void setPriority(String priority) {
		this.mPriority = priority;
	}

	public void setBytes(byte[] bytes) {
		this.mBytes = bytes;
	}

	public byte[] getBytes() {
		return mBytes;
	}

	public JSONObject getBody() {
		return mBody;
	}

	public void setBody(JSONObject body) {
		this.mBody = body;
	}

	public String getStringBody() {
		return stringBody;
	}

	public void setStringBody(String stringBody) {
		this.stringBody = stringBody;
	}
	
	public void setError(VolleyError error){
		this.error = error;
	}
	
	public VolleyError getError(){
		return error;
	}
	
}
