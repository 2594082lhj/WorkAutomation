package com.generate.util;


public class StringUtils {
	
	//String[] to String use "," separated
	public static String toCommaSeparatedString(String[] stringArray){
		StringBuffer string = new StringBuffer();
		for (int i = 0; i < stringArray.length; i++) {
			if(i == 0){
				string.append(stringArray[i]);
				continue;
			}
			string.append(","+stringArray[i]);
		}
		return string.toString();
	}
	
	public static boolean isNullOrBlank(String[] value){
		if(value == null || value.length == 0 ){
			return true;
		}
		return false;
	}
	
	public static boolean isNullOrBlank(String value){
		if(value == null || "".equalsIgnoreCase(value) || value.length() == 0 ){
			return true;
		}
		return false;
	}
	
	public static String toUppercaseFirstLetter(String value){
		if(isNullOrBlank(value)){
			return null;
		}
		return new StringBuffer()
		.append(Character.toUpperCase(value.charAt(0)))
		.append(value.substring(1, value.length()))
		.toString();
		
	}
	
	public static String toLowerCaseFirstLetter(String value){
		if(isNullOrBlank(value)){
			return null;
		}
		return new StringBuffer()
		.append(Character.toLowerCase(value.charAt(0)))
		.append(value.substring(1, value.length()))
		.toString();
	}
	
}
