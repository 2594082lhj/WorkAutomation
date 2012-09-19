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

import com.generate.model.XMLTemplate;

@SuppressWarnings("unchecked")
public class ParseXMLTemplateHelper {
	
	public static final String SECTION = "section";
	public static final String DEFAULTDIRECTORY = System.getProperty("user.dir")+"/bin/com/generate/template/";
	
	private static ParseXMLTemplateHelper parseXMLTemplateHelper ;
	public static List<File>  templateFiles= new ArrayList<File>();
	public static List<XMLTemplate> xmlTempaltes = new ArrayList<XMLTemplate>();
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
			xmlTempaltes.add(convertXMLToModel(template));
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
	
	//convert a XML file to XMLTemplate
	public XMLTemplate convertXMLToModel(File file) throws DocumentException{
		
		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		doc = reader.read(file);
		
		Element root = doc.getRootElement();
		Iterator<Element> notes = root.elementIterator();
		XMLTemplate xmlTemplate = new XMLTemplate();
		xmlTemplate.setName(root.attributeValue("name"));
		methodNames.add(root.attributeValue("name"));
		//convert sections to Map
		Map<String,String> sections = xmlTemplate.getSections();
		while(notes.hasNext()){
			Element note = (Element)notes.next();
			Assert.assertEquals("Named "+note.getName()+" is not Legitimate ", SECTION, note.getName());
			sections.put(note.attributeValue("name"), note.getText());
		}
		return xmlTemplate;
	}
	
	public static XMLTemplate getXMLTemplate(String xmlMethodName){
		for (XMLTemplate xmlTemplate : xmlTempaltes) {
			if(xmlMethodName.equals(xmlMethodName)){
				return xmlTemplate;
			}
		}
		return new XMLTemplate();
	}
	
}
