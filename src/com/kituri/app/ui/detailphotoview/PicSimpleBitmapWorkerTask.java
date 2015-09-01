package com.kituri.app.ui.detailphotoview;

import java.util.HashMap;

import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kituri.app.KituriApplication;
import com.kituri.app.data.Entry;
import com.kituri.app.model.KituriToast;
import com.kituri.app.model.MyAsyncTask;
import com.kituri.app.model.asyncdrawable.TaskCache;
import com.kituri.app.utils.FileDownloaderHttpHelper;
import com.kituri.app.utils.FileManager;
import com.kituri.app.utils.FileManager.FileLocationMethod;
import com.kituri.app.utils.ImageUtility;
import com.kituri.app.utils.PhotoUtils;
import com.kituri.app.utils.PhotoUtils.Photoable;
import com.kituri.app.widget.CircleProgressView;
import com.kituri.app.widget.SelectionListener;
import com.kituri.demo.R;


public class PicSimpleBitmapWorkerTask extends MyAsyncTask<String, Integer, String> {


	
    private FileDownloaderHttpHelper.DownloadListener downloadListener
            = new FileDownloaderHttpHelper.DownloadListener() {
        @Override
        public void pushProgress(int progress, int max) {
            publishProgress(progress, max);
        }


    };

    public void setWidget(ImageView iv, WebView gif, CircleProgressView spinner, TextView wait,
            TextView readError) {
        this.iv = iv;
        this.spinner = spinner;
        this.wait = wait;
        this.readError = readError;
        this.gif = gif;
    }

    private ImageView iv;

    private WebView gif;

    private WebView large;

    private TextView wait;

    private Photoable photoable;

    private CircleProgressView spinner;
    
    private SelectionListener<Entry> mListener;

    private TextView readError;

    private HashMap<String, PicSimpleBitmapWorkerTask> taskMap;

    public PicSimpleBitmapWorkerTask(ImageView iv, WebView gif, WebView large,
            CircleProgressView spinner, TextView wait,
            TextView readError, Photoable photoable,
            HashMap<String, PicSimpleBitmapWorkerTask> taskMap,
            SelectionListener<Entry> mListener) {
        this.iv = iv;
        this.photoable = photoable;
        this.spinner = spinner;
        this.readError = readError;
        this.taskMap = taskMap;
        this.gif = gif;
        this.large = large;
        this.wait = wait;
        this.readError.setVisibility(View.INVISIBLE);
        this.spinner.setVisibility(View.VISIBLE);
        this.mListener = mListener;
    }


    @Override
    protected String doInBackground(String... dd) {
        if (isCancelled()) {
            return null;
        }

        boolean downloaded = TaskCache.waitForMsgDetailPictureDownload(photoable.getPhotoUrl(), downloadListener);
        if (downloaded) {
            return FileManager.getFilePathFromUrl(photoable.getPhotoUrl(), FileLocationMethod.picture_large);
        } else {
            return null;
        }

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        this.wait.setVisibility(View.INVISIBLE);
        int progress = values[0];
        int max = values[1];
        spinner.setMax(max);
        spinner.setProgress(progress);
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
        taskMap.remove(photoable.getPhotoUrl());
        this.spinner.setVisibility(View.INVISIBLE);
        this.wait.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPostExecute(final String bitmapPath) {

        this.spinner.setVisibility(View.INVISIBLE);
        this.wait.setVisibility(View.INVISIBLE);

        if (isCancelled()) {
            return;
        }

        taskMap.remove(photoable.getPhotoUrl());

        if (TextUtils.isEmpty(bitmapPath) || iv == null) {

            readError.setVisibility(View.VISIBLE);
            readError.setText(KituriApplication.getInstance().getString(R.string.picture_cant_download_or_sd_cant_read));
            return;
        } else {
            readError.setVisibility(View.INVISIBLE);
        }

        if (!ImageUtility.isThisBitmapCanRead(bitmapPath)) {
        	KituriToast.toastShow(R.string.download_finished_but_cant_read_picture_file);
        }

        PhotoUtils.readPicture(iv, gif, large, readError, photoable, bitmapPath, mListener);


    }









}
