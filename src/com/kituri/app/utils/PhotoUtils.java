package com.kituri.app.utils;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kituri.app.KituriApplication;
import com.kituri.app.data.Entry;
import com.kituri.app.model.KituriToast;
import com.kituri.app.model.MyAsyncTask;
import com.kituri.app.push.PsPushUserData;
import com.kituri.app.widget.SelectionListener;
import com.kituri.demo.R;

public class PhotoUtils {

    private static final int IMAGEVIEW_SOFT_LAYER_MAX_WIDTH = 2000;

    private static final int IMAGEVIEW_SOFT_LAYER_MAX_HEIGHT = 3000;
	
	static public void readResPicture(final ImageView imageView, final TextView readError, final Photoable photoable, final int resId, final SelectionListener<Entry> mListener) {

	    //ImageView already have bitmap, ignore it
	    if (imageView.getDrawable() != null) {
	        return;
	    }

	    new MyAsyncTask<Void, Bitmap, Bitmap>() {

	        //todo
	        //when I finish new ImageView in the future, I will refactor these code....
	        @Override
	        protected Bitmap doInBackground(Void... params) {
	            Bitmap bitmap = null;
	            try {
	                bitmap = ImageUtility.getResCornerPic(photoable.getResId(),
	                				KituriApplication.getInstance().getDisplayMetrics().widthPixels,
	                				KituriApplication.getInstance().getDisplayMetrics().heightPixels);
	            } catch (OutOfMemoryError ignored) {
	                KituriApplication.getInstance().getBitmapCache().evictAll();
	                try {
		                bitmap = ImageUtility.getResCornerPic(photoable.getResId(),
                				KituriApplication.getInstance().getDisplayMetrics().widthPixels,
                				KituriApplication.getInstance().getDisplayMetrics().heightPixels);
	                } catch (OutOfMemoryError ignoredToo) {

	                }
	            }

	            return bitmap;
	        }

	        @Override
	        protected void onPostExecute(Bitmap bitmap) {
	            super.onPostExecute(bitmap);

	            if (imageView.getDrawable() != null) {
	                return;
	            }

	            if (bitmap != null) {
	                imageView.setVisibility(View.VISIBLE);
	                imageView.setImageBitmap(bitmap);
	                readError.setVisibility(View.INVISIBLE);
	            } else {
	                readError.setText(KituriApplication.getInstance().getString(R.string.picture_read_failed));
	                imageView.setVisibility(View.INVISIBLE);
	                readError.setVisibility(View.VISIBLE);
	            }
	        }
	    }.executeOnExecutor(MyAsyncTask.THREAD_POOL_EXECUTOR);
	}
    
	static public void readPicture(final ImageView imageView, WebView gif, WebView large,
	        final TextView readError, final Photoable photoable, final String bitmapPath, final SelectionListener<Entry> mListener) {

	    if (bitmapPath.endsWith(".gif")) {
	        readGif(gif, large, readError, photoable, bitmapPath, mListener);
	        return;
	    }

	    if (!ImageUtility.isThisBitmapCanRead(bitmapPath)) {
	    	KituriToast.toastShow(R.string.download_finished_but_cant_read_picture_file);
	    }

	    boolean isThisBitmapTooLarge = ImageUtility.isThisBitmapTooLargeToRead(bitmapPath);
	    Boolean alreadyShowPicturesTooLargeHint = PsPushUserData.getData(KituriApplication.getInstance(),
	    		PsPushUserData.KEY_ALREADY_SHOW_PIC_TOO_LARGE_HINT, false);
	    if (isThisBitmapTooLarge && !alreadyShowPicturesTooLargeHint) {
	    	KituriToast.toastShow(R.string.picture_is_too_large_so_enable_software_layer);
	        //alreadyShowPicturesTooLargeHint = true;
	    	PsPushUserData.setData(KituriApplication.getInstance(), PsPushUserData.KEY_ALREADY_SHOW_PIC_TOO_LARGE_HINT, true);
	    }

	    if (isThisBitmapTooLarge) {
	        readLarge(large, photoable, bitmapPath, mListener);
	        return;
	    }

	    //ImageView already have bitmap, ignore it
	    if (imageView.getDrawable() != null) {
	        return;
	    }

	    new MyAsyncTask<Void, Bitmap, Bitmap>() {

	        //todo
	        //when I finish new ImageView in the future, I will refactor these code....
	        @Override
	        protected Bitmap doInBackground(Void... params) {
	            Bitmap bitmap = null;
	            try {
	                bitmap = ImageUtility
	                        .decodeBitmapFromSDCard(bitmapPath, IMAGEVIEW_SOFT_LAYER_MAX_WIDTH,
	                                IMAGEVIEW_SOFT_LAYER_MAX_HEIGHT);
	            } catch (OutOfMemoryError ignored) {
	                KituriApplication.getInstance().getBitmapCache().evictAll();
	                try {
	                    bitmap = ImageUtility
	                            .decodeBitmapFromSDCard(bitmapPath, IMAGEVIEW_SOFT_LAYER_MAX_WIDTH,
	                                    IMAGEVIEW_SOFT_LAYER_MAX_HEIGHT);
	                } catch (OutOfMemoryError ignoredToo) {

	                }
	            }

	            return bitmap;
	        }

	        @Override
	        protected void onPostExecute(Bitmap bitmap) {
	            super.onPostExecute(bitmap);

	            if (imageView.getDrawable() != null) {
	                return;
	            }

	            if (bitmap != null) {
	                imageView.setVisibility(View.VISIBLE);
	                imageView.setImageBitmap(bitmap);
	                bindImageViewLongClickListener(imageView, photoable, bitmapPath);
	                readError.setVisibility(View.INVISIBLE);
	            } else {
	                readError.setText(KituriApplication.getInstance().getString(R.string.picture_read_failed));
	                imageView.setVisibility(View.INVISIBLE);
	                readError.setVisibility(View.VISIBLE);
	            }
	        }
	    }.executeOnExecutor(MyAsyncTask.THREAD_POOL_EXECUTOR);
	}

	@SuppressLint("NewApi")
	static public  void readGif(WebView webView, WebView large, TextView readError, final Photoable photoable,
	        String bitmapPath, final SelectionListener<Entry> mListener) {
	    readError.setVisibility(View.INVISIBLE);
	    BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(bitmapPath, options);

	    int picWidth = options.outWidth;
	    int picHeight = options.outHeight;
	    int availableWidth = Utility.getScreenWidth()
	            - KituriApplication.getInstance().getResources().getDimensionPixelOffset(R.dimen.normal_gif_webview_margin_left)
	            - KituriApplication.getInstance().getResources().getDimensionPixelOffset(R.dimen.normal_gif_webview_margin_right);
	    int availableHeight = SmileyPickerUtility.getAppHeight(KituriApplication.getInstance().getActivity());

	    int maxPossibleResizeHeight = availableWidth * availableHeight / picWidth;

	    if (picWidth >= availableWidth || picHeight >= availableHeight
	            || maxPossibleResizeHeight >= availableHeight) {
	        readLarge(large, photoable, bitmapPath, mListener);
	        return;
	    }

	    webView.setVisibility(View.VISIBLE);
	    bindImageViewLongClickListener(((View) webView.getParent()), photoable, bitmapPath);

	    if (webView.getTag() != null) {
	        return;
	    }

	    webView.getSettings().setJavaScriptEnabled(true);
	    webView.getSettings().setUseWideViewPort(true);
	    webView.getSettings().setLoadWithOverviewMode(true);
	    webView.getSettings().setBuiltInZoomControls(false);
	    if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB){
	    	webView.getSettings().setDisplayZoomControls(false);
	    }        
	    webView.getSettings().setSupportZoom(false);

	    webView.setVerticalScrollBarEnabled(false);
	    webView.setHorizontalScrollBarEnabled(false);

	    File file = new File(bitmapPath);
	    String str1 = "file://" + file.getAbsolutePath().replace("/mnt/sdcard/", "/sdcard/");
	    String str2 =
	            "<html>\n<head>\n     <style>\n          html,body{background:transparent;margin:0;padding:0;}          *{-webkit-tap-highlight-color:rgba(0, 0, 0, 0);}\n     </style>\n     <script type=\"text/javascript\">\n     var imgUrl = \""
	                    + str1 + "\";" + "     var objImage = new Image();\n"
	                    + "     var realWidth = 0;\n" + "     var realHeight = 0;\n" + "\n"
	                    + "     function onLoad() {\n"
	                    + "          objImage.onload = function() {\n"
	                    + "               realWidth = objImage.width;\n"
	                    + "               realHeight = objImage.height;\n" + "\n"
	                    + "               document.gagImg.src = imgUrl;\n"
	                    + "               onResize();\n" + "          }\n"
	                    + "          objImage.src = imgUrl;\n" + "     }\n" + "\n"
	                    + "     function onResize() {\n" + "          var scale = 1;\n"
	                    + "          var newWidth = document.gagImg.width;\n"
	                    + "          if (realWidth > newWidth) {\n"
	                    + "               scale = realWidth / newWidth;\n" + "          } else {\n"
	                    + "               scale = newWidth / realWidth;\n" + "          }\n" + "\n"
	                    + "          hiddenHeight = Math.ceil(30 * scale);\n"
	                    + "          document.getElementById('hiddenBar').style.height = hiddenHeight + \"px\";\n"
	                    + "          document.getElementById('hiddenBar').style.marginTop = -hiddenHeight + \"px\";\n"
	                    + "     }\n" + "     </script>\n" + "</head>\n"
	                    + "<body onload=\"onLoad()\" onresize=\"onResize()\" onclick=\"Android.toggleOverlayDisplay();\">\n"
	                    + "     <table style=\"width: 100%;height:100%;\">\n"
	                    + "          <tr style=\"width: 100%;\">\n"
	                    + "               <td valign=\"middle\" align=\"center\" style=\"width: 100%;\">\n"
	                    + "                    <div style=\"display:block\">\n"
	                    + "                         <img name=\"gagImg\" src=\"\" width=\"100%\" style=\"\" />\n"
	                    + "                    </div>\n"
	                    + "                    <div id=\"hiddenBar\" style=\"position:absolute; width: 100%; background: transparent;\"></div>\n"
	                    + "               </td>\n" + "          </tr>\n" + "     </table>\n"
	                    + "</body>\n" + "</html>";
	    webView.loadDataWithBaseURL("file:///android_asset/", str2, "text/html", "utf-8", null);

	    webView.setTag(new Object());
	}

	@SuppressLint("NewApi")
	static public  void readLarge(WebView large, final Photoable photoable, String bitmapPath, final SelectionListener<Entry> mListener) {
	    large.setVisibility(View.VISIBLE);
	    bindImageViewLongClickListener(large, photoable, bitmapPath);
	    //if (SettingUtility.allowClickToCloseGallery()) {
	    large.setOnTouchListener(getLargeOnTouchListener(mListener, photoable));

	        
	    //}

	    if (large.getTag() != null) {
	        return;
	    }

	    large.getSettings().setJavaScriptEnabled(true);
	    large.getSettings().setUseWideViewPort(true);
	    large.getSettings().setLoadWithOverviewMode(true);
	    large.getSettings().setBuiltInZoomControls(true);
	    if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB){
	    	large.getSettings().setDisplayZoomControls(false);
	    }      
	    

	    large.setVerticalScrollBarEnabled(false);
	    large.setHorizontalScrollBarEnabled(false);

	    File file = new File(bitmapPath);

	    String str1 = "file://" + file.getAbsolutePath().replace("/mnt/sdcard/", "/sdcard/");
	    String str2 =
	            "<html>\n<head>\n     <style>\n          html,body{background:transparent;margin:0;padding:0;}          *{-webkit-tap-highlight-color:rgba(0, 0, 0, 0);}\n     </style>\n     <script type=\"text/javascript\">\n     var imgUrl = \""
	                    + str1 + "\";" + "     var objImage = new Image();\n"
	                    + "     var realWidth = 0;\n" + "     var realHeight = 0;\n" + "\n"
	                    + "     function onLoad() {\n"
	                    + "          objImage.onload = function() {\n"
	                    + "               realWidth = objImage.width;\n"
	                    + "               realHeight = objImage.height;\n" + "\n"
	                    + "               document.gagImg.src = imgUrl;\n"
	                    + "               onResize();\n" + "          }\n"
	                    + "          objImage.src = imgUrl;\n" + "     }\n" + "\n"
	                    + "     function onResize() {\n" + "          var scale = 1;\n"
	                    + "          var newWidth = document.gagImg.width;\n"
	                    + "          if (realWidth > newWidth) {\n"
	                    + "               scale = realWidth / newWidth;\n" + "          } else {\n"
	                    + "               scale = newWidth / realWidth;\n" + "          }\n" + "\n"
	                    + "          hiddenHeight = Math.ceil(30 * scale);\n"
	                    + "          document.getElementById('hiddenBar').style.height = hiddenHeight + \"px\";\n"
	                    + "          document.getElementById('hiddenBar').style.marginTop = -hiddenHeight + \"px\";\n"
	                    + "     }\n" + "     </script>\n" + "</head>\n"
	                    + "<body onload=\"onLoad()\" onresize=\"onResize()\" onclick=\"Android.toggleOverlayDisplay();\">\n"
	                    + "     <table style=\"width: 100%;height:100%;\">\n"
	                    + "          <tr style=\"width: 100%;\">\n"
	                    + "               <td valign=\"middle\" align=\"center\" style=\"width: 100%;\">\n"
	                    + "                    <div style=\"display:block\">\n"
	                    + "                         <img name=\"gagImg\" src=\"\" width=\"100%\" style=\"\" />\n"
	                    + "                    </div>\n"
	                    + "                    <div id=\"hiddenBar\" style=\"position:absolute; width: 100%; background: transparent;\"></div>\n"
	                    + "               </td>\n" + "          </tr>\n" + "     </table>\n"
	                    + "</body>\n" + "</html>";
	    large.loadDataWithBaseURL("file:///android_asset/", str2, "text/html", "utf-8", null);
	    large.setVisibility(View.VISIBLE);

	    large.setTag(new Object());
	}
	
	private static void bindImageViewLongClickListener(View view, final Photoable photoable,
	        final String filePath) {

	    view.setOnLongClickListener(new View.OnLongClickListener() {
	        @Override
	        public boolean onLongClick(View v) {

	            String[] values = {KituriApplication.getInstance().getString(R.string.copy_link_to_clipboard),
	                    KituriApplication.getInstance().getString(R.string.share), KituriApplication.getInstance().getString(R.string.save_pic_album)};

	            new AlertDialog.Builder(KituriApplication.getInstance().getActivity())
	                    .setItems(values, new DialogInterface.OnClickListener() {
	                        @SuppressLint("NewApi")
							@Override
	                        public void onClick(DialogInterface dialog, int which) {
	                            switch (which) {
	                                case 0:
	                                    ClipboardManager cm = (ClipboardManager) KituriApplication.getInstance().getSystemService(
	                                            Context.CLIPBOARD_SERVICE);
	                                    if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB){
	                                    	 cm.setPrimaryClip(ClipData.newPlainText("sinaweibo", photoable.getPhotoUrl()));
	                                    }
	                                    KituriToast.toastShow(R.string.copy_successfully);
	                                    break;
	                                case 1:
	                                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
	                                    sharingIntent.setType("image/jpeg");
	                                    if (!TextUtils.isEmpty(filePath)) {
	                                        Uri uri = Uri.fromFile(new File(filePath));
	                                        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
	                                        if (Utility.isIntentSafe(KituriApplication.getInstance().getActivity(),
	                                                sharingIntent)) {
	                                        	KituriApplication.getInstance().getActivity().startActivity(Intent.createChooser(sharingIntent,
	                                                    KituriApplication.getInstance().getString(R.string.share)));
	                                        }
	                                    }
	                                    break;
	                                case 2:
	                                    //saveBitmapToPictureDir(filePath);
	                                    break;
	                            }
	                        }
	                    }).show();

	            return true;
	        }
	    });
	}

	static public OnTouchListener getLargeOnTouchListener(final SelectionListener<Entry> mListener, final Photoable photoable){
		return new PhotoOnTouchListener(mListener, photoable);
	}
	
	public interface Photoable{
		public String getPhotoUrl();
		public int getResId();
		public String getLocalPath();
	}
	
	static public class PhotoOnTouchListener implements OnTouchListener {
		
		private SelectionListener<Entry> mListener;
		private Photoable mPhotoable;
		
		public PhotoOnTouchListener(final SelectionListener<Entry> mListener, final Photoable photoable){
			this.mListener = mListener;
			this.mPhotoable = photoable;
		}
		
	    boolean mPressed;

	    boolean mClose;

	    CheckForSinglePress mPendingCheckForSinglePress;

	    long lastTime = 0;

	    float[] location = new float[2];

	    class CheckForSinglePress implements Runnable {

	        View view;

	        public CheckForSinglePress(View view) {
	            this.view = view;
	        }

	        public void run() {
	            if (!mPressed && mClose) {
	                Utility.playClickSound(view);
	                //KituriApplication.getInstance().getActivity().finish();
	                if(mListener != null){
	                	if(mPhotoable instanceof Entry){
	                		mListener.onSelectionChanged((Entry) mPhotoable, true);
	                	}else{
	                		mListener.onSelectionChanged(null, true);
	                	}                	
	                }
	            }
	        }

	    }

	    @Override
	    public boolean onTouch(View v, MotionEvent event) {
	        switch (event.getActionMasked()) {
	            case MotionEvent.ACTION_DOWN:
	                mPendingCheckForSinglePress = new CheckForSinglePress(v);
	                mPressed = true;
	                if (System.currentTimeMillis() - lastTime
	                        > ViewConfiguration.getDoubleTapTimeout() + 100) {
	                    mClose = true;
	                    new Handler().postDelayed(mPendingCheckForSinglePress,
	                            ViewConfiguration.getDoubleTapTimeout() + 100);
	                } else {
	                    mClose = false;
	                }
	                lastTime = System.currentTimeMillis();

	                location[0] = event.getRawX();
	                location[1] = event.getRawY();

	                break;
	            case MotionEvent.ACTION_UP:
	                mPressed = false;
	                break;
	            case MotionEvent.ACTION_CANCEL:
	                mClose = false;

	                break;
	            case MotionEvent.ACTION_MOVE:
	                float x = event.getRawX();
	                float y = event.getRawY();
	                if (Math.abs(location[0] - x) > 5.0f && Math.abs(location[1] - y) > 5.0f) {
	                    mClose = false;
	                }
	                break;
	        }

	        return false;
	    }
	};
	
//	static public OnTouchListener largeOnTouchListener = 

//    static private void saveBitmapToPictureDir(String filePath) {
//        if (Utility.isTaskStopped(PicSaveTask.getInstance())) {
//        	PicSaveTask.getInstance().setPath(filePath);
//        	PicSaveTask.getInstance().executeOnExecutor(MyAsyncTask.THREAD_POOL_EXECUTOR);
//        }
//
//    }
	
}
