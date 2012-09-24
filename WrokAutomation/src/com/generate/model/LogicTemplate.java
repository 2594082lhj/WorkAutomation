package com.generate.model;

import java.util.ArrayList;
import java.util.List;

public class LogicTemplate {
	
	private String name;
	private List<String> section = new ArrayList<String>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getSection() {
		return section;
	}
	public void setSection(List<String> section) {
		this.section = section;
	}
	
}
