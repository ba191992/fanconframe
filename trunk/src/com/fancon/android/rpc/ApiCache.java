package com.fancon.android.rpc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.util.Log;

import com.fancon.android.utils.FileUtil;

public class ApiCache {
	public static final String CACHE_DIR = "api";
	private static final int BUFFER_SIZE = 1024;
	public static final String LOGCAT_NAME = "ApiCache";
	private Context mContext;
	public static final int CONNECTION_TIME_OUT = 10000;
	private String cacheDir = CACHE_DIR;

	public ApiCache(final Context context, String cacheDir) {
		this.mContext = context;
		this.cacheDir = cacheDir;
	}

	public ApiCache(final Context context) {
		this.mContext = context;
	}

	/**
	 * Get data from cache. If it is not existed, connect to server to get data
	 * and save to cache
	 * 
	 * @param apiUrl
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public InputStream getData(String apiUrl) throws IOException,
			NoSuchAlgorithmException {
		String fileName = hashKey(apiUrl);
		InputStream in = checkAndGetFromCache(fileName);
		if (in != null) {
			Log.d("CACHE API HIT", in.toString());
			return in;
		} else {
			in = getFromServer(apiUrl, fileName);
			Log.d("CACHE API MISS AND SAVE", in.toString());
		}
		return in;
	}

	/**
	 * Get data from cache. If it is not existed, connect to server to get data
	 * and save to cache
	 * 
	 * @param apiUrl
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public InputStream getApiData(String apiUrl) throws IOException,
			NoSuchAlgorithmException {
		String fileName = hashKey(apiUrl);
		InputStream in = getFromServer(apiUrl, fileName);
		if (in != null) {
			Log.d("CACHE API HIT", in.toString());
			return in;
		} else {
			in = checkAndGetFromCache(fileName);
			Log.d("CACHE API MISS AND SAVE", in.toString());
		}
		return in;
	}

	/**
	 * Load data from server and save cache
	 * 
	 * @param apiUrl
	 * @param fileName
	 * @return
	 */
	private InputStream getFromServer(String apiUrl, String fileName) {
		InputStream in = null;
		InputStream inRet = null;
		try {
			if (getConnection(apiUrl) != null)
				in = getConnection(apiUrl).getInputStream();

			boolean mSavedOk = false;
			if (in != null) {
				mSavedOk = saveCacheData(fileName, in);
			}
			if (mSavedOk) {
				inRet = checkAndGetFromCache(fileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("loi o cai chet tiet nay`",
					"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		}
		return inRet;

	}

	/**
	 * Get connection to API server to return stream data
	 * 
	 * @param apiUrl
	 * @return HttpURLConnection
	 */
	protected HttpURLConnection getConnection(String apiUrl) {

		HttpURLConnection httpURLConnection = null;

		try {
			URL url = new URL(apiUrl);
			Log.i("BASE_SERVICE", url.toString());
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setReadTimeout(CONNECTION_TIME_OUT);
			httpURLConnection.setConnectTimeout(CONNECTION_TIME_OUT);
			httpURLConnection.connect();
			if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				return httpURLConnection;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * Check if cache exist-> get from cache else get from server + save to
	 * cache
	 * 
	 * @param filename
	 * @return
	 */
	private InputStream checkAndGetFromCache(String filename) {
		File cacheFile = FileUtil
				.getFileFromCache(mContext, filename, cacheDir);

		if (cacheFile != null && cacheFile.exists()) {
			try {
				final InputStream in = new FileInputStream(cacheFile);
				return in;
			} catch (Exception e) {
				Log.e(LOGCAT_NAME, "Error reading file: " + e.toString());
			}
		}
		return null;
	}

	/**
	 * Generate key for saving cache data
	 * 
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	private String hashKey(String key) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		String hash = FileUtil.MD5(key);
		return hash;
	}

	/**
	 * Save data stream from server to cache
	 * 
	 * @param filename
	 * @param inputStream
	 * @return
	 */
	public boolean saveCacheData(String filename, InputStream inputStream) {
		File f = FileUtil.addFileToCache(mContext, filename, cacheDir);
		OutputStream out;
		try {
			out = new FileOutputStream(f);
			byte buf[] = new byte[BUFFER_SIZE];
			int len;
			while ((len = inputStream.read(buf)) > 0)
				out.write(buf, 0, len);
			out.close();

			inputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Clear all api cache data
	 * 
	 * @return
	 */
	public static boolean clearApiCache(final Context context) {
		try {
			FileUtil.cleanCaches(context, CACHE_DIR);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Clear api cache in sub dir
	 * 
	 * @param context
	 * @param cacheDir
	 * @return
	 */
	public static boolean clearApiCache(final Context context, String cacheDir) {
		try {
			FileUtil.cleanCacheApi(context, cacheDir);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
