package com.kituri.app.data;

import java.util.HashMap;


public class CookieData extends Entry {

	private static final long serialVersionUID = 1L;
	
	private HashMap<String, String> cookies;
	
	private HashMap<String, String> domains;

	public HashMap<String, String> getCookies() {
		return cookies;
	}

	public void setCookies(HashMap<String, String> cookies) {
		this.cookies = cookies;
	}

	public HashMap<String, String> getDomains() {
		return domains;
	}

	public void setDomains(HashMap<String, String> domains) {
		this.domains = domains;
	}
	
}
