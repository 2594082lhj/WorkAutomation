package com.generate.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectorUtils {
	
	public static String assignmentPlaceholder(String source,String value,String regex){
		Pattern pattern = Pattern.compile("\\$\\["+regex+"\\]");  
		Matcher matcher = pattern.matcher(source);
		if(matcher.find()){
			source = matcher.replaceFirst(value);
		}
		return source;
	}
	
	public static void main(String[] args) {
		String source = "flsjelfiefjeifj $[name] lsjflseijfeoiljf";
		String value = "Value";
		String regex = "\\$\\[name\\]";
		System.out.println(assignmentPlaceholder(source, value,regex));
	}
	
}
