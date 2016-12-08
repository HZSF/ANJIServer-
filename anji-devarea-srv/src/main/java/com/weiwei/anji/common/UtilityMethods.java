package com.weiwei.anji.common;

import java.util.Map;

public class UtilityMethods {
	public static String getMapValueString(Map map, String key){
		if(map == null)
			return "";
		if(map.containsKey(key)){
			return (String)map.get(key);
		}else{
			return "";
		}
	}
}
