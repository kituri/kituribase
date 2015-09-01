package com.kituri.net.http;


import java.util.Map;

import com.kituri.app.utils.FileDownloaderHttpHelper;
import com.kituri.app.utils.FileUploaderHttpHelper;
import com.kituri.net.http.error.KituriException;

public class HttpUtility {

    private static HttpUtility httpUtility = new HttpUtility();

    private HttpUtility() {
    }

    public static HttpUtility getInstance() {
        return httpUtility;
    }


    public String executeNormalTask(HttpMethod httpMethod, String url, Map<String, String> param) throws KituriException {
        return new JavaHttpUtility().executeNormalTask(httpMethod, url, param);
    }

    public boolean executeDownloadTask(String url, String path, FileDownloaderHttpHelper.DownloadListener downloadListener) {
        return !Thread.currentThread().isInterrupted() && new JavaHttpUtility().doGetSaveFile(url, path, downloadListener);
    }

    public boolean executeUploadTask(String url, Map<String, String> param, String path, String imageParamName, FileUploaderHttpHelper.ProgressListener listener) throws KituriException {
        return !Thread.currentThread().isInterrupted() && new JavaHttpUtility().doUploadFile(url, param, path, imageParamName, listener);
    }
}

