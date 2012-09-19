package com.generate.model;

import java.io.Serializable;

public class Config implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String note;		//注释
	private String name;		//方法名称
	private String parameter;	//请求参数
	private String mapping;		//返回
	
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
