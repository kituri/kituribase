package com.kituri.app.ui.detailphotoview;

import java.util.HashMap;
import java.util.HashSet;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kituri.app.KituriApplication;
import com.kituri.app.data.Entry;
import com.kituri.app.data.ListEntry;
import com.kituri.app.utils.PhotoUtils;
import com.kituri.app.utils.PhotoUtils.Photoable;
import com.kituri.app.utils.Utility;
import com.kituri.app.widget.SelectionListener;
import com.kituri.demo.R;

public class ResImagePagerAdapter extends PagerAdapter {
	 
	private HashSet<ViewGroup> unRecycledViews = new HashSet<ViewGroup>();
	
    private LayoutInflater inflater;
    
    private ListEntry mDatas;
    
    private OnPhotoTapListener mOnPhotoTapListener;
    
    private SelectionListener<Entry> mSelectionListener;
    
    private HashMap<String, PicSimpleBitmapWorkerTask> taskMap
    = new HashMap<String, PicSimpleBitmapWorkerTask>();
    
    public static final String CURRENT_VISIBLE_PAGE = "currentPage";
    public static final int STATUS_BAR_HEIGHT_DP_UNIT = 25;
    public static final int NAVIGATION_BAR_HEIGHT_DP_UNIT = 48;


    public ResImagePagerAdapter(Activity activity, ListEntry datas,
    		OnPhotoTapListener onPhotoTapListener,
    		final SelectionListener<Entry> selectionListener) {
        inflater = activity.getLayoutInflater();
        mDatas = datas;
        mOnPhotoTapListener = onPhotoTapListener;
        mSelectionListener = selectionListener;
    }
    
//    public HashMap<String, PicSimpleBitmapWorkerTask> getTasks(){
//    	return taskMap;
//    }
    
    public HashSet<ViewGroup> getUnRecycledViews(){
    	return unRecycledViews;
    }
    
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof ViewGroup) {
            ((ViewPager) container).removeView((View) object);
            unRecycledViews.remove(object);
            ViewGroup viewGroup = (ViewGroup) object;
            Utility.recycleViewGroupAndChildViews(viewGroup, true);

        }
//        ((ViewPager) container).removeView((View) object);
    }


    @Override
    public int getCount() {
        return mDatas.getEntries().size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View contentView = inflater.inflate(R.layout.widget_galleryactivity, view, false);

        handlePage(position, contentView, true);

        ((ViewPager) view).addView(contentView, 0);
        unRecycledViews.add((ViewGroup) contentView);
        return contentView;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        View contentView = (View) object;
        if (contentView == null) {
            return;
        }

        contentView.setTag(CURRENT_VISIBLE_PAGE);

        //if (SettingUtility.allowClickToCloseGallery()) {
            contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	KituriApplication.getInstance().getActivity().finish();
                }
            });
        //}

        ImageView imageView = (ImageView) contentView.findViewById(R.id.iv_pic);

        if (imageView.getDrawable() != null) {
            return;
        }

        handlePage(position, contentView, false);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }




@SuppressLint("NewApi")
private void handlePage(final int position, View contentView, boolean fromInstantiateItem) {

    final PhotoView imageView = (PhotoView) contentView.findViewById(R.id.iv_pic);
    imageView.setVisibility(View.INVISIBLE);

//	 final RelativeLayout movieView = (RelativeLayout) contentView.findViewById(R.id.rl_play);
//	 final TextView movieIv = (TextView) contentView.findViewById(R.id.tv_play);
//	 final LinearLayout llCommenScan = (LinearLayout) contentView.findViewById(R.id.ll_commen_scan);
//	 final TextView mCommentNum = (TextView) contentView.findViewById(R.id.tv_comment_num);
//	 final TextView mScanNum = (TextView) contentView.findViewById(R.id.tv_scan_num);

	 final Entry data = mDatas.getEntries().get(position);
    
	 imageView.setTag(data);
	 imageView.setOnPhotoTapListener(mOnPhotoTapListener);
// 	llCommenScan.setVisibility(View.GONE);   	 
// 	movieView.setVisibility(View.GONE);   	
// 	movieIv.setVisibility(View.GONE);
	
    
    WebView gif = (WebView) contentView.findViewById(R.id.gif);
    gif.setBackgroundColor(contentView.getContext().getResources().getColor(android.R.color.transparent));
    gif.setVisibility(View.INVISIBLE);

    WebView large = (WebView) contentView.findViewById(R.id.large);
    large.setBackgroundColor(contentView.getContext().getResources().getColor(android.R.color.transparent));
    large.setVisibility(View.INVISIBLE);
    large.setOverScrollMode(View.OVER_SCROLL_NEVER);
    if (Utility.doThisDeviceOwnNavigationBar(contentView.getContext())) {
        imageView.setPadding(0, 0, 0,
                Utility.dip2px(NAVIGATION_BAR_HEIGHT_DP_UNIT));
        //webview has a bug, padding is ignored
        gif.setPadding(0, 0, 0,
                Utility.dip2px(NAVIGATION_BAR_HEIGHT_DP_UNIT));
        large.setPadding(0, 0, 0,
                Utility.dip2px(NAVIGATION_BAR_HEIGHT_DP_UNIT));
    }

    TextView wait = (TextView) contentView.findViewById(R.id.wait);

    TextView readError = (TextView) contentView.findViewById(R.id.error);

    Photoable photoable = (Photoable) mDatas.getEntries().get(position);

    //sometime picture is not downloaded completely, but android already can read it....
    wait.setVisibility(View.INVISIBLE);
    PhotoUtils.readResPicture(imageView, readError, photoable, photoable.getResId(), mSelectionListener);
    
//    if (ImageUtility.isThisBitmapCanRead(path)
//            && taskMap.get(photoable.getPhotoUrl()) == null
//            && TaskCache.isThisUrlTaskFinished(photoable.getPhotoUrl())) {
//        wait.setVisibility(View.INVISIBLE);
//        PhotoUtils.readPicture(imageView, gif, large, readError, photoable, path, mSelectionListener);
//
//    } else if (shouldDownLoadPicture) {
//
//        final CircleProgressView spinner = (CircleProgressView) contentView
//                .findViewById(R.id.loading);
//        spinner.setVisibility(View.VISIBLE);
//
//        if (taskMap.get(photoable.getPhotoUrl()) == null) {
//            wait.setVisibility(View.VISIBLE);
//            PicSimpleBitmapWorkerTask task = new PicSimpleBitmapWorkerTask(imageView, gif,
//                    large, spinner, wait, readError, photoable, taskMap, mSelectionListener);
//            taskMap.put(photoable.getPhotoUrl(), task);
//            task.executeOnExecutor(MyAsyncTask.THREAD_POOL_EXECUTOR);
//        } else {
//            PicSimpleBitmapWorkerTask task = taskMap.get(photoable.getPhotoUrl());
//            task.setWidget(imageView, gif, spinner, wait, readError);
//        }
//    }
}

}