/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.volley.toolbox;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import android.graphics.BitmapFactory;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

/**
 * A canned request for retrieving the response body at a given URL as a String.
 */
public class InputStreamRequest extends Request<ByteArrayInputStream> {
    private final Listener<ByteArrayInputStream> mListener;

    /**
     * Creates a new request with the given method.
     *
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public InputStreamRequest(int method, String url, Listener<ByteArrayInputStream> listener,
            ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    /**
     * Creates a new GET request.
     *
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public InputStreamRequest(String url, Listener<ByteArrayInputStream> listener, ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    @Override
    protected void deliverResponse(ByteArrayInputStream response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<ByteArrayInputStream> parseNetworkResponse(NetworkResponse response) {
//    	StringBuffer sb = new StringBuffer();
//        String parsed = null;
    	ByteArrayInputStream tInputStringStream = null;
        tInputStringStream = new ByteArrayInputStream(response.data);
//            int len = 0;
//            byte[] buf = new byte[1024];
//            while((len = tInputStringStream.read(buf)) != -1) {
//            	sb.append(new String(buf, 0, len, "utf-8"));
//            }
//            tInputStringStream.close();
//            parsed = new String(sb.toString().getBytes());
        return Response.success(tInputStringStream, HttpHeaderParser.parseCacheHeaders(response));
    }
}
