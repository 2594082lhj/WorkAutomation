package com.generate.method.section;

import com.generate.model.config.BaseConfig;


public interface BaseMethod {
	
	public String definedMethod(BaseConfig config);
	
	public String definedParameters(BaseConfig config);
	
	public String definedRequestParameters(BaseConfig config);
	
	public String returnNull();
	
	public String returnMapping(BaseConfig config);
}
