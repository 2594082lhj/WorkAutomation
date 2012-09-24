package com.generate.util;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.generate.model.LogicTemplate;
import com.generate.model.MethodTemplate;
import com.generate.model.SectionTemplate;

public class TestParseXMLTemplateHelper {

	@Test
	public void testGetXMLDirectory(){
		ParseXMLTemplateHelper parseXMLTemplateHelper = ParseXMLTemplateHelper.getInstance();
		List<File> templateFiles = parseXMLTemplateHelper.templateFiles;
		for (File file : templateFiles) {
			System.out.println(file.getName());
		}
	}
	
	@Test
	public void testGetInstance() {
		ParseXMLTemplateHelper parseXMLTemplateHelper = ParseXMLTemplateHelper.getInstance();
		Assert.assertNotNull(parseXMLTemplateHelper);
	}

	@Test
	public void testInit() {
		ParseXMLTemplateHelper.getInstance().init();
	}

	@Test
	public void testConvertXMLToSectionModel() {
		List<MethodTemplate> methodTemplates = ParseXMLTemplateHelper.getInstance().methodTemplates;
		List<SectionTemplate> sectionTemplates = null;
		for (MethodTemplate methodTemplate : methodTemplates) {
			System.out.println("Method -name:"+methodTemplate.getName());
			sectionTemplates = methodTemplate.getSetionTemplates();
			for (SectionTemplate sectionTemplate : sectionTemplates) {
				System.out.print("\t Section -name: "+sectionTemplate.getName());
				System.out.print("\t		 -parameter "+sectionTemplate.getParameter());
				System.out.print("\t		 -logic "+sectionTemplate.getLogic());
				System.out.print("\t		 -context "+sectionTemplate.getContent());
				System.out.println();
			}
		}
	}


	@Test
	public void testGetLogicTemplate() {
		List<LogicTemplate> logicTemplates = ParseXMLTemplateHelper.getInstance().logicTemplates;
		for (LogicTemplate logicTemplate : logicTemplates) {
			System.out.println("Logic -Name:"+logicTemplate.getName());
			for (String section : logicTemplate.getSection()) {
				System.out.println("\tSection -Name"+section);
			}
		}
	}
	
	@Test
	public void testGetSectionTemplate(){
		String sectionName = "returnNull";
		SectionTemplate sectionTemplate = ParseXMLTemplateHelper.getSectionTemplate(sectionName);
		System.out.println(sectionTemplate.getContent());
	}
	
	@Test
	public void testAssignment(){
		
		
		
	}


}
