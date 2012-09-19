package com.generate.test;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Assert;
import org.junit.Test;

import com.generate.model.XMLTemplate;
import com.generate.util.ParseXMLTemplateHelper;


public class ParseXmlTest {
	
	@SuppressWarnings("unchecked")
	public XMLTemplate readTemplate(File file) throws DocumentException{
		String filePath = file.getPath();
		InputStream templateStream = this.getClass().getClassLoader().getResourceAsStream(filePath) ;
		Assert.assertNotNull("Can't parse ["+ filePath+"]", templateStream);
		
		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		doc = reader.read(templateStream);
		
		Element root = doc.getRootElement();
		Iterator<Element> notes = root.elementIterator();
		XMLTemplate xmlTemplate = new XMLTemplate();
		xmlTemplate.setName(root.attributeValue("name"));
		Map<String,String> sections = xmlTemplate.getSections();
		while(notes.hasNext()){
			Element note = (Element)notes.next();
			Assert.assertEquals("Named "+note.getName()+" is not Legitimate ", "section", note.getName());
			sections.put(note.attributeValue("name"), note.getText());
		}
		return xmlTemplate;
	}
	
	public void getAllFiles (File file){
		File[] files = null;
		
		files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if(files[i].isDirectory()){
				getAllFiles(files[i]);
			}else{
				System.out.println("File:"+files[i].getPath());
			}
		}
	
	}
	
	@Test
	public void testReader(){
		String filePath = "com/generate/method/template/pageQuery.xml";
		try {
			XMLTemplate xmlTemplate =  readTemplate(new File(filePath));
			Map<String,String> sections = xmlTemplate.getSections();
			for (Map.Entry<String, String> entry : sections.entrySet()) {
				System.out.println("Key:"+entry.getKey());
				System.out.println("value:"+entry.getValue());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAllFiles(){
		String dir = "com/generate/template/";
		String path = "D:/Workspaces/WorkConcise/bin/com/generate/template/";
		System.out.println(System.getProperty("user.dir"));
		
		getAllFiles(new File(path));
	}
	
	@Test
	public void testparseXMLTemplateHelper() {
		try {
			ParseXMLTemplateHelper.getInstance().init();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	
}
