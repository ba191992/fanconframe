package com.fancon.android.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.fancon.android.cache.core.ImageLoader;
import com.fancon.android.core.IFanconCache;
import com.fancon.android.core.IFanconGlobalState;

/**
 * Base class for all activities within this application. This class provides
 * some common functions to be used application wide.
 * 
 * @author binhbt
 * 
 */
public class FanconBaseActivity extends Activity implements IFanconCache {

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * Creates an intent for given class. This method makes it easier to create
	 * such intents as you can't have 'this' pointer from e.g. embedded observer
	 * implementations.
	 * 
	 * @param cls
	 *            Class this intent is created for.
	 * @return New Intent instance.
	 */
	public Intent createIntent(Class<?> cls) {
		Intent i = new Intent(this, cls);
		return i;
	}

	/**
	 * This function returns global GlobalState instance.
	 * 
	 * @return Global GlobalState instance.
	 */
	public IFanconGlobalState getGlobalState() {
		// Our application is actually GlobalState so we have only have to use
		// getApplication() and cast it to GlobalState.
		return (IFanconGlobalState) getApplication();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		freeMemory();
		super.finish();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("destroy", "Activity destroyed");
		System.gc();
		Runtime.getRuntime().gc();
	}

	@Override
	public void onPause() {
		super.onPause();
		// getGlobalState().getRequestQueue().setPaused(this, true);
		freeMemory();
		Log.d("Pause", "Free memory cache");
		// mImgLoader.recycle();
	}

	@Override
	public void onResume() {
		super.onResume();
		// getGlobalState().getRequestQueue().setPaused(this, false);
		Log.d("Resume", "resume");
	}

	public Context getRoot() {
		if (this.getParent() != null) {
			return this.getParent();
		}
		return this;
	}

	protected void unbindDrawables(View view) {
		try {
			if (view.getBackground() != null) {
				view.getBackground().setCallback(null);
			}
			if (view instanceof ViewGroup) {
				for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
					unbindDrawables(((ViewGroup) view).getChildAt(i));
				}
				try {
					((ViewGroup) view).removeAllViews();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			 view = null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		try {
			freeMemory();
			Log.d("Low memory", "Free memory now!");
			System.gc();
			Runtime.getRuntime().gc();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public ImageLoader getImageLoader() {
		// TODO Auto-generated method stub
		return imageLoader;
	}

	protected void freeMemory() {
		try {
			Log.d("free memory", "free all rq, cch");
			getGlobalState().getRequestQueue().removeRequests();
			imageLoader.stop();
			imageLoader.clearMemoryCache();
			System.gc();
			Runtime.getRuntime().gc();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
