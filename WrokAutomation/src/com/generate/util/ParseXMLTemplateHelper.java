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
	private List<File>  templateFiles= new ArrayList<File>();
	private List<XMLTemplate> xmlTempaltes = new ArrayList<XMLTemplate>();
	
	public List<XMLTemplate> getXmlTempaltes() {
		return xmlTempaltes;
	}

	private ParseXMLTemplateHelper(){
		super();
	}
	
	public static synchronized ParseXMLTemplateHelper getInstance(){
		if(null == parseXMLTemplateHelper){
			parseXMLTemplateHelper = new ParseXMLTemplateHelper();
		}
		return parseXMLTemplateHelper;
	}
	
	public void init() throws DocumentException{
		getXMLDirectory(new File(DEFAULTDIRECTORY));
		for (File template : templateFiles) {
			XMLTemplate xmlTemplate = convertXMLToModel(template);
			xmlTempaltes.add(xmlTemplate);
			System.out.println("--Method:"+xmlTemplate.getName());
//			for (Map.Entry<String, String> entry : xmlTemplate.getSections().entrySet()) {
//				System.out.println("Key:"+entry.getKey());
//				System.out.println("value:"+entry.getValue());
//			}
		}
	}
	
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
	
	public XMLTemplate convertXMLToModel(File file) throws DocumentException{
		
		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		doc = reader.read(file);
		
		Element root = doc.getRootElement();
		Iterator<Element> notes = root.elementIterator();
		XMLTemplate xmlTemplate = new XMLTemplate();
		xmlTemplate.setName(root.attributeValue("name"));
		Map<String,String> sections = xmlTemplate.getSections();
		while(notes.hasNext()){
			Element note = (Element)notes.next();
			Assert.assertEquals("Named "+note.getName()+" is not Legitimate ", SECTION, note.getName());
			sections.put(note.attributeValue("name"), note.getText());
		}
		return xmlTemplate;
	}
	
}
