package com.weiwei.anji.common;

public class StringUtility {
	public static boolean isEmptyString(String str){
		if(str == null || "".equalsIgnoreCase(str.trim()))
			return true;
		else
			return false;
	}
}
