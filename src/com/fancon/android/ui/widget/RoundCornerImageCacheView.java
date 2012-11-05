package com.fancon.android.ui.widget;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;

import com.fancon.android.R;
import com.fancon.android.cache.core.DisplayImageOptions;
import com.fancon.android.cache.core.ImageLoader;
import com.fancon.android.cache.core.ImageLoadingListener;
import com.fancon.android.cache.utils.StorageUtils;
import com.fancon.android.core.IFanconCache;

public class RoundCornerImageCacheView extends RelativeLayout {
	public static int X_SIZE = 1709084;
	public static Boolean isComPressMax = false;
	private Context mContext;
	private RoundCornerImageView mImage;
	private ImageLoader imgLoader;
	private View mBaseView;
	private Integer loadingImg;
	private Integer errorImg;
	private ImageLoadingListener imageLoadingListener = null;

	public RoundCornerImageCacheView(Context context) {
		super(context);
		this.mContext = context;
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mBaseView = layoutInflater.inflate(R.layout.round_corner_image_cache_view, this);
		mImage = (RoundCornerImageView) mBaseView.findViewById(R.id.image_id);
		imgLoader = ((IFanconCache) ((Activity) mContext).getApplication())
				.getImageLoader();
	}

	public RoundCornerImageCacheView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mBaseView = layoutInflater.inflate(R.layout.round_corner_image_cache_view, this);
		mImage = (RoundCornerImageView) mBaseView.findViewById(R.id.image_id);
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

	public void setmImage(RoundCornerImageView mImage) {
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
		// if (!hasLocal(url)) {
		DisplayImageOptions options;
		if (loadingImg != null) {
			options = new DisplayImageOptions.Builder()
					.showStubImage(loadingImg).cacheInMemory().cacheOnDisc()
					.build();
		} else {
			options = new DisplayImageOptions.Builder().cacheInMemory()
					.cacheOnDisc().build();
		}
		if (imageLoadingListener == null) {
			imageLoadingListener = new ImageLoadingListener() {
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
			};
		}
		imgLoader.displayImage(url, mImage, options, imageLoadingListener);
		// }
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

	/**
	 * Load image form url with scale param
	 * 
	 * @param url
	 * @param isScale
	 */
	public void loadImage(String url, ScaleType scaleType) {
		if (scaleType != null) {
			mImage.setScaleType(scaleType);
		}
		loadImage(url);
	}

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	public RoundCornerImageView getImage() {
		return mImage;
	}

	public void setImage(RoundCornerImageView image) {
		this.mImage = image;
	}

	public void setImageBitmap(Bitmap bm) {
		this.mImage.setImageBitmap(bm);
	}

	public ImageLoadingListener getImageLoadingListener() {
		return imageLoadingListener;
	}

	public void setImageLoadingListener(
			ImageLoadingListener imageLoadingListener) {
		this.imageLoadingListener = imageLoadingListener;
	}

	/**
	 * Check image has saved in cache or not
	 * 
	 * @param url
	 * @return
	 */
	private Boolean hasLocal(String url) {
		File cacheDir = StorageUtils.getIndividualCacheDirectory(getContext());
		File file = new File(cacheDir, String.valueOf(url.hashCode()));
		if (file.exists()) {
			try {
				Bitmap bitmap = BitmapFactory
						.decodeFile(file.getAbsolutePath());
				mImage.setImageBitmap(bitmap);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * load image from url and process scale or round corner
	 * 
	 * @param url
	 * @param isScale
	 * @param isRound
	 */
	public void loadImage(String url, Boolean isScale, final Boolean isRound) {
		if (isScale) {
			mImage.setScaleType(ScaleType.FIT_XY);
		} else {

		}
		// Full "displayImage" method using.
		// You can use simple call:
		// imageLoader.displayImage(imageUrls.get(position), holder.image);
		// instead of.
		DisplayImageOptions options;
		if (isRound) {
			options = new DisplayImageOptions.Builder().cacheInMemory()
					.cacheOnDisc().roundedCorner().build();

		} else {
			options = new DisplayImageOptions.Builder().cacheInMemory()
					.cacheOnDisc().build();
		}
		imgLoader.displayImage(url, mImage, options, imageLoadingListener);
	}
}
