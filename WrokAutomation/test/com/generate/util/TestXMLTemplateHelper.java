package com.generate.util;

import junit.framework.Assert;

import org.junit.Test;

import com.generate.model.LogicTemplate;
import com.generate.model.MethodTemplate;
import com.generate.model.SectionTemplate;

public class TestXMLTemplateHelper {

	@Test
	public void testGetMethodTemplate() {
		String methodName = "baseMethod";
		MethodTemplate methodTemplate = XMLTemplateHelper.getMethodTemplate(methodName);
		Assert.assertEquals("baseMethod", methodTemplate.getName());
		Assert.assertEquals("definedMethod", methodTemplate.getSections().get(0));
	}

	@Test
	public void testGetSectionTemplate() {
		String sectionName = "definedMethod";
		SectionTemplate sectionTemplate = XMLTemplateHelper.getSectionTemplate(sectionName);
		Assert.assertEquals("note,name", sectionTemplate.getParameter());
	}

	@Test
	public void testGetLogicTemplate() {
		String logicName = "baseLogic";
		LogicTemplate logicTemplate = XMLTemplateHelper.getLogicTemplate(logicName);
		Assert.assertEquals(2, logicTemplate.getSections().size());
	}

}
