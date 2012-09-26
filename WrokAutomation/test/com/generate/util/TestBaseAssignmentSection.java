package com.generate.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;

import com.generate.model.MethodTemplate;
import com.generate.model.SectionTemplate;
import com.generate.model.config.BaseConfig;

public class TestBaseAssignmentSection {

	@Test
	public void test() throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		BaseConfig config = new BaseConfig();
		config.setMapping("test");
		config.setName(" name for test");
		config.setNote(" note for test ");
		config.setParameter("id/name/age/gender");
		config.setHqlColunmName("o.one,o.two,o.three,o.for");
		config.setHeaderName("one,two,three,for");
		config.setMethodName("baseMethod");

		MethodTemplate methodTemplate = XMLTemplateHelper
				.getMethodTemplate(config.getMethodName());
		List<SectionTemplate> sectionTemplates = XMLTemplateHelper
				.getSectionTemplates(methodTemplate.getName());

		// assignment
		assignmentSections(config, sectionTemplates);
		
		// print for view
		for (SectionTemplate sectionTemplate : sectionTemplates) {
			System.out.println("Section Name:" + sectionTemplate.getName());
			System.out.println("Section parameter:"
					+ sectionTemplate.getParameter());
			System.out.print("Section Content:" + sectionTemplate.getContent());
		}

	}

	public void assignmentSections(BaseConfig config,
			List<SectionTemplate> sectionTemplates) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {

		Class<? extends BaseConfig> c = config.getClass();
		for (SectionTemplate sectionTemplate : sectionTemplates) {
			String[] parameters = sectionTemplate.getParameter().split(",");
			String content = sectionTemplate.getContent();
			StringBuffer assignmentContext = new StringBuffer();
			for (int j = 0; j < parameters.length; j++) {
				// spell method name
				String methodName = "get"
						+ StringUtils.toUppercaseFirstLetter(parameters[j]);
				Method method = c.getMethod(methodName);
				String value = method.invoke(config).toString();
				String[] values = value.split("/");
				// Multiple comma-separated
				if (values.length > 1) {
					for (int i = 0; i < values.length; i++) {
						if (i == 0) {
							assignmentContext.append(content
									.replaceAll("\\$\\[" + parameters[j]
											+ "\\]", values[i]));
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

	
	public void organizeMethod(List<MethodTemplate> methodTemplates){
		
	}
}
