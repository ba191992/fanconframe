/* Copyright (C) 2011 Catch.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Harry Tormey   <harry@catch.com>
 */
package com.fancon.android.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.format.DateUtils;
import android.util.Log;

public class FileUtil {
	private static final int BUFFER_SIZE = 8 * 1024;
	public static final String NOMEDIA_FILENAME = ".nomedia";
	private static final long CACHE_FILE_EXPIRATION = DateUtils.DAY_IN_MILLIS * 30;
	private static final String LOGCAT_NAME = "FileUtil";

	private static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	/**
	 * Use MD5 algorithm to encode a text
	 * 
	 * @param text
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String MD5(String text) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		MessageDigest md;
		md = MessageDigest.getInstance("MD5");
		byte[] md5hash = new byte[32];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		md5hash = md.digest();
		return convertToHex(md5hash);
	}

	/**
	 * 
	 * @param context
	 * @param dir
	 * @return
	 */
	private static File getExternalStorageDir(Context context, String dir) {
		if (context != null && dir != null) {
			File extMediaDir = new File(
					Environment.getExternalStorageDirectory()
							+ "/Android/data/" + context.getPackageName() + dir);

			if (extMediaDir.exists()) {
				createNomediaDotFile(context, extMediaDir);
				return extMediaDir;
			}

			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				File sdcard = Environment.getExternalStorageDirectory();

				if (sdcard.canWrite()) {
					extMediaDir.mkdirs();
					createNomediaDotFile(context, extMediaDir);
					return extMediaDir;
				} else {
					Log.e(LOGCAT_NAME,
							"SD card not writeable, unable to create directory: "
									+ extMediaDir.getPath());
				}
			} else {
				return extMediaDir;
			}
		}
		return null;
	}

	private static void createNomediaDotFile(Context context, File directory) {
		if (context != null && directory != null) {
			File nomedia = new File(directory, NOMEDIA_FILENAME);

			if (!nomedia.exists()) {
				try {
					nomedia.createNewFile();
				} catch (IOException e) {
					Log.e(LOGCAT_NAME, "unable to create .nomedia file in "
							+ directory.getPath(), e);
				}
			}
		}
	}

	public static File getExternalCacheDir(Context context) {
		return getExternalStorageDir(context, "/cache");
	}

	/**
	 * Default cache in cache folder
	 * 
	 * @param context
	 * @return
	 */
	public static File getInternalCacheDir(Context context) {
		if (context != null) {
			File intCacheDir = new File(context.getCacheDir(), null);
			if (!intCacheDir.exists()) {
				intCacheDir.mkdirs();
			}

			return intCacheDir;
		}
		return null;
	}

	// get cache dir from external folder params
	public static File getInternalCacheDir(Context context, String dir) {
		if (context != null) {
			File intCacheDir = new File(context.getCacheDir(), dir);
			if (!intCacheDir.exists()) {
				intCacheDir.mkdirs();
			}

			return intCacheDir;
		}
		return null;
	}

	/**
	 * Add a file to cache by file name
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static File addFileToCache(Context context, String fileName) {
		File intCacheDir = getInternalCacheDir(context);
		return addFileToCache(context, fileName, intCacheDir);
	}

	public static File addFileToCache(Context context, String fileName,
			String dir) {
		File intCacheDir = getInternalCacheDir(context, dir);
		return addFileToCache(context, fileName, intCacheDir);
	}

	public static File addFileToExternalCache(Context context, String fileName) {
		File extCacheDir = getExternalCacheDir(context);
		return addFileToCache(context, fileName, extCacheDir);
	}

	public static File addFileToCache(Context context, String fileName,
			File cacheDir) {
		if (context != null) {
			if (cacheDir != null) {
				File cachedFile = new File(cacheDir, fileName);
				if (!cachedFile.exists()) {
					try {
						cachedFile.createNewFile();
					} catch (IOException e) {
						Log.e(LOGCAT_NAME, "unable to create file in "
								+ cachedFile.getPath(), e);
					}
				}
				return cachedFile;
			}
		}
		return null;
	}

	public static File getFileFromCache(Context context, String fileName) {
		File intCacheDir = getInternalCacheDir(context);
		return getFileFromCache(context, fileName, intCacheDir);
	}

	//
	public static File getFileFromCache(Context context, String fileName,
			String dir) {
		File intCacheDir = getInternalCacheDir(context, dir);
		return getFileFromCache(context, fileName, intCacheDir);
	}

	public static File getFileFromExternalCache(Context context, String fileName) {
		File extCacheDir = getInternalCacheDir(context);
		return getFileFromCache(context, fileName, extCacheDir);
	}

	//
	public static File getFileFromExternalCache(Context context,
			String fileName, String dir) {
		File extCacheDir = getInternalCacheDir(context, dir);
		return getFileFromCache(context, fileName, extCacheDir);
	}

	/**
	 * Get a file from cache
	 * 
	 * @param context
	 * @param fileName
	 * @param cacheDir
	 * @return
	 */
	public static File getFileFromCache(Context context, String fileName,
			File cacheDir) {
		if (context != null) {
			if (cacheDir != null) {
				File cachedFile = new File(cacheDir, fileName);
				if (cachedFile.exists()) {
					return cachedFile;
				}
			}
		}
		return null;
	}

	public static boolean copyFileToFile(Context context, File src, File dst) {
		if (context != null && src != null && dst != null) {
			try {
				BufferedInputStream in = new BufferedInputStream(
						new FileInputStream(src), BUFFER_SIZE);
				BufferedOutputStream out = new BufferedOutputStream(
						new FileOutputStream(dst), BUFFER_SIZE);

				// Transfer bytes from in to out
				byte[] buf = new byte[BUFFER_SIZE];
				int len;

				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				in.close();
				out.close();
				return true;
			} catch (Exception e) {
				Log.e(LOGCAT_NAME, "unable to copy file " + src.getPath()
						+ " to file " + dst.getPath(), e);
			}
		}

		return false;
	}

	public static boolean moveFileToFile(Context context, File src, File dst) {
		if (context != null && src != null && dst != null) {
			File extDir = Environment.getExternalStorageDirectory();
			File intDir = context.getFilesDir();

			// If src and dst are on the same filesystem, just renameTo()
			if ((src.getPath().startsWith(extDir.getPath()) && dst.getPath()
					.startsWith(extDir.getPath()))
					|| (src.getPath().startsWith(intDir.getPath()) && dst
							.getPath().startsWith(intDir.getPath()))) {
				return src.renameTo(dst);
			}

			// Otherwise, copy and delete src
			if (copyFileToFile(context, src, dst)) {
				return src.delete();
			}
		}

		return false;
	}

	public static boolean copyUriToFile(Context context, Uri uri, File dst) {
		if (context != null && uri != null && dst != null) {
			try {
				BufferedInputStream in = new BufferedInputStream(context
						.getContentResolver().openInputStream(uri), BUFFER_SIZE);
				BufferedOutputStream out = new BufferedOutputStream(
						new FileOutputStream(dst), BUFFER_SIZE);
				// Transfer bytes from in to out
				byte[] buf = new byte[BUFFER_SIZE];
				int len;

				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				in.close();
				out.close();
				return true;
			} catch (Exception e) {
				Log.e(LOGCAT_NAME, "unable to copy URI " + uri.toString()
						+ " to file " + dst.getPath(), e);
			}
		}

		return false;
	}

	public static void cleanCaches(Context context) {
		if (context != null) {
			Log.i(LOGCAT_NAME, "cleaning up caches");
			File internalDir = getInternalCacheDir(context);

			if (internalDir != null) {
				File internalFiles[] = internalDir.listFiles();

				if (internalFiles != null && internalFiles.length > 0) {
					for (File file : internalFiles) {
						if (System.currentTimeMillis() - file.lastModified() >= CACHE_FILE_EXPIRATION) {
							Log.i(LOGCAT_NAME, "deleting " + file.getPath());
							file.delete();
						}
					}
				}
			}

			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				File externalDir = getExternalCacheDir(context);

				if (externalDir != null) {
					File[] externalFiles = externalDir.listFiles();

					if (externalFiles != null && externalFiles.length > 0) {
						for (File file : externalFiles) {
							if (System.currentTimeMillis()
									- file.lastModified() >= CACHE_FILE_EXPIRATION
									&& !NOMEDIA_FILENAME.equals(file.getName())) {
								Log.i(LOGCAT_NAME, "deleting " + file.getPath());
								file.delete();
							}
						}
					}
				}
			}
		}
	}

	/**
	 * clear spectcifiles cache sub folder
	 * 
	 * @param context
	 * @param dir
	 */
	public static void cleanCaches(Context context, String dir) {
		if (context != null) {
			Log.i(LOGCAT_NAME, "cleaning up caches");
			File internalDir = getInternalCacheDir(context, dir);

			if (internalDir != null) {
				File internalFiles[] = internalDir.listFiles();

				if (internalFiles != null && internalFiles.length > 0) {
					for (File file : internalFiles) {
						if (System.currentTimeMillis() - file.lastModified() >= CACHE_FILE_EXPIRATION) {
							Log.i(LOGCAT_NAME, "deleting " + file.getPath());
							file.delete();
						}
					}
				}
			}

			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				File externalDir = getExternalCacheDir(context);

				if (externalDir != null) {
					File[] externalFiles = externalDir.listFiles();

					if (externalFiles != null && externalFiles.length > 0) {
						for (File file : externalFiles) {
							if (System.currentTimeMillis()
									- file.lastModified() >= CACHE_FILE_EXPIRATION
									&& !NOMEDIA_FILENAME.equals(file.getName())) {
								Log.i(LOGCAT_NAME, "deleting " + file.getPath());
								file.delete();
							}
						}
					}
				}
			}
		}
	}

	/**
	 * clear spectcifiles cache sub folder
	 * 
	 * @param context
	 * @param dir
	 */
	public static void cleanCacheApi(Context context, String dir) {
		if (context != null) {
			Log.i(LOGCAT_NAME, "cleaning up caches");
			File internalDir = getInternalCacheDir(context, dir);

			if (internalDir != null) {
				File internalFiles[] = internalDir.listFiles();

				if (internalFiles != null && internalFiles.length > 0) {
					for (File file : internalFiles) {
						Log.i(LOGCAT_NAME, "deleting " + file.getPath());
						file.delete();
					}
				}
			}
		}
	}

	public static File createUniqueFile(File directory, String filename) {
		File file = new File(directory, filename);

		if (file != null && file.exists()) {
			// There is already a file here with the desired name, so let's
			// loop and create a unique one with a numeric suffix.
			//
			// This is awful, find a library that does this for us.
			try {
				int index = 0;

				while (file.exists() && index <= 128) {
					file = new File(directory, "[" + (++index) + "]" + filename);
				}

				if (file.exists()) {
					file = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				file = null;
			}
		}

		return file;
	}
}
