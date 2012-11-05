package com.fancon.android.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class CircleImageView extends XImageview {


	public CircleImageView(Context context) {
		super(context);
		type =1;
	}

	public CircleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		type =1;
	}

	public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		type =1;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
}