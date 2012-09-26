package com.generate.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.generate.model.SectionTemplate;
import com.generate.model.config.BaseConfig;

public class TestBaseAssignmentSection {

	@Test
	public void testAssignment() throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {

		BaseConfig config = new BaseConfig();
		config.setMapping("test");
		config.setName(" name for test");
		config.setNote(" note for test ");
		config.setParameter("id/name/age/gender");
		config.setHqlColunmName("o.one,o.two,o.three,o.for");
		config.setHeaderName("one,two,three,for");
		config.setMethodName("baseMethod");

		Map<String, List<SectionTemplate>> methodTemplates = XMLTemplateHelper
				.getInstance().methodTemplates;
		List<SectionTemplate> sectionsForMethod = methodTemplates.get(config
				.getMethodName());

		Map<String, List<SectionTemplate>> logicTemplates = XMLTemplateHelper
				.getInstance().logicTemplates;
		List<SectionTemplate> sectionsForLogic = null;
		// assignment
		Class<? extends BaseConfig> c = config.getClass();
		for (SectionTemplate sectionTemplate : sectionsForMethod) {
			if(!StringUtils.isNullOrBlank(sectionTemplate.getLogic())){
				sectionsForLogic = logicTemplates.get(sectionTemplate.getLogic());
			}
			assignmentSections(c.getClass(), sectionTemplate);
		}

		// print for view
		for (SectionTemplate sectionTemplate : sectionsForMethod) {
			System.out.println("Section Name:" + sectionTemplate.getName());
			System.out.println("Section parameter:"
					+ sectionTemplate.getParameter());
			System.out.print("Section Content:" + sectionTemplate.getContent());
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
