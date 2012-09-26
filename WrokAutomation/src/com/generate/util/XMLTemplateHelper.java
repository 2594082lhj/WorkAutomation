package com.generate.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Assert;

import com.generate.model.LogicTemplate;
import com.generate.model.MethodTemplate;
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
	public List<MethodTemplate> methodTemplates = new ArrayList<MethodTemplate>();
	public List<SectionTemplate> sectionTemplates = new ArrayList<SectionTemplate>();
	public List<LogicTemplate> logicTemplates = new ArrayList<LogicTemplate>();

	private Document doc = DocumentHelper.createDocument();
	private SAXReader reader = new SAXReader();
	private static XMLTemplateHelper parseXMLTemplateHelper;

	private XMLTemplateHelper() {
		super();
		init();
	}

	public static synchronized XMLTemplateHelper getInstance() {
		if (null == parseXMLTemplateHelper) {
			parseXMLTemplateHelper = new XMLTemplateHelper();
		}
		return parseXMLTemplateHelper;
	}

	// convert all XML files to a Map
	public void init() {
		getXMLDirectory(new File(DEFAULTDIRECTORY));
		try {
			for (File template : templateFiles) {
				doc = reader.read(template);
				Element root = doc.getRootElement();
				List<Element> elements = root.elements();
				for (Element element : elements) {
					if(METHOD.equalsIgnoreCase(element.getName())){
						convertXMLToMethodModel(element);
					}else if(SECTION.equalsIgnoreCase(element.getName())){
						convertXMLToSectionModel(element);
					}else if(LOGIC.equalsIgnoreCase(element.getName())){
						convertXMLToLogicModel(element);
					}else{
						Assert.fail("The node named ["+element.getName()+"] in ["+template.getName()+"] can not be resolved !");
					}
				}
			}
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

	public void convertXMLToMethodModel(Element element) throws DocumentException {
		MethodTemplate methodTemplate = new MethodTemplate();
		Iterator<Element> nodeList = element.elementIterator();
		methodTemplate.setName(element.attributeValue(NAME));
		List<String> sections = methodTemplate.getSections();
		while (nodeList.hasNext()) {
			Element note = (Element) nodeList.next();
			Assert.assertEquals("Named " + note.getName()
					+ " is not Legitimate ", SECTION, note.getName());
			sections.add(note.attributeValue(REF));
		}
		methodTemplates.add(methodTemplate);
	}
	
	// convert a XML file to SectionTemplate
	public void convertXMLToSectionModel(Element element) throws DocumentException {
		SectionTemplate sectionTemplate = new SectionTemplate();
		sectionTemplate.setName(element.attributeValue(NAME));
		sectionTemplate.setLogic(element.attributeValue(LOGIC));
		sectionTemplate.setParameter(element.attributeValue(PARAMETER));
		sectionTemplate.setContent(element.getText());
		sectionTemplates.add(sectionTemplate);
	}

	// convert a XML file to LogicTemplate
	public void convertXMLToLogicModel(Element element) throws DocumentException {
		LogicTemplate logicTemplate = new LogicTemplate();
		Iterator<Element> nodeList = element.elementIterator();
		logicTemplate.setName(element.attributeValue(NAME));
		List<String> sections = logicTemplate.getSections();
		while (nodeList.hasNext()) {
			Element note = (Element) nodeList.next();
			Assert.assertEquals("Named " + note.getName()
					+ " is not Legitimate ", SECTION, note.getName());
			sections.add(note.attributeValue(REF));
		}
		logicTemplates.add(logicTemplate);
	}

	public static MethodTemplate getMethodTemplate(String methodName){
		List<MethodTemplate> methodTemplates = getInstance().methodTemplates;
		for (MethodTemplate methodTemplate : methodTemplates) {
			if(methodName.equalsIgnoreCase(methodTemplate.getName())){
				return methodTemplate;
			}
		}
		return new MethodTemplate();
	}
	
	public static SectionTemplate getSectionTemplate(String sectionName){
		List<SectionTemplate> sectionTemplates = getInstance().sectionTemplates;
		for (SectionTemplate sectionTemplate : sectionTemplates) {
			if(sectionName.equalsIgnoreCase(sectionTemplate.getName())){
				return sectionTemplate;
			}
		}
		return new SectionTemplate();
	}
	
	public static LogicTemplate getLogicTemplate(String logicName) {
		List<LogicTemplate> logicTemplates = getInstance().logicTemplates;
		for (LogicTemplate logicTempalte : logicTemplates) {
			if (logicName.equals(logicTempalte.getName())) {
				return logicTempalte;
			}
		}
		return new LogicTemplate();
	}
	
	public static List<SectionTemplate> getLogicTemplates(String logicName){
		List<SectionTemplate> sectionTemplates = new ArrayList<SectionTemplate>();
		LogicTemplate logicTemplate = getLogicTemplate(logicName);
		List<String> sections = logicTemplate.getSections();
		for (String sectionName : sections) {
			sectionTemplates.add(getSectionTemplate(sectionName));
		}
		return sectionTemplates;
	}
	
	public static List<SectionTemplate> getSectionTemplates(String methodName){
		List<SectionTemplate> sectionTemplates = new ArrayList<SectionTemplate>();
		MethodTemplate methodTemplate = getMethodTemplate(methodName);
		List<String> sections = methodTemplate.getSections();
		for (String sectionName : sections) {
			SectionTemplate sectionTemplate = getSectionTemplate(sectionName);
			// if section include logic
			if(!StringUtils.isNullOrBlank(sectionTemplate.getLogic())){
				String [] logicNames = sectionTemplate.getLogic().split(",");
				for (String logicName : logicNames) {
					sectionTemplates.addAll(getLogicTemplates(logicName));
				}
			}
			sectionTemplates.add(getSectionTemplate(sectionName));
		}
		return sectionTemplates;
	}
	
}
