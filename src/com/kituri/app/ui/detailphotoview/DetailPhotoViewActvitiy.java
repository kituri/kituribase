package com.kituri.app.ui.detailphotoview;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.kituri.app.data.Entry;
import com.kituri.app.data.ListEntry;
import com.kituri.app.model.MyAsyncTask;
import com.kituri.app.ui.BaseFragmentActivity;
import com.kituri.app.utils.PhotoUtils.Photoable;
import com.kituri.app.utils.Utility;
import com.kituri.demo.R;

/**
 * @author kituri
 * 
 */

public class DetailPhotoViewActvitiy extends BaseFragmentActivity {

    private TextView position;
    
    private ViewPager pager;

    private ImageView animationView;

    private View currentViewPositionLayout;

    private Rect rect;
    
    private ListEntry mDatas;
    
    private ImagePagerAdapter mImagePagerAdapter;
    
    private int mPosition = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_photoview);

        animationView = (ImageView) findViewById(R.id.animation);
        currentViewPositionLayout = findViewById(R.id.position_layout);

        position = (TextView) findViewById(R.id.position);
        TextView sum = (TextView) findViewById(R.id.sum);

        rect = getIntent().getParcelableExtra(com.kituri.app.model.Intent.EXTRA_PHOTOS_RECT);

        mDatas = (ListEntry) getIntent().getExtras().getSerializable(com.kituri.app.model.Intent.EXTRA_DETAIL_PICS);
        if(mDatas == null || mDatas.getEntries().size() == 0){
        	finish();
        	return;
        }
        sum.setText(String.valueOf(mDatas.getEntries().size()));

        if(mDatas.getEntries().size() == 1){
        	currentViewPositionLayout.setVisibility(View.GONE);
        }
        
        pager = (ViewPager) findViewById(R.id.pager);
        mImagePagerAdapter = new ImagePagerAdapter(this, mDatas, new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {

                if (rect == null || view == null){
                	DetailPhotoViewActvitiy.this.finish();
                	return;
                }
                if(view instanceof ImageView){
                	ImageView imageView = (ImageView)view;
                	if(!(imageView.getDrawable() instanceof BitmapDrawable)){
                        DetailPhotoViewActvitiy.this.finish();
                        return;
                	}else{
                		 //animateClose((PhotoView)imageView);
                		onBackPressed();
                	}
                }

            }

        }, null);
        pager.setAdapter(mImagePagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                DetailPhotoViewActvitiy.this.position.setText(String.valueOf(position + 1));
            }
        });
        mPosition = getIntent().getIntExtra("position", 0);
        pager.setCurrentItem(mPosition);
        pager.setOffscreenPageLimit(1);
        pager.setPageTransformer(true, new ZoomOutPageTransformer());
        pager.setPadding(0, Utility.dip2px(ImagePagerAdapter.STATUS_BAR_HEIGHT_DP_UNIT), 0, 0);
    }

    @Override
    public void onBackPressed() {

        if (rect == null || mPosition != pager.getCurrentItem()) {
            super.onBackPressed();
            return;
        }

        View view = pager.findViewWithTag(ImagePagerAdapter.CURRENT_VISIBLE_PAGE);

        final PhotoView imageView = (PhotoView) view.findViewById(R.id.iv_pic);

        if (imageView == null
                || (!(imageView.getDrawable() instanceof BitmapDrawable))) {
            super.onBackPressed();
            return;
        }

        animateClose(imageView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Entry entry : mDatas.getEntries()) {
            MyAsyncTask task = mImagePagerAdapter.getTasks().get(((Photoable)entry).getPhotoUrl());
            if (task != null) {
                task.cancel(true);
            }
        }
        Utility.recycleViewGroupAndChildViews(pager, true);
        for (ViewGroup viewGroup : mImagePagerAdapter.getUnRecycledViews()) {
            Utility.recycleViewGroupAndChildViews(viewGroup, true);
        }

        System.gc();
    }


    @SuppressLint("NewApi")
	private void animateClose(PhotoView imageView) {
        currentViewPositionLayout.setVisibility(View.INVISIBLE);
        animationView.setImageDrawable(imageView.getDrawable());

        pager.setVisibility(View.INVISIBLE);

        final Rect startBounds = rect;
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        animationView.getGlobalVisibleRect(finalBounds, globalOffset);

        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB){
            animationView.setPivotX(0f);
            animationView.setPivotY(0f);
        }


        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1){
        	final float startScaleFinal = startScale;

            animationView.animate().setInterpolator(new DecelerateInterpolator()).x(startBounds.left)
                    .y(startBounds.top).scaleY(startScaleFinal).scaleX(startScaleFinal).setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            DetailPhotoViewActvitiy.this.finish();
                            overridePendingTransition(0, 0);
                        }
                    }).start();
        }
        
        
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

    }



}