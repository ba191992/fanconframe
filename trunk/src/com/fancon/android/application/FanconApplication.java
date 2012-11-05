package com.fancon.android.application;

import android.app.Application;

import com.fancon.android.cache.core.ImageLoader;
import com.fancon.android.cache.core.ImageLoaderConfiguration;
import com.fancon.android.core.IFanconCache;
import com.fancon.android.core.IFanconGlobalState;
import com.fancon.android.multithread.RequestQueue;

/**
 * FanconApplycation class extends Application and is used as base class for
 * our application. It is used for storing application wide data among
 * Activities.
 * 
 * @author binhbt
 * 
 */
public class FanconApplication extends Application implements
		IFanconGlobalState , IFanconCache{

	// RequestQueue instance.
	private RequestQueue mRequestQueue = null;
	protected ImageLoader imageLoader = null;
	@Override
	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = new RequestQueue();
		}
		return mRequestQueue;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).threadPoolSize(3)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCacheSize(1500000) // 1.5 Mb
				.discCacheSize(50000000) // 50 Mb
				.httpReadTimeout(10000) // 10 s
				.denyCacheImageMultipleSizesInMemory().build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
		//ImageLoader.getInstance().enableLogging(); // Not necessary in common
	}

	@Override
	public ImageLoader getImageLoader() {
		// TODO Auto-generated method stub
		if(imageLoader == null){
			imageLoader = ImageLoader.getInstance();
		}
		return imageLoader;
	}

}
