package com.fancon.android.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class RoundCornerImageView extends XImageview {


	public RoundCornerImageView(Context context) {
		super(context);
		type =2;
	}

	public RoundCornerImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		type =2;
	}

	public RoundCornerImageView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		type =2;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
}
