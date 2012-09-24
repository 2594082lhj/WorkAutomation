package com.generate.model;

import java.util.ArrayList;
import java.util.List;

public class MethodTemplate {

	private String name;
	private List<SectionTemplate> setionTemplates = new ArrayList<SectionTemplate>();
	
	public MethodTemplate() {
		super();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public List<SectionTemplate> getSetionTemplates() {
		return setionTemplates;
	}
	public void setSetionTemplates(List<SectionTemplate> setionTemplates) {
		this.setionTemplates = setionTemplates;
	}
	
}
