package com.fancon.android.core;

import com.fancon.android.multithread.RequestQueue;

/**
 * GlobalState interface.
 * 
 * @author binhbt
 */
public interface IFanconGlobalState {

	/**
	 * Returns application wide instance of RequestQueue. Creates one once this
	 * method is called for the first time.
	 * 
	 * @return RequestQueue instance.
	 */
	public RequestQueue getRequestQueue();

}
