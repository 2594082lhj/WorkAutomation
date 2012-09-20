package com.generate.test;

import org.junit.Test;

import com.generate.method.section.BaseMethod;
import com.generate.method.section.impl.BaseMethodImpl;
import com.generate.model.config.BaseConfig;

public class BaseMethodTest {

	// get info of Caller
	public static void getCaller() {
		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		for (int i = 0; i < stack.length; i++) {
			StackTraceElement s = stack[i];
			System.out.format(" ClassName:%d\t%s\n", i, s.getClassName());
			System.out.format("MethodName:%d\t%s\n", i, s.getMethodName());
			System.out.format("  FileName:%d\t%s\n", i, s.getFileName());
			System.out.format("LineNumber:%d\t%s\n\n", i, s.getLineNumber());
		}
	}
	
	@Test
	public void testInstanceBaseMethodImpl(){
		BaseMethod baseMethod = new BaseMethodImpl();
		baseMethod.getClass();
	}

	@Test
	public void testGetCaller() {
		getCaller();
	}
	
	@Test
	public void testBaseMethod(){
		BaseConfig config = new BaseConfig();
		config.setNote("·½·¨×¢ÊÍ");
		config.setName("test");
		config.setParameter("id,name,age");
		config.setMapping("returnMapping");
		BaseMethodImpl b = new BaseMethodImpl();
		StringBuffer buffer = new StringBuffer();
		buffer
		.append(b.definedMethod(config))
		.append(b.definedParameters(config))
		.append(b.returnMapping(config));
		
		System.out.println(buffer.toString());
	}
	
	

}
