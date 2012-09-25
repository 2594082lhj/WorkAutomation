package com.generate.util;

import junit.framework.Assert;

import org.junit.Test;

public class TestStringUtils {

	@Test
	public void testToCommaSeparated() {
		String [] stringArray = new String[]{"1","2","3","4"};
		String actuals = StringUtils.toCommaSeparated(stringArray);
		String expected = "1,2,3,4";
		Assert.assertEquals(expected , actuals);
	}

	@Test
	public void testIsNullOrBlankStringArray() {
		String[] value = null; 
		boolean actuals = StringUtils.isNullOrBlank(value);
		boolean expected = true;
		Assert.assertEquals(expected , actuals);
	}

	@Test
	public void testIsNullOrBlankString() {
		String value = new String(); 
		boolean actuals = StringUtils.isNullOrBlank(value);
		boolean expected = true;
		Assert.assertEquals(expected , actuals);
	}

	@Test
	public void testToUppercaseFirstLetter() {
		String value = "name";
		String actual = StringUtils.toUppercaseFirstLetter(value);
		String expected = "Name";
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testToLowerCaseFirstLetter() {
		String value = "Name";
		String actual = StringUtils.toLowerCaseFirstLetter(value);
		String expected = "name";
		Assert.assertEquals(expected, actual);
	}

}
