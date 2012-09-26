package com.generate.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Assert;

import com.generate.model.SectionTemplate;

@SuppressWarnings("unchecked")
public class XMLTemplateHelper {

	public static final String TEMPLATE = "template";
	public static final String METHOD = "method";
	public static final String SECTION = "section";
	public static final String LOGIC = "logic";
	public static final String NAME = "name";
	public static final String PARAMETER = "parameter";
	public static final String REF = "ref";
	public static final String DEFAULTDIRECTORY = System
			.getProperty("user.dir") + "/bin/com/generate/template/";

	public List<File> templateFiles = new ArrayList<File>();
	public Map<String,List<SectionTemplate>> methodTemplates = new HashMap<String, List<SectionTemplate>>();
	public Map<String,List<SectionTemplate>> logicTemplates = new HashMap<String, List<SectionTemplate>>();

	private Document doc = DocumentHelper.createDocument();
	private SAXReader reader = new SAXReader();
	private static List<SectionTemplate> sectionTemplates = new ArrayList<SectionTemplate>();
	private static XMLTemplateHelper parseXMLTemplateHelper;

	private XMLTemplateHelper() {
		super();
		init();
	}

	public static XMLTemplateHelper getInstance() {
		if (null == parseXMLTemplateHelper) {
			parseXMLTemplateHelper = new XMLTemplateHelper();
		}
		return parseXMLTemplateHelper;
	}

	// convert all XML files to a Map
	public void init() {
		getXMLDirectory(new File(DEFAULTDIRECTORY));
		List<Element> sectionElements = new ArrayList<Element>();
		List<Element> logicElements = new ArrayList<Element>();
		List<Element> methodElements = new ArrayList<Element>();
		try {
			//parse XML to Section
			for (File template : templateFiles) {
				doc = reader.read(template);
				Element root = doc.getRootElement();
				List<Element> elements = root.elements();
				for (Element element : elements) {
					if(SECTION.equalsIgnoreCase(element.getName())){
						sectionElements.add(element);
					}else if(LOGIC.equalsIgnoreCase(element.getName())){
						logicElements.add(element);
					}else if(METHOD.equalsIgnoreCase(element.getName())){
						methodElements.add(element);
					}
				}
			}
			convertXMLToSectionModel(sectionElements);
			convertXMLToLogicModel(logicElements);
			convertXMLToMethodModel(methodElements);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// load all XML files
	public void getXMLDirectory(File file) {
		File[] files = null;
		if (file.isDirectory()) {
			files = file.listFiles();
			for (File child : files) {
				getXMLDirectory(child);
			}
		} else {
			templateFiles.add(file);
		}
	}

	public void convertXMLToMethodModel(List<Element> methodElements) throws DocumentException {
		for (Element element : methodElements) {
			Iterator<Element> nodeList = element.elementIterator();
			String methodName = element.attributeValue(NAME);
			List<SectionTemplate> sections = new ArrayList<SectionTemplate>();
			while (nodeList.hasNext()) {
				Element note = (Element) nodeList.next();
				Assert.assertEquals("Named " + note.getName()
						+ " is not Legitimate ", SECTION, note.getName());
				String sectionName = note.attributeValue(REF); 
				sections.add(getSectionTemplate(sectionName));
			}
			methodTemplates.put(methodName,sections);
		}
	}
	
	// convert Elements file to SectionTemplate
	public void convertXMLToSectionModel(List<Element> sectionElements) throws DocumentException {
		for (Element element : sectionElements) {
			SectionTemplate sectionTemplate = new SectionTemplate();
			sectionTemplate.setName(element.attributeValue(NAME));
			sectionTemplate.setLogic(element.attributeValue(LOGIC));
			sectionTemplate.setParameter(element.attributeValue(PARAMETER));
			sectionTemplate.setContent(element.getText());
			sectionTemplates.add(sectionTemplate);
		}
	}

	// convert Elements file to LogicTemplate
	public void convertXMLToLogicModel(List<Element> logicElements) throws DocumentException {
		for (Element element : logicElements) {
			Iterator<Element> nodeList = element.elementIterator();
			String logicName = element.attributeValue(NAME);
			List<SectionTemplate> sections = new ArrayList<SectionTemplate>();
			while (nodeList.hasNext()) {
				Element note = (Element) nodeList.next();
				Assert.assertEquals("Named " + note.getName()
						+ " is not Legitimate ", SECTION, note.getName());
				String sectionName = note.attributeValue(REF); 
				sections.add(getSectionTemplate(sectionName));
			}
			logicTemplates.put(logicName, sections);
		}
	}

	public SectionTemplate getSectionTemplate(String sectionName){
		for (SectionTemplate sectionTemplate : sectionTemplates) {
			if(sectionName.equalsIgnoreCase(sectionTemplate.getName())){
				return sectionTemplate;
			}
		}
		return new SectionTemplate();
	}
	
}
