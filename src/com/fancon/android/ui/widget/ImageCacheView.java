package com.fancon.android.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.fancon.android.R;
import com.fancon.android.cache.core.DisplayImageOptions;
import com.fancon.android.cache.core.ImageLoader;
import com.fancon.android.cache.core.ImageLoadingListener;
import com.fancon.android.core.IFanconCache;
/**
 * Cache Image can store image to disk and load image from network
 * @author binhbt
 * 2012
 */
public class ImageCacheView extends RelativeLayout {

	private Context mContext;
	private ImageView mImage;
	private ImageLoader imgLoader;
	private View mBaseView;

	public ImageCacheView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mBaseView = layoutInflater.inflate(R.layout.image_cache_view,
				this);
		mImage = (ImageView) mBaseView.findViewById(R.id.image_id);
		imgLoader = ((IFanconCache) mContext).getImageLoader();
	}

	public void setBackgroundResource(Integer resId) {
		if (resId != null) {
			mImage.setBackgroundResource(resId);
		}
	}

	/**
	 * Set Image resource for Image cache
	 * 
	 * @param resId
	 */
	public void setImageResource(Integer resId) {
		if (resId != null) {
			mImage.setImageResource(resId);
		}
	}

	/**
	 * Set Image Darwable for Image cache
	 * 
	 * @param img
	 */
	public void setDrawableImage(Drawable img) {
		if (img != null) {
			mImage.setImageDrawable(img);
		}
	}

	/**
	 * Load image from cache and network
	 * 
	 * @param url
	 */
	public void loadImage(String url) {
		// Full "displayImage" method using.
		// You can use simple call:
		// imageLoader.displayImage(imageUrls.get(position), holder.image);
		// instead of.
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		// .showStubImage(R.drawable.stub_image).cacheInMemory()
				.cacheInMemory().cacheOnDisc().build();
		imgLoader.displayImage(url, mImage, options,
				new ImageLoadingListener() {
					@Override
					public void onLoadingStarted() {
					}

					@Override
					public void onLoadingFailed() {
						// mImage.setImageResource(android.R.drawable.ic_delete);
					}

					@Override
					public void onLoadingComplete() {
					}
				});
	}
	/**
	 * Load image form url with scale param
	 * @param url
	 * @param isScale
	 */
	public void loadImage(String url, Boolean isScale) {
		if (isScale) {
			mImage.setScaleType(ScaleType.FIT_XY);
		} else {

		}
		// Full "displayImage" method using.
		// You can use simple call:
		// imageLoader.displayImage(imageUrls.get(position), holder.image);
		// instead of.
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		// .showStubImage(R.drawable.stub_image).cacheInMemory()
				.cacheInMemory().cacheOnDisc().build();
		imgLoader.displayImage(url, mImage, options,
				new ImageLoadingListener() {
					@Override
					public void onLoadingStarted() {
					}

					@Override
					public void onLoadingFailed() {
						// mImage.setImageResource(android.R.drawable.ic_delete);
					}

					@Override
					public void onLoadingComplete() {
					}
				});
	}
	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	public ImageView getImage() {
		return mImage;
	}

	public void setImage(ImageView image) {
		this.mImage = image;
	}

}
