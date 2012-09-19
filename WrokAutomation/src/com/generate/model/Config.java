package com.generate.model;

import java.io.Serializable;

public class Config implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String note;		//ע��
	private String name;		//��������
	private String parameter;	//�������
	private String mapping;		//����
	
	public Config() {
		super();
	}
	
	public Config(String name, String parameter, String mapping) {
		super();
		this.name = name;
		this.parameter = parameter;
		this.mapping = mapping;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getMapping() {
		return mapping;
	}
	public void setMapping(String mapping) {
		this.mapping = mapping;
	}
	
}
