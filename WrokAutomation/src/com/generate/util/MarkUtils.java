package com.generate.util;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.generate.model.LogicTemplate;

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
		StringBuffer logicSection = new StringBuffer();
		String logicName = getLogicMarkName(source);
		LogicTemplate logicTemplate = ParseXMLTemplateHelper.getLogicTemplate(logicName);
		List<String> sectionNames = logicTemplate.getSection();
		Map<String,String> sections = ParseXMLTemplateHelper.getAllSections(); 
		for (String sectionName : sectionNames) {
			logicSection.append(sections.get(sectionName));
		}
		return logicSection.toString();
	}
	
	
}
