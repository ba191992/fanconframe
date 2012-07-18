package com.fancon.android.rpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;

import android.content.Context;
import android.util.Log;

public abstract class FanconApiService {

	protected String apiRoot;
	public static final int CONNECTION_TIME_OUT = 10000;
	protected Context mContext;

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
	public String getApiData(String api, Boolean isRenew) {
		String apiUrl = apiRoot + api;
		ApiCache apiCache = new ApiCache(mContext);
		apiCache.setIsRenew(isRenew);
		InputStream in = null;
		try {
			in = apiCache.getData(apiUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		try {
			return inputStreem2str(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String getApiFromCache(String api, String cacheDir, List<NameValuePair> nameValuePairs) {
		String apiUrl = apiRoot + api + getStrParam(nameValuePairs);
		ApiCache apiCache = new ApiCache(mContext, cacheDir);
		InputStream in = null;
		try {
			in = apiCache.getApiFromCache(apiUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		try {
			return inputStreem2str(in);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String getApiData(String api, String cacheDir,long cacheExpiration, List<NameValuePair> nameValuePairs, Boolean isRenew) {
		String apiUrl = apiRoot + api + getStrParam(nameValuePairs);
		ApiCache apiCache = new ApiCache(mContext, cacheDir, cacheExpiration);
		apiCache.setIsRenew(isRenew);
		InputStream in = null;
		try {
			in = apiCache.getData(apiUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		try {
			return inputStreem2str(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String getApiData(String api, String cacheDir, List<NameValuePair> nameValuePairs, Boolean isRenew) {
		String apiUrl = apiRoot + api + getStrParam(nameValuePairs);
		ApiCache apiCache = new ApiCache(mContext, cacheDir);
		apiCache.setIsRenew(isRenew);
		InputStream in = null;
		try {
			in = apiCache.getData(apiUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		try {
			return inputStreem2str(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String getApiDataByPost(String api, String cacheDir, List<NameValuePair> nameValuePairs, Boolean isRenew) {
		String apiUrl = apiRoot + api;
		
		ApiCache apiCache = new ApiCache(mContext, cacheDir);
		apiCache.setIsRenew(isRenew);
		InputStream in = null;
		try {
			in = apiCache.getDataByPost(apiUrl, nameValuePairs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		try {
			return inputStreem2str(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
		String ret = inputStreem2str(in);
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
	/**
	 * InputStream to String
	 * 
	 * @param is
	 *            InputStream
	 * @return String
	 * @throws IOException
	 */
	public static String inputStreem2str(InputStream is) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"UTF-8"));
		StringBuffer buf = new StringBuffer();
		String str;
		while (null != (str = reader.readLine())) {
			buf.append(str);
			buf.append("\n");
		}
		return buf.toString();
	}
	/**
	 * Build URL
	 * 
	 * @param params
	 * 
	 * @return params URL
	 */
	static public String getStrParam(List<NameValuePair> params) {
		String ret = "?";
		boolean flgAdd = false;

		for (int i = 0; params.size() > i; i++) {
			NameValuePair nvp = params.get(i);

			if (flgAdd) {
				ret += "&";
			}

			ret += nvp.getName() + "=" + nvp.getValue();

			flgAdd = true;

		}

		return ret;

	}
}
