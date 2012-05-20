package com.fancon.android.rpc;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Api Connect class
 * 
 * @author DuyTX
 * 
 */
public class ApiConnect {
	private Context mContext;

	public ApiConnect(Context context) {
		this.mContext = context;
	}

	/**
	 * Get method
	 * 
	 * @param group
	 *            : Server API Group đang sử dụng (A,F,L...)
	 * 
	 * @param method
	 *            : Method để get dữ liệu v�?
	 * 
	 * @param paramList
	 *            : Danh sách các tham số dạng NameValuePair
	 * 
	 * @return JSON
	 */
	public static String execGet(String strUrl, List<NameValuePair> paramList) {
		try {
			if (null != paramList) {
				strUrl += getStrParam(paramList);
			}

			// Request to Server
			Log.v("URL", strUrl);
			URL url = new URL(strUrl);

			HttpURLConnection http;
			http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("GET");
			http.connect();
			InputStream is = http.getInputStream();
			String ret = inputStreem2str(is);
			is.close();

			return ret;

		} catch (Exception e) {
			Log.e("ApiConnectexecGet", e.getMessage());
			return null;
		}
	}

	/**
	 * Get method
	 * 
	 * @param group
	 *            : Server API Group đang sử dụng (A,F,L...)
	 * 
	 * @param method
	 *            : Method để get dữ liệu v�?
	 * 
	 * @param paramList
	 *            : Danh sách các tham số dạng NameValuePair
	 * 
	 * @return InputStream
	 */
	public static InputStream getInputStrem(String strUrl,
			List<NameValuePair> paramList) {
		try {
			if (null != paramList) {
				strUrl += getStrParam(paramList);
			}

			// Request to Server
			Log.v("URL", strUrl);
			URL url = new URL(strUrl);

			HttpURLConnection http;
			http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("GET");
			http.connect();
			InputStream is = http.getInputStream();
			// String ret = inputStreem2str(is);
			// is.close();

			return is;

		} catch (Exception e) {
			Log.e("ApiConnectexecGet", e.getMessage());
			return null;
		}
	}
	/**
	 * Get Api Data with server data is 1st priority
	 * Get from server dta
	 * If has wifi or 3G => get from server
	 * No => get from cache
	 * @param strUrl
	 * @param paramList
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public String getApiData(String strUrl, List<NameValuePair> paramList)
			throws NoSuchAlgorithmException, IOException {
		if (null != paramList) {
			strUrl += getStrParam(paramList);
		}
		ApiCache api = new ApiCache(mContext, ApiCache.CACHE_DIR);
		InputStream in = api.getApiData(strUrl);
		String ret = inputStreem2str(in);
		in.close();
		return ret;
	}
	/**
	 * Get Api Data with cache is 1st priority
	 * Check in cache data
	 * If exst in cache => get from cache
	 * No => get from server
	 * @param strUrl
	 * @param paramList
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public String getApiDataWithCachePriority(String strUrl, List<NameValuePair> paramList)
			throws NoSuchAlgorithmException, IOException {
		if (null != paramList) {
			strUrl += getStrParam(paramList);
		}
		ApiCache api = new ApiCache(mContext, ApiCache.CACHE_DIR);
		InputStream in = api.getApiDataWithCachePriority(strUrl);
		String ret = inputStreem2str(in);
		in.close();
		return ret;
	}
	/**
	 * Post method
	 * 
	 * @param group
	 *            : Server API Group đang sử dụng (A,F,L...)
	 * 
	 * @param method
	 *            : Method để lấy dữ liệu v�? hoặc post dữ liệu lên server
	 * 
	 * @param paramList
	 *            : Danh sách các tham số dạng NameValuePair
	 * 
	 * @return JSON
	 */
	public static String execPost(Context context, String url,
			List<NameValuePair> paramList) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		try {
			httppost.setEntity(new UrlEncodedFormEntity(paramList));
			HttpResponse response = httpclient.execute(httppost);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			response.getEntity().writeTo(byteArrayOutputStream);

			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				String ret = byteArrayOutputStream.toString();
				return ret;
			} else {
				Log.e("DbApiMgr#execPost",
						"HttpStatus is "
								+ Integer.toString(response.getStatusLine()
										.getStatusCode()));
				return null;
			}

		} catch (Exception e) {
			Log.e("ApiConnectexecPost", e.getMessage());
			return null;
		}
	}

	/**
	 * Build URL
	 * 
	 * @param params
	 * 
	 * @return params URL
	 */
	static private String getStrParam(List<NameValuePair> params) {
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
	 * Get Bit map from URL
	 * 
	 * @param str
	 * @return Bitmap
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static Bitmap loadBitmap(String url) throws MalformedURLException,
			IOException {
		return ((BitmapDrawable) loadImage(url)).getBitmap();
	}

	/**
	 * Get Drawalbe from URL
	 * 
	 * @param str
	 * @return Drawable
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static Drawable loadImage(String url) throws MalformedURLException,
			IOException {

		HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url)
				.openConnection();

		httpURLConnection.setRequestMethod("GET");

		httpURLConnection.connect();

		InputStream stream = httpURLConnection.getInputStream();

		Drawable drawable = Drawable.createFromStream(stream, "");

		stream.close();

		return drawable;
	}
}
