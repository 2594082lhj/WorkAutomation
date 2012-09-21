package com.generate.util;

import org.junit.Test;

import com.generate.model.config.BaseConfig;

public class TestBaseAssignmentSection {
	
	@Test
	public void test (){
		BaseConfig config = new BaseConfig();
		config.setMapping("test");
		config.setName(" name for test");
		config.setNote(" note for test ");
		config.setParameter("id,name,age,gender");
		
		double d = 3434.3423432434234324;
		
		System.out.println(d);
	}
	
}
