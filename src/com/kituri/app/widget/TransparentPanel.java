package com.kituri.app.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 透明层
 * 
 * @author Kituri
 * 
 */
public class TransparentPanel extends LinearLayout {

	private Paint innerPaint, borderPaint;
	private int borderWidth = 0;

	public TransparentPanel(Context context) {
		super(context);
		init();
		// TODO Auto-generated constructor stub
	}

	public TransparentPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		// TODO Auto-generated constructor stub
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public TransparentPanel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		// TODO Auto-generated constructor stub
	}

	private void init() {
		innerPaint = new Paint(); 
		innerPaint.setARGB(200, 116,116,116); // color for content
		innerPaint.setAntiAlias(true);

		borderPaint = new Paint();
		borderPaint.setARGB(200, 255, 255, 255);
		borderPaint.setAntiAlias(true);
		borderPaint.setStyle(Style.STROKE);
		borderPaint.setStrokeWidth(borderWidth);
	}

	public void setInnerPaint(Paint innerPaint) {
		this.innerPaint = innerPaint;
	}

	public void setBorderPaint(Paint borderPaint) {
		this.borderPaint = borderPaint;
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {

		RectF drawRect = new RectF();
		drawRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());

		canvas.drawRoundRect(drawRect, 5, 5, innerPaint);
		//canvas.drawRoundRect(drawRect, 5, 5, borderPaint);

		super.dispatchDraw(canvas);
	}

}
