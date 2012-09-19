package com.generate.model;

import java.util.HashMap;
import java.util.Map;

public class XMLTemplate {
	
	private String name;
	
	private Map<String,String> sections = new HashMap<String,String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getSections() {
		return sections;
	}

	public void setSections(Map<String, String> sections) {
		this.sections = sections;
	}
	
}
