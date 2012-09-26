package com.generate.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.generate.model.SectionTemplate;

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
	
	public void assigmentLogicOrMethod(Class<?> c, List<SectionTemplate> sectionTemplates){
		List<SectionTemplate> sections = null;
		for (SectionTemplate sectionTemplate : sectionTemplates) {
			if(!StringUtils.isNullOrBlank(sectionTemplate.getLogic())){
				sections = XMLTemplateHelper.getInstance().logicTemplates.get(sectionTemplate.getLogic());
				assigmentLogicOrMethod(c, sections);
			}
			try {
				assignmentSections(c.getClass(), sectionTemplate);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void assignmentSections(Class<?> c, SectionTemplate sectionTemplate)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		String[] parameters = sectionTemplate.getParameter().split(",");
		String content = sectionTemplate.getContent();
		StringBuffer assignmentContext = new StringBuffer();
		for (int j = 0; j < parameters.length; j++) {
			// spell method name
			String methodName = "get"
					+ StringUtils.toUppercaseFirstLetter(parameters[j]);
			Method method = c.getMethod(methodName);
			String value = method.invoke(c.getClass()).toString();
			String[] values = value.split("/");
			// Multiple comma-separated
			if (values.length > 1) {
				for (int i = 0; i < values.length; i++) {
					if (i == 0) {
						assignmentContext.append(content.replaceAll("\\$\\["
								+ parameters[j] + "\\]", values[i]));
						continue;
					}
					assignmentContext.append(content.replaceAll("\\$\\["
							+ parameters[j] + "\\]", values[i]));
				}
			} else {
				if (j == 0) {
					assignmentContext.append(content.replaceAll("\\$\\["
							+ parameters[j] + "\\]", value));
					continue;
				}
				assignmentContext.replace(
						0,
						assignmentContext.length(),
						assignmentContext.toString().replaceAll(
								"\\$\\[" + parameters[j] + "\\]", value));
			}
		}
		sectionTemplate.setContent(assignmentContext.toString());
	}
	
	
	
}
