/**
 * 
 */
package com.kituri.app.model;



import android.graphics.Bitmap;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.kituri.app.KituriApplication;
import com.kituri.app.model.asyncdrawable.TimeLineBitmapDownloader;
import com.kituri.app.utils.FileManager.FileLocationMethod;

/**
 * @author Kituri
 *
 */
public class ImageLoader {
	
	//private static ImageLoader mInstance;
	//private BitmapUtils bitmapUtils;
	//private BitmapDisplayConfig bigPicDisplayConfig;
	//private Context mContext;
	//private boolean mAutoLoadFromServer = true;
	//private HashMap<String, RequestListenable> mRequests = new HashMap<String, RequestListenable>();
	
//	public static synchronized ImageLoader getInstance() {
//		if (mInstance == null) {
//			//init(context);
//			mInstance = new ImageLoader();
//		}
//		return mInstance;
//	}
//	
	private ImageLoader(){
		
	}		

    public static MyAsyncTask display(ImageView imageView, String uri) { 	
		return TimeLineBitmapDownloader.getInstance().downContentPic(imageView, uri);

    }
    
    public static MyAsyncTask display(ImageView iv, String uri, final DownLoadBitmapListener listener){
    	return TimeLineBitmapDownloader.getInstance().displayImageView(iv, uri, FileLocationMethod.picture_large, false, false, listener);
    }
    		
    
    public static MyAsyncTask displayOriginalPic(ImageView iv, String uri){
    	return TimeLineBitmapDownloader.getInstance().downOriginalPic(iv, uri);
    }

    /**
     *函数说明：
     *width：超过该值时，会减少Bitmap质量
     *height：超过该值时，会减少Bitmap质量
     *url：略
     *listener：回调接口
     *
     **/
    public static MyAsyncTask downLoadBitmap(final int width, final int height, final String url, final DownLoadBitmapListener listener) { 	
    	return TimeLineBitmapDownloader.getInstance().downLoadBitmap(width, height, url, listener);
    }
    
    /**
     *函数说明：
     *url：图片地址
     *listener：回调接口
     *
     **/
    static public MyAsyncTask downLoadBitmap(final String url, final DownLoadBitmapListener listener) { 	
    	//getResources().getDisplayMetrics()
    	return downLoadBitmap(KituriApplication.getInstance().getDisplayMetrics().widthPixels, 
    			KituriApplication.getInstance().getDisplayMetrics().heightPixels, 
    			url, listener);
    }
    
    
	static public MyAsyncTask displayPhoto(ImageView view, String path){
		return TimeLineBitmapDownloader.getInstance().displayPhoto(view, path);
	}
	
    public static void displayResImageView(final ImageView view, final int resId){
    	TimeLineBitmapDownloader.getInstance().displayResImageView(view, resId);
    }
	
	public interface DownLoadBitmapListener{
		public void onDownLoadCompleted(String url, Bitmap bitmap);
		public void onDownLoadFailed(String url, Bitmap bitmap);
	}

	
//	public void DownloadImage(Context context, final String url,
//			final RequestListener listener) {
//		AppStateManager.getInstance(context).downloadApp(DataTypeTransformer.transformURL(url), listener);
//	}
//	
//	public void DownloadImages(Context context, final ArrayList<String> urls,
//			final RequestListener listener) {	
//		AppStateManager.getInstance(context).downloadApps(DataTypeTransformer.transformURL(urls), listener);
//	}
	
	public OnScrollListener getAutoLoadFromServer() {
		//mAutoLoadFromServer = auto;
		//return new PauseOnScrollListener(bitmapUtils, false, true);	
		return new ListOnScrollListener();
	}
	
	class ListOnScrollListener implements OnScrollListener{

		private OnScrollListener mOnScrollListener;
		
		public ListOnScrollListener(OnScrollListener onScrollListener){
			this.mOnScrollListener = onScrollListener;
		}
		
		public ListOnScrollListener(){
			
		}
		
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			//listViewScrollState = scrollState;
			switch (scrollState) {
			case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
//				if (!enableRefreshTime) {
//					enableRefreshTime = true;
//					getAdapter().notifyDataSetChanged();
//				}
				//onListViewScrollStop();
//				LongClickableLinkMovementMethod.getInstance().setLongClickable(
//						true);
				TimeLineBitmapDownloader.getInstance().setPauseDownloadWork(
						false);
				TimeLineBitmapDownloader.getInstance().setPauseReadWork(false);

				break;
			case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
//				enableRefreshTime = false;
//				LongClickableLinkMovementMethod.getInstance().setLongClickable(
//						false);
				TimeLineBitmapDownloader.getInstance().setPauseDownloadWork(
						true);
				TimeLineBitmapDownloader.getInstance().setPauseReadWork(true);
				//onListViewScrollStateFling();
				break;
			case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//				enableRefreshTime = true;
//				LongClickableLinkMovementMethod.getInstance().setLongClickable(
//						false);
				TimeLineBitmapDownloader.getInstance().setPauseDownloadWork(
						true);
				//onListViewScrollStateTouchScroll();
				break;
			}
			if(mOnScrollListener != null){
				mOnScrollListener.onScrollStateChanged(view, scrollState);
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			//onListViewScroll();
			if(mOnScrollListener != null){
				mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			}
		}
		
	}

	
	public void setOnScrollListener(ListView lv){
		lv.setOnScrollListener(new ListOnScrollListener());
	}

	public void setOnScrollListener(ListView lv, OnScrollListener onScrollListener){
		//lv.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, true, true, onScrollListener));
		lv.setOnScrollListener(new ListOnScrollListener(onScrollListener));
	}

}