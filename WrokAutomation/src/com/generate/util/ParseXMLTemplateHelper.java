package com.generate.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Assert;

import com.generate.model.LogicTemplate;
import com.generate.model.SectionTemplate;

@SuppressWarnings("unchecked")
public class ParseXMLTemplateHelper {
	
	public static final String Method = "method";
	public static final String SECTION = "section";
	public static final String LOGIC = "logic";
	
	private Document doc = DocumentHelper.createDocument();
	private SAXReader reader = new SAXReader();
	
	public static final String DEFAULTDIRECTORY = System.getProperty("user.dir")+"/bin/com/generate/template/";
	
	private static ParseXMLTemplateHelper parseXMLTemplateHelper ;
	public static List<File>  templateFiles= new ArrayList<File>();
	public static List<SectionTemplate> sectionTemplates = new ArrayList<SectionTemplate>();
	public static List<LogicTemplate> logicTemplates = new ArrayList<LogicTemplate>();
	public static List<String> methodNames = new ArrayList<String>();
	
	private ParseXMLTemplateHelper(){
		super();
		try {
			init();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized ParseXMLTemplateHelper getInstance(){
		if(null == parseXMLTemplateHelper){
			parseXMLTemplateHelper = new ParseXMLTemplateHelper();
		}
		return parseXMLTemplateHelper;
	}
	
	//convert all XML files to a Map
	public void init() throws DocumentException{
		getXMLDirectory(new File(DEFAULTDIRECTORY));
		for (File template : templateFiles) {
			doc = reader.read(template);
			Element root = doc.getRootElement();
			if(Method.equals(root.getName())){
				convertXMLToSectionModel(root);
			}else if(LOGIC.equals(root.getName())){
				convertXMLToLogicModel(root);
			}
		}
	}
	
	// load all XML files
	public void getXMLDirectory(File file){
		File[] files = null;
		if(file.isDirectory()){
			files = file.listFiles();
			for (File child : files) {
				getXMLDirectory(child);
			}
		}else{
			templateFiles.add(file);
		}
	}
	
	//convert a XML file to SectionTemplate
	public void convertXMLToSectionModel(Element root) throws DocumentException{
		
		SectionTemplate xmlTemplate = new SectionTemplate();
		Iterator<Element> nodeList = root.elementIterator();
		
		xmlTemplate.setName(root.attributeValue("name"));
		methodNames.add(root.attributeValue("name"));
		
		Map<String,String> sections = xmlTemplate.getSections();
		while(nodeList.hasNext()){
			Element note = (Element)nodeList.next();
			//if note is logic
			if(LOGIC.equals(note.getName())){
				convertXMLToLogicModel(root);
				continue;
			}
			Assert.assertEquals("Named ["+note.getName()+"] section is not exist ", SECTION, note.getName());
			sections.put(note.attributeValue("name"), note.getText());
		}
		sectionTemplates.add(xmlTemplate);
	}
	
	//convert a XML file to LogicTemplate
	public void convertXMLToLogicModel(Element root) throws DocumentException{
		LogicTemplate logicTemplate = new LogicTemplate();
		Iterator<Element> nodeList = root.elementIterator();
		
		logicTemplate.setName(root.attributeValue("name"));
		
		List<String> sections = logicTemplate.getSection();
		while(nodeList.hasNext()){
			Element note = (Element)nodeList.next();
			Assert.assertEquals("Named "+note.getName()+" is not Legitimate ", LOGIC, note.getName());
			sections.add(note.attributeValue("name"));
		}
		logicTemplates.add(logicTemplate);
	}
	
	public static SectionTemplate getSectionTemplate(String xmlMethodName){
		for (SectionTemplate sectionTemplate : sectionTemplates) {
			if(xmlMethodName.equals(sectionTemplate.getName())){
				return sectionTemplate;
			}
		}
		return new SectionTemplate();
	}
	
	public static LogicTemplate getLogicTempalte(String xmlMethodName){
		for (LogicTemplate logicTempalte : logicTemplates) {
			if(xmlMethodName.equals(logicTempalte.getMethodName())){
				return logicTempalte;
			}
		}
		return new LogicTemplate();
	}
	
}
