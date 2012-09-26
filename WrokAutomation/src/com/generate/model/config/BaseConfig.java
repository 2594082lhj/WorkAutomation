package com.generate.model.config;


public class BaseConfig extends Config{
	
	private String note;		
	private String methodName;		
	private String parameter;	
	private String mapping;		
	private String hqlColunmName;
	private String headerName;
	
	public BaseConfig() {
		super();
	}
	
	public BaseConfig(String name, String parameter, String mapping) {
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

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
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

	public String getHqlColunmName() {
		return hqlColunmName;
	}

	public void setHqlColunmName(String hqlColunmName) {
		this.hqlColunmName = hqlColunmName;
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}
	
}
