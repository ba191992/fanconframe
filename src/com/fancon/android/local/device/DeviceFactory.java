package com.fancon.android.local.device;

import java.security.MessageDigest;
import java.util.Locale;
import java.util.UUID;

import android.content.Context;
import android.telephony.TelephonyManager;

public class DeviceFactory {

	public static String getDeviceId(Context context) {
		final TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(
						context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		// String deviceId = deviceUuid.toString();

		return deviceUuid.toString();
	}

	public static String getLanguage() {
		return Locale.getDefault().getDisplayLanguage();
	}

	public static String md5(String str) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (byte b : md5(str.getBytes()))
			sb.append(Integer.toHexString(0x100 + (b & 0xff)).substring(1));
		return sb.toString();
	}

	public static byte[] md5(byte[] data) throws Exception {
		// MessageDigest md5 = MessageDigest.getInstance("MD5");
		// md5.update(data);
		// return md5.digest();
		MessageDigest sha = MessageDigest.getInstance("SHA");
		sha.update(data);
		return sha.digest();
	}
}
