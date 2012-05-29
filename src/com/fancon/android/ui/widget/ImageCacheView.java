package com.fancon.android.ui.widget;

import android.app.Activity;
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
 * 
 * @author binhbt 2012
 */
public class ImageCacheView extends RelativeLayout {

	private Context mContext;
	private ImageView mImage;
	private ImageLoader imgLoader;
	private View mBaseView;
	private Integer loadingImg;
	private Integer errorImg;

	public ImageCacheView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mBaseView = layoutInflater.inflate(R.layout.image_cache_view, this);
		mImage = (ImageView) mBaseView.findViewById(R.id.image_id);
		imgLoader = ((IFanconCache) ((Activity) mContext).getApplication())
				.getImageLoader();
	}

	public ImageCacheView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mBaseView = layoutInflater.inflate(R.layout.image_cache_view, this);
		mImage = (ImageView) mBaseView.findViewById(R.id.image_id);
		imgLoader = ((IFanconCache) ((Activity) mContext).getApplication())
				.getImageLoader();
	}

	public void setBackgroundResource(Integer resId) {
		if (resId != null) {
			mImage.setBackgroundResource(resId);
		}
	}

	public ImageView getmImage() {
		return mImage;
	}

	public void setmImage(ImageView mImage) {
		this.mImage = mImage;
	}

	public ImageLoader getImgLoader() {
		return imgLoader;
	}

	public void setImgLoader(ImageLoader imgLoader) {
		this.imgLoader = imgLoader;
	}

	public View getmBaseView() {
		return mBaseView;
	}

	public void setmBaseView(View mBaseView) {
		this.mBaseView = mBaseView;
	}

	public Integer getLoadingImg() {
		return loadingImg;
	}

	public void setLoadingImg(Integer loadingImg) {
		this.loadingImg = loadingImg;
	}

	public Integer getErrorImg() {
		return errorImg;
	}

	public void setErrorImg(Integer errorImg) {
		this.errorImg = errorImg;
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
		DisplayImageOptions options;
		if (loadingImg != null) {
			options = new DisplayImageOptions.Builder()
					.showStubImage(loadingImg).cacheInMemory().cacheOnDisc()
					.build();
		} else {
			options = new DisplayImageOptions.Builder().cacheInMemory()
					.cacheOnDisc().build();
		}
		imgLoader.displayImage(url, mImage, options,
				new ImageLoadingListener() {
					@Override
					public void onLoadingStarted() {
					}

					@Override
					public void onLoadingFailed() {
						if (errorImg != null) {
							mImage.setImageResource(errorImg);
						}
					}

					@Override
					public void onLoadingComplete() {
					}
				});
	}

	/**
	 * Load image form url with scale param
	 * 
	 * @param url
	 * @param isScale
	 */
	public void loadImage(String url, Boolean isScale) {
		if (isScale) {
			mImage.setScaleType(ScaleType.FIT_XY);
		} else {

		}
		loadImage(url);
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
