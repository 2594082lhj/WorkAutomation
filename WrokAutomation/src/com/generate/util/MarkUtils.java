package com.generate.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkUtils {
	
	public static final String LOGICMARK = ".*(\\$\\[logic\\:.*]).*";
	
	public static String getLogicMarkName(String source){
		Pattern pattern = Pattern.compile(LOGICMARK,Pattern.CASE_INSENSITIVE);  
		Matcher matcher = pattern.matcher(source);
		String returnValue = "";
		if(matcher.find()){
			returnValue = matcher.group(1).replaceAll("\\$\\[logic:", "");
			returnValue = returnValue.replaceAll("\\]", "");
		}
		return returnValue;
	}
	
	public static String logicToSection(String source){
		return "";
	}
	
	
}
