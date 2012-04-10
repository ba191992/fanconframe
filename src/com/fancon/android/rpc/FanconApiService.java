package com.fancon.android.rpc;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.methods.HttpPost;

import android.content.Context;
import android.util.Log;

public abstract class FanconApiService {

	protected String apiRoot;
	public static final int CONNECTION_TIME_OUT = 10000;
	protected Context mContext;
	public static final String CACHE_DIR = "api";

	public FanconApiService(final Context context, String apiRoot) {
		this.mContext = context;
		this.apiRoot = apiRoot;
	}

	protected InputStream getApiData(String api) {
		String apiUrl = apiRoot + api;
		ApiCache apiCache = new ApiCache(mContext);
		InputStream in = null;
		try {
			in = apiCache.getData(apiUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return in;
	}

	/**
	 * Get data gave by api If exist in cache -> load from cache Else download
	 * from server and save tocache to reuse
	 * 
	 * @param api
	 * @param cacheDir
	 * @return
	 */
	protected InputStream getApiData(String api, String cacheDir) {
		String apiUrl = apiRoot + api;
		ApiCache apiCache = new ApiCache(mContext, cacheDir);
		InputStream in = null;
		try {
			in = apiCache.getData(apiUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return in;
	}

	protected String getApiCacheData(String api, String cacheDir)
			throws IOException {
		String apiUrl = apiRoot + api;
		ApiCache apiCache = new ApiCache(mContext, cacheDir);
		InputStream in = null;
		try {
			in = apiCache.getData(apiUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		String ret = ApiConnect.inputStreem2str(in);
		in.close();
		return ret;
	}

	/**
	 * Get connection from an api url
	 * 
	 * @param api
	 * @return
	 */
	protected HttpURLConnection getConnection(String api) {

		HttpURLConnection httpURLConnection = null;

		try {

			URL url = new URL(apiRoot + api);
			Log.i("BASE_SERVICE", url.toString());
			httpURLConnection = (HttpURLConnection) url.openConnection();

			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setReadTimeout(CONNECTION_TIME_OUT);
			httpURLConnection.setConnectTimeout(CONNECTION_TIME_OUT);
			httpURLConnection.connect();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return httpURLConnection;
	}
	protected HttpPost getPostConnection(String api) {

		HttpPost httppost = new HttpPost();
		try {

			httppost = new HttpPost(apiRoot + api);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return httppost;
	}
}
