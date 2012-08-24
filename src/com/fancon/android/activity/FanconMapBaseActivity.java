package com.fancon.android.activity;

/**
 * MapBase Activity for meshtiles
 * @author binhbt
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fancon.android.R;
import com.fancon.android.cache.core.ImageLoader;
import com.fancon.android.core.IFanconCache;
import com.fancon.android.core.IFanconGlobalState;
import com.fancon.android.multithread.RequestQueue;
import com.fancon.android.ui.widget.IProgress;
import com.google.android.maps.MapActivity;

public class FanconMapBaseActivity extends MapActivity implements IFanconCache, IFanconGlobalState, IProgress {

	protected ImageLoader imageLoader = ImageLoader.getInstance();
	/* Contain activity you have pass */
	protected PopupWindow popupWindow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
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
		dismissProgress();
		//freeMemory();
		Log.d("Pause", "Free memory cache");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d("Resume", "resume");
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
			imageLoader.clearMemoryCache();
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

	@Override
	public RequestQueue getRequestQueue() {
		// TODO Auto-generated method stub
		return ((IFanconGlobalState) getApplication()).getRequestQueue();
	}

	@Override
	public void showProgress(String title) {
		try {
			if (popupWindow == null) {
				LayoutInflater layoutInflater = (LayoutInflater) this
						.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				View popupView = layoutInflater.inflate(
						R.layout.common_mesh_progress_bar, null);
				TextView message = (TextView) popupView
						.findViewById(R.id.progress_text);
				message.setText(title);
				popupWindow = new PopupWindow(popupView,
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			}
			final View v = findViewById(android.R.id.content);
			v.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	@Override
	public void dismissProgress() {
		try {
			if (popupWindow != null && popupWindow.isShowing()) {
				popupWindow.dismiss();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	protected void onStop() {
		imageLoader.stop();
		super.onStop();
	}
	@Override
	public void showProgress(final View v, String title) {
		// TODO Auto-generated method stub
		try {
			if (popupWindow == null) {
				LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				View popupView = layoutInflater.inflate(
						R.layout.common_mesh_progress_bar, null);
				TextView message = (TextView) popupView
						.findViewById(R.id.progress_text);
				message.setText(title);
				popupWindow = new PopupWindow(popupView,
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			}
			v.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
}
