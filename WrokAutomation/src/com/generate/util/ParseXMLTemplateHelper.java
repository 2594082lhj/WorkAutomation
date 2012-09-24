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
public class ParseXMLTemplateHelper {

	public static final String METHODS = "methods";
	public static final String SECTIONS = "sections";
	public static final String LOGICS = "logics";
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
	private static ParseXMLTemplateHelper parseXMLTemplateHelper;

	private ParseXMLTemplateHelper() {
		super();
		init();
	}

	public static synchronized ParseXMLTemplateHelper getInstance() {
		if (null == parseXMLTemplateHelper) {
			parseXMLTemplateHelper = new ParseXMLTemplateHelper();
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
				String rootName = root.getName();
				if (METHODS.equalsIgnoreCase(rootName)) {
					convertXMLToMethodModel(root);
				}else if(SECTIONS.equalsIgnoreCase(rootName)){
					convertXMLToSectionModel(root);
				} else if (LOGICS.equalsIgnoreCase(rootName)) {
					convertXMLToLogicModel(root);
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

	public void convertXMLToMethodModel(Element root) throws DocumentException {
		MethodTemplate methodTemplate = new MethodTemplate();
		Iterator<Element> nodeList = root.elementIterator();
		methodTemplate.setName(root.attributeValue(NAME));
		List<String> sections = methodTemplate.getSection();
		while (nodeList.hasNext()) {
			Element note = (Element) nodeList.next();
			Assert.assertEquals("Named " + note.getName()
					+ " is not Legitimate ", METHOD, note.getName());
			sections.add(note.attributeValue(REF));
		}
		methodTemplates.add(methodTemplate);
	}

	
	// convert a XML file to SectionTemplate
	public void convertXMLToSectionModel(Element root) throws DocumentException {
		Iterator<Element> nodeList = root.elementIterator();
		while (nodeList.hasNext()) {
			Element note = (Element) nodeList.next();
			SectionTemplate sectionTemplate = new SectionTemplate();
			sectionTemplate.setName(note.attributeValue(NAME));
			sectionTemplate.setLogic(note.attributeValue(LOGIC));
			sectionTemplate.setParameter(note.attributeValue(PARAMETER));
			sectionTemplate.setContent(note.getText());
			sectionTemplates.add(sectionTemplate);
		}
	}

	// convert a XML file to LogicTemplate
	public void convertXMLToLogicModel(Element root) throws DocumentException {
		LogicTemplate logicTemplate = new LogicTemplate();
		Iterator<Element> nodeList = root.elementIterator();
		logicTemplate.setName(root.attributeValue(NAME));
		List<String> sections = logicTemplate.getSection();
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
	
	
}
