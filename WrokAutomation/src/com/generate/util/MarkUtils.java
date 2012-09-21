package com.generate.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkUtils {
	
	public static final String LOGICMARK = "\\$\\[logic\\:.*\\]";
	
	public static String assignmentMark(String source,String value,String regex){
		Pattern pattern = Pattern.compile("\\$\\["+regex+"\\]");  
		Matcher matcher = pattern.matcher(source);
		if(matcher.find()){
			source = matcher.replaceFirst(value);
		}
		return source;
	}
	
	public static String assignmentMark(String source,Map<String,String> valueRegex){
		for (Map.Entry<String, String> entry : valueRegex.entrySet()) {
			return assignmentMark(source,entry.getKey(), entry.getValue());
		}
		return source;
	}
	
	public static boolean hasLogic(String source){
		Pattern pattern = Pattern.compile(LOGICMARK);  
		Matcher matcher = pattern.matcher(source);
		return matcher.find(0);
	}
	
	
	
	public static void main(String[] args) {
//		String source = "flsjelfiefjeifj $[name] lsjflseijfeoiljf";
//		String value = "Value";
//		String regex = "\\$\\[name\\]";
//		System.out.println(assignmentMark(source, value,regex));
		String source = "rejgjljglreiewehwdn $[logic:ieje] jfoeisjf";
		System.out.println(hasLogic(source));
	}
	
}
