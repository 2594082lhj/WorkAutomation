package com.generate.util;

import org.junit.Test;

import com.generate.model.SectionTemplate;

public class TestXMLTemplateHelper {

	@Test
	public void testGetSectionTemplate() {
		String sectionName = "definedMethod";
		SectionTemplate sectionTemplate = XMLTemplateHelper.getInstance().getSectionTemplate(sectionName);
		System.out.println(" Name :"+sectionTemplate.getName());
		System.out.println(" Content :"+sectionTemplate.getContent());
	}
	
	

}
