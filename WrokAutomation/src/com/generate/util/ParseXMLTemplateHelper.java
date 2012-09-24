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

	public static final String Method = "method";
	public static final String NAME = "name";
	public static final String SECTION = "section";
	public static final String PARAMETER = "parameter";
	public static final String LOGIC = "logic";
	public static final String REF = "ref";
	public static final String DEFAULTDIRECTORY = System
			.getProperty("user.dir") + "/bin/com/generate/template/";

	public List<File> templateFiles = new ArrayList<File>();
	public List<MethodTemplate> methodTemplates = new ArrayList<MethodTemplate>();
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
				if (Method.equals(root.getName())) {
					convertXMLToSectionModel(root);
				} else if (LOGIC.equals(root.getName())) {
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

	// convert a XML file to SectionTemplate
	public void convertXMLToSectionModel(Element root) throws DocumentException {
		MethodTemplate methodTemplate = new MethodTemplate();
		List<SectionTemplate> sectionTemplates  = methodTemplate.getSetionTemplates();
		Iterator<Element> nodeList = root.elementIterator();

		methodTemplate.setName(root.attributeValue(NAME));
		
		while (nodeList.hasNext()) {
			Element note = (Element) nodeList.next();
			SectionTemplate sectionTemplate = new SectionTemplate();
			// if note is logic
			if (LOGIC.equals(note.getName())) {
				convertXMLToLogicModel(root);
				continue;
			}
			sectionTemplate.setName(note.attributeValue(NAME));
			sectionTemplate.setLogic(note.attributeValue(LOGIC));
			sectionTemplate.setParameter(note.attributeValue(PARAMETER));
			sectionTemplate.setContent(note.getText());
			sectionTemplates.add(sectionTemplate);
		}
		methodTemplates.add(methodTemplate);
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

	public static LogicTemplate getLogicTemplate(String logicName) {
		List<LogicTemplate> logicTemplates = getInstance().logicTemplates;
		for (LogicTemplate logicTempalte : logicTemplates) {
			if (logicName.equals(logicTempalte.getName())) {
				return logicTempalte;
			}
		}
		return new LogicTemplate();
	}
	
	public static List<SectionTemplate> getAllSectionTemplates(){
		List<MethodTemplate> methodTemplates = getInstance().methodTemplates;
		List<SectionTemplate> sectionTemplates = new ArrayList<SectionTemplate>();
		for (MethodTemplate methodTemplate : methodTemplates) {
			sectionTemplates.addAll(methodTemplate.getSetionTemplates());
		}
		return sectionTemplates;
	}
	
	public static SectionTemplate getSectionTemplate(String sectionName){
		List<SectionTemplate> sectionTemplates = getAllSectionTemplates();
		for (SectionTemplate sectionTemplate : sectionTemplates) {
			if(sectionName.equalsIgnoreCase(sectionTemplate.getName())){
				return sectionTemplate;
			}
		}
		return new SectionTemplate();
	}
	
}
