package com.fancon.android.gardenjokes.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Check user name email and etc...
 * @author ThanhHV
 *
 */
public class CheckValidate {
	
	/**
	 * Check format of email
	 * 
	 * @param emailAddress
	 * @return true if email is valid
	 */
	public static boolean checkEmailFormat(String emailAddress){  
		   String  expression="^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"; 
		   CharSequence inputStr = emailAddress;  
		   Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);  
		   Matcher matcher = pattern.matcher(inputStr);  
		   return matcher.matches();  
		  
		 }
	/**
	 * Check string include number or alphabet
	 * 
	 * @param str
	 * @return true if string is valid
	 */
	public static boolean checkAlphabetOrNumber(String str){
		return str.matches("\\w*");
	}

}
