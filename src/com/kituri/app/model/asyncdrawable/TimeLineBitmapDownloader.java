package com.kituri.app.model.asyncdrawable;

import android.R;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.kituri.app.KituriApplication;
import com.kituri.app.model.ImageLoader.DownLoadBitmapListener;
import com.kituri.app.model.MyAsyncTask;
import com.kituri.app.utils.FileManager;
import com.kituri.app.utils.FileManager.FileLocationMethod;
import com.kituri.app.utils.ImageUtility;


/**
 * User: qii
 * Date: 12-12-12
 */
public class TimeLineBitmapDownloader {

    private int defaultPictureResId;

    private Handler handler;

    static volatile boolean pauseDownloadWork = false;
    static final Object pauseDownloadWorkLock = new Object();

    static volatile boolean pauseReadWork = false;
    static final Object pauseReadWorkLock = new Object();

    private static final Object lock = new Object();

    private static TimeLineBitmapDownloader instance;

    private TimeLineBitmapDownloader(Handler handler) {
        this.handler = handler;
        //this.defaultPictureResId = ThemeUtility.getResourceId(R.attr.listview_pic_bg);
        this.defaultPictureResId = R.drawable.dialog_frame;
    }

    public static TimeLineBitmapDownloader getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new TimeLineBitmapDownloader(new Handler(Looper.getMainLooper()));
            }
        }
        return instance;
    }

    public static void refreshThemePictureBackground() {
        synchronized (lock) {
            instance = new TimeLineBitmapDownloader(new Handler(Looper.getMainLooper()));
        }
    }

    /**
     * Pause any ongoing background work. This can be used as a temporary
     * measure to improve performance. For example background work could
     * be paused when a ListView or GridView is being scrolled using a
     * {@link android.widget.AbsListView.OnScrollListener} to keep
     * scrolling smooth.
     * <p/>
     * If work is paused, be sure setPauseDownloadWork(false) is called again
     * before your fragment or activity is destroyed (for example during
     * {@link android.app.Activity#onPause()}), or there is a risk the
     * background thread will never finish.
     */
    public void setPauseDownloadWork(boolean pauseWork) {
        synchronized (pauseDownloadWorkLock) {
            TimeLineBitmapDownloader.pauseDownloadWork = pauseWork;
            if (!TimeLineBitmapDownloader.pauseDownloadWork) {
                pauseDownloadWorkLock.notifyAll();
            }
        }
    }

    public void setPauseReadWork(boolean pauseWork) {
        synchronized (pauseReadWorkLock) {
            TimeLineBitmapDownloader.pauseReadWork = pauseWork;
            if (!TimeLineBitmapDownloader.pauseReadWork) {
                pauseReadWorkLock.notifyAll();
            }
        }
    }

    protected Bitmap getBitmapFromMemCache(String key) {
        if (TextUtils.isEmpty(key))
            return null;
        else
            return (Bitmap) KituriApplication.getInstance().getBitmapCache().get(key);
    }


//    public void downloadAvatar(ImageView view, UserBean user) {
//        downloadAvatar(view, user, false);
//    }
//
//
//    public void downloadAvatar(ImageView view, UserBean user, AbstractTimeLineFragment fragment) {
//        boolean isFling = fragment.isListViewFling();
//        downloadAvatar(view, user, isFling);
//    }
//
    public void downloadAvatar(ImageView view, String url, boolean isFling) {

//        if (user == null) {
//            view.setImageResource(defaultPictureResId);
//            return;
//        }

//        String url;
//        FileLocationMethod method;
//        if (SettingUtility.getEnableBigAvatar()) {
//            url = user.getAvatar_large();
//            method = FileLocationMethod.avatar_large;
//        } else {
//            url = user.getProfile_image_url();
//            method = FileLocationMethod.avatar_small;
//        }
    	FileLocationMethod method = FileLocationMethod.avatar_large;
        displayImageView(view, url, method, isFling, false);
    }

    public MyAsyncTask downContentPic(ImageView view, String url) {
//        String picUrl;
//
//        boolean isFling = ((AbstractTimeLineFragment) fragment).isListViewFling();
//
//        if (SettingUtility.getEnableBigPic()) {
//            picUrl = msg.getOriginal_pic();
//            displayImageView(view, picUrl, FileLocationMethod.picture_large, isFling, false);
//
//        } else {
//            picUrl = msg.getThumbnail_pic();
//            displayImageView(view, picUrl, FileLocationMethod.picture_thumbnail, isFling, false);
//
//        }
        return displayImageView(view, url, FileLocationMethod.picture_large, false, false);
    }

    /**
     * 获取原始图片（无压缩）
     */
    public MyAsyncTask downOriginalPic(ImageView view, String url) {
    	 return displayImageView(view, url, FileLocationMethod.picture_bmiddle, false, false);
    }
    
//    public void displayMultiPicture(IWeiciyuanDrawable view, String picUrl, FileLocationMethod method, AbstractTimeLineFragment fragment) {
//
//        boolean isFling = ((AbstractTimeLineFragment) fragment).isListViewFling();
//
//        display(view, picUrl, method, isFling, true);
//
//    }

//    public void displayMultiPicture(IWeiciyuanDrawable view, String picUrl, FileLocationMethod method) {
//
//        display(view, picUrl, method, false, true);
//
//    }


//    public void downContentPic(IWeiciyuanDrawable view, MessageBean msg, AbstractTimeLineFragment fragment) {
//        String picUrl;
//
//        boolean isFling = ((AbstractTimeLineFragment) fragment).isListViewFling();
//
//        if (SettingUtility.getEnableBigPic()) {
//            picUrl = msg.getOriginal_pic();
//            display(view, picUrl, FileLocationMethod.picture_large, isFling, false);
//
//        } else {
//            picUrl = msg.getThumbnail_pic();
//            display(view, picUrl, FileLocationMethod.picture_thumbnail, isFling, false);
//
//        }
//    }

    /**
     * when user open weibo detail, the activity will setResult to previous Activity,
     * timeline will refresh at the time user press back button to display the latest repost count
     * and comment count. But sometimes, weibo detail's pictures are very large that bitmap memory
     * cache has cleared those timeline bitmap to save memory, app have to read bitmap from sd card
     * again, then app play annoying animation , this method will check whether we should read again or not.
     */
    private boolean shouldReloadPicture(ImageView view, String urlKey) {
        if (urlKey.equals(view.getTag())
                && view.getDrawable() != null
                && view.getDrawable() instanceof BitmapDrawable
                && ((BitmapDrawable) view.getDrawable() != null
                && ((BitmapDrawable) view.getDrawable()).getBitmap() != null)) {
            //AppLogger.d("shouldReloadPicture=false");
            return false;
        } else {
            view.setTag(null);
           // AppLogger.d("shouldReloadPicture=true");
            return true;
        }
    }

    @SuppressLint("NewApi")
	public MyAsyncTask displayPhoto(final ImageView view, final String path) {
        view.clearAnimation();

        if (!shouldReloadPicture(view, path))
            return null;

        final Bitmap bitmap = getBitmapFromMemCache(path);
        if (bitmap != null) {
            view.setImageBitmap(bitmap);
            view.setTag(path);
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB){
                if (view.getAlpha() != 1.0f) {
                    view.setAlpha(1.0f);
                }
            }
            cancelPotentialDownload(path, view);
        } else {

            if (!cancelPotentialDownload(path, view)) {
                return null;
            }

            final ReadPathWorker newTask = new ReadPathWorker(view, path, FileLocationMethod.picture_thumbnail, true);
            PictureBitmapDrawable downloadedDrawable = new PictureBitmapDrawable(newTask);
            view.setImageDrawable(downloadedDrawable);

            //listview fast scroll performance
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (getBitmapDownloaderTask(view) == newTask) {
                        newTask.executeOnExecutor(MyAsyncTask.THREAD_POOL_EXECUTOR);
                    }
                    return;
                    
                }
            }, 400);
            return newTask;
               
        }
        return null;
    }
    
    public MyAsyncTask displayImageView(final ImageView view, final String urlKey,
			final FileLocationMethod method, boolean isFling, boolean isMultiPictures){
    	return displayImageView(view, urlKey, method, isFling, isMultiPictures, null);
    }
    
    @SuppressLint("NewApi")
	public MyAsyncTask displayImageView(final ImageView view, final String urlKey,
			final FileLocationMethod method, boolean isFling, boolean isMultiPictures,
			DownLoadBitmapListener listener) {
        view.clearAnimation();

        if (!shouldReloadPicture(view, urlKey))
            return null;

        final Bitmap bitmap = getBitmapFromMemCache(urlKey);
        if (bitmap != null) {
            view.setImageBitmap(bitmap);
            view.setTag(urlKey);
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB){
                if (view.getAlpha() != 1.0f) {
                    view.setAlpha(1.0f);
                }
            }
            cancelPotentialDownload(urlKey, view);
            if(listener != null){
            	listener.onDownLoadCompleted(urlKey, bitmap);
            }
        } else {

            if (isFling) {
                view.setImageResource(defaultPictureResId);
                return null;
            }

            if (!cancelPotentialDownload(urlKey, view)) {
                return null;
            }

            final ReadWorker newTask = new ReadWorker(view, urlKey, method, isMultiPictures, listener);
            PictureBitmapDrawable downloadedDrawable = new PictureBitmapDrawable(newTask);
            view.setImageDrawable(downloadedDrawable);

            //listview fast scroll performance
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (getBitmapDownloaderTask(view) == newTask) {
                        newTask.executeOnExecutor(MyAsyncTask.THREAD_POOL_EXECUTOR);
                    }
                    return;
                    
                }
            }, 400);
            return newTask;
               
        }
        return null;
    }

    @SuppressLint("NewApi")
	public void displayResImageView(final ImageView view, final int resId) {
    	// final FileLocationMethod method, boolean isFling, boolean isMultiPictures
    	//FileLocationMethod.picture_large
    	final FileLocationMethod method = FileLocationMethod.picture_large;
    	final Boolean isFling = false;
    	final Boolean isMultiPictures = false;
    	final String urlKey = String.valueOf(resId);
    	
        view.clearAnimation();

        if (!shouldReloadPicture(view, urlKey))
            return;

        final Bitmap bitmap = getBitmapFromMemCache(urlKey);
        if (bitmap != null) {
            view.setImageBitmap(bitmap);
            view.setTag(urlKey);
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB){
                if (view.getAlpha() != 1.0f) {
                    view.setAlpha(1.0f);
                }
            }
            cancelPotentialDownload(urlKey, view);
        } else {

            if (isFling) {
                view.setImageResource(defaultPictureResId);
                return;
            }

            if (!cancelPotentialDownload(urlKey, view)) {
                return;
            }

            final ReadResWorker newTask = new ReadResWorker(view, resId, method, isMultiPictures);
            PictureBitmapDrawable downloadedDrawable = new PictureBitmapDrawable(newTask);
            view.setImageDrawable(downloadedDrawable);

            //listview fast scroll performance
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (getBitmapDownloaderTask(view) == newTask) {
                        newTask.executeOnExecutor(MyAsyncTask.THREAD_POOL_EXECUTOR);
                    }
                    return;
                    
                }
            }, 400);


        }

    }
    
    @SuppressLint("NewApi")
	private void display(final IWeiciyuanDrawable view, final String urlKey, final FileLocationMethod method, boolean isFling, boolean isMultiPictures) {
        view.getImageView().clearAnimation();

        if (!shouldReloadPicture(view.getImageView(), urlKey))
            return;

        final Bitmap bitmap = getBitmapFromMemCache(urlKey);
        if (bitmap != null) {
            view.setImageBitmap(bitmap);
            view.getImageView().setTag(urlKey);
            if (view.getProgressBar() != null)
                view.getProgressBar().setVisibility(View.INVISIBLE);
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB){
                if (view.getImageView().getAlpha() != 1.0f) {
                    view.getImageView().setAlpha(1.0f);
                }
            }

            view.setGifFlag(ImageUtility.isThisPictureGif(urlKey));
            cancelPotentialDownload(urlKey, view.getImageView());
        } else {

            if (isFling) {
                view.getImageView().setImageResource(defaultPictureResId);
                if (view.getProgressBar() != null)
                    view.getProgressBar().setVisibility(View.INVISIBLE);
                view.setGifFlag(ImageUtility.isThisPictureGif(urlKey));
                return;
            }

            if (!cancelPotentialDownload(urlKey, view.getImageView())) {
                return;
            }

            final ReadWorker newTask = new ReadWorker(view, urlKey, method, isMultiPictures);
            PictureBitmapDrawable downloadedDrawable = new PictureBitmapDrawable(newTask);
            view.setImageDrawable(downloadedDrawable);

            //listview fast scroll performance
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (getBitmapDownloaderTask(view.getImageView()) == newTask) {
                        newTask.executeOnExecutor(MyAsyncTask.THREAD_POOL_EXECUTOR);
                    }
                    return;


                }
            }, 400);


        }

    }


    public void totalStopLoadPicture() {


    }


    private static boolean cancelPotentialDownload(String url, ImageView imageView) {
        IPictureWorker bitmapDownloaderTask = getBitmapDownloaderTask(imageView);

        if (bitmapDownloaderTask != null) {
            String bitmapUrl = bitmapDownloaderTask.getUrl();
            if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
                if (bitmapDownloaderTask instanceof MyAsyncTask)
                    ((MyAsyncTask) bitmapDownloaderTask).cancel(true);
            } else {
                return false;
            }
        }
        return true;
    }


    private static IPictureWorker getBitmapDownloaderTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof PictureBitmapDrawable) {
                PictureBitmapDrawable downloadedDrawable = (PictureBitmapDrawable) drawable;
                return downloadedDrawable.getBitmapDownloaderTask();
            }
        }
        return null;
    }

    public void display(final ImageView imageView, final int width, final int height, final String url, final FileLocationMethod method) {
        if (TextUtils.isEmpty(url))
            return;

        new MyAsyncTask<Void, Bitmap, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap bitmap = null;
                boolean downloaded = TaskCache.waitForPictureDownload(
                        url, null, FileManager.getFilePathFromUrl(url, method), method);
                if (downloaded)
                    bitmap = ImageUtility.readNormalPic(FileManager.getFilePathFromUrl(url, method), width, height);
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (bitmap != null)
                    imageView.setImageDrawable(new BitmapDrawable(KituriApplication.getInstance().getResources(), bitmap));
            }
        }.executeOnExecutor(MyAsyncTask.THREAD_POOL_EXECUTOR);
    }
    
    public MyAsyncTask downLoadBitmap(final int width, final int height, final String url, final DownLoadBitmapListener listener) {
        if (TextUtils.isEmpty(url))
            return null;
        final FileLocationMethod method = FileLocationMethod.picture_large;
        new MyAsyncTask<Void, Bitmap, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap bitmap = null;
                boolean downloaded = TaskCache.waitForPictureDownload(
                        url, null, FileManager.getFilePathFromUrl(url, method), method);
                if (downloaded)
                    bitmap = ImageUtility.readNormalPic(FileManager.getFilePathFromUrl(url, method), width, height);
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if(listener != null){
                    if (bitmap != null){
                    	listener.onDownLoadCompleted(url, bitmap);
                    }else{
                    	listener.onDownLoadFailed(url, bitmap);
                    }
                }
                    //imageView.setImageDrawable(new BitmapDrawable(KituriApplication.getInstance().getResources(), bitmap));
            }
        }.executeOnExecutor(MyAsyncTask.THREAD_POOL_EXECUTOR);
        return null;
    }
    
}