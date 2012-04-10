package com.fancon.android.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class StringUtil {
	
	/**
	 * encode MD5 for password
	 * @param input
	 * @return
	 */
	public static final String md5(final String input) {

		try {

			// Create MD5 Hash
			MessageDigest messageDigest = java.security.MessageDigest
					.getInstance("MD5");
			messageDigest.update(input.getBytes());
			byte messageDigestBytes[] = messageDigest.digest();
			// Create Hex String
			StringBuffer md5String = new StringBuffer();
			for (int i = 0; i < messageDigestBytes.length; i++) {
				// Convert to Hex
				String h = Integer.toHexString(0xFF & messageDigestBytes[i]);
				// Make sure we have leading zeros.
				while (h.length() < 2) {
					h = "0" + h;
				}
				// Append to our final String
				md5String.append(h);
			}
			// Return our MD5 Hash
			return md5String.toString();
		} catch (NoSuchAlgorithmException e) {
			// In case the device does not support MD5.
			e.printStackTrace();

		}

		return "";

	}
	/**
	 * 
	 * @param str: input string to standardize the length.
	 * @param length: max length of string
	 * @return If string longer max length String after cutted and append ... in the end of string, else return string input
	 */
	public static final String validString(String str,int length) {
		if (str.length()>length) {
			str = str.substring(0,length)+"...";
		}
		return str;
	}
	
	/**
	 * Remove emoji
	 * @param inStr
	 * @return String without emoji
	 */
	public static String removeHexChar(String inStr) {
        if(inStr == null) return null;

        StringBuilder sbOutput = new StringBuilder();
        char ch;

        for(int i = 0; i < inStr.length(); i++) {
            ch = inStr.charAt(i);
            if( ( ch >= 0x0020 && ch <= 0xD7FF ) || 
                ( ch >= 0xE000 && ch <= 0xFFFD ) ||
                ch == 0x0009 ||
                ch == 0x000A || 
                ch == 0x000D ) {
                sbOutput.append( ch );
            }
        }
        return sbOutput.toString();
	}
	
	/**
	 * rule 1000ï¼?1K					
	 *		1000000ï¼?1M					
	 *		1000000000=1G					
	 * @param count int
	 * @return
	 */
	public static final String getPostCount(int count) {
		String strCount = "";
		if (count < 1000) {
			strCount = String.valueOf(count);
		} else if (count < 1000 * 1000) {
			strCount = String.valueOf(count / 1000) + "K";
		} else if (count < 1000 * 1000 * 1000) {
			strCount = String.valueOf(count / (1000 * 1000)) + "M";
		} else {
			strCount = String.valueOf(count / (1000 * 1000 * 1000)) + "G";
		}
		return strCount;
	}

	/**
	 * remove underline when set click in textview
	 * @author Thanhhv
	 *
	 */
	public static class NonUnderlinedClickableSpan extends ClickableSpan {
		
		@Override public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false);
        }

		@Override
		public void onClick(View widget) {
			// TODO Auto-generated method stub
		}
    }

}
