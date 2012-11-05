package com.fancon.android.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.fancon.android.cache.core.ImageLoader;
import com.fancon.android.core.IFanconCache;
import com.fancon.android.core.IFanconGlobalState;
import com.fancon.android.multithread.RequestQueue;
import com.fancon.android.ui.widget.IProgress;

/**
 * Base fragment for screen belong to tab
 * 
 * @author binhbt 2012
 */
public class FanconBaseFragment extends Fragment implements IFanconCache, IFanconGlobalState, IProgress {
	/**
	 * This function returns global GlobalState instance.
	 * 
	 * @return Global GlobalState instance.
	 */
	public IFanconGlobalState getGlobalState() {
		// Our application is actually GlobalState so we have only have to use
		// getApplication() and cast it to GlobalState.
		return (IFanconGlobalState) getActivity().getApplication();
	}

	@Override
	public void showProgress(String title) {
		try {
			((IProgress) getActivity()).showProgress(title);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dismissProgress() {
		try {
			((IProgress) getActivity()).dismissProgress();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FragmentManager getFgManager() {
		return ((FragmentActivity) getActivity()).getSupportFragmentManager();
	}

	@Override
	public void showProgress(View v, String title) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RequestQueue getRequestQueue() {
		// TODO Auto-generated method stub
		return ((IFanconGlobalState) getActivity()).getRequestQueue();
	}

	@Override
	public ImageLoader getImageLoader() {
		// TODO Auto-generated method stub
		return ((IFanconCache) getActivity()).getImageLoader();
	}
}