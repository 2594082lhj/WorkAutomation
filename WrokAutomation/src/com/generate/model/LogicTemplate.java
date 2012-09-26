package com.generate.model;

import java.util.ArrayList;
import java.util.List;

public class LogicTemplate {
	
	private String name;
	private List<String> sections = new ArrayList<String>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getSections() {
		return sections;
	}
	public void setSections(List<String> sections) {
		this.sections = sections;
	}
	
}
