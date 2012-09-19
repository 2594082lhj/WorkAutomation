package com.generate.util;

import org.junit.Assert;

public class StringUtils {
	
	private static final String SELECTORMODEL = "$[*]";
	
	/**
	 * Comma Separated �������
	 * ��StringArrayת������","������ַ���
	 * @param stringArray
	 * @return
	 */
	public static String toCommaSeparated(String[] stringArray){
		StringBuffer string = new StringBuffer();
		for (int i = 0; i < stringArray.length; i++) {
			if(i == 0){
				string.append("\""+stringArray[i]+"\"");
				continue;
			}
			string.append(",\""+stringArray[i]+"\"");
		}
		return string.toString();
	}
	
	/**
	 * ��hql�и��ݲ������ҵ���Ӧ������key
	 * ��������hql�е�key��������Ҫһ��
	 * @param parameterName
	 * @return
	 */
	public static String getConditionKey(String hql,String parameterName){
		if(hql == null || hql.equals("")){
			hql = null;
			Assert.assertNotNull("Error: Parameter�����е�hqlΪ��", hql);
		}
		String[] baseHql = hql.split(" ");
		for (String key : baseHql) {
			if(key.contains(parameterName)){
				return key;
			}else{
				return baseHql[baseHql.length-1]+"."+parameterName;
			}
		}
		Assert.assertNotNull("Error: hql�в�����"+parameterName, parameterName);
		return null;
	}
	
	/**
	 * array not null and have value
	 * @param value
	 * @return
	 */
	public static boolean isNullOrBlank(String[] value){
		if(value == null || value.length == 0 ){
			return true;
		}
		return false;
	}
	
	public static boolean isNotNullOrBlank(String[] value){
		return !isNotNullOrBlank(value);
	}
	
	/**
	 * string is not null or blank
	 * @param vlaue
	 * @return
	 */
	public static boolean isNullOrBlank(String value){
		if(value == null || "".equalsIgnoreCase(value) || value.length() == 0 ){
			return true;
		}
		return false;
	}
	
	/**
	 * ����ĸ���д
	 * @param value
	 * @return
	 */
	public static String toUppercaseFirstLetter(String value){
		if(isNullOrBlank(value)){
			return null;
		}
		return new StringBuffer()
		.append(Character.toUpperCase(value.charAt(0)))
		.append(value.substring(1, value.length()))
		.toString();
		
	}
	
	/**
	 * ����ĸ��Сд
	 * @param value
	 * @return
	 */
	public static String toLowerCaseFirstLetter(String value){
		if(isNullOrBlank(value)){
			return null;
		}
		return new StringBuffer()
		.append(Character.toLowerCase(value.charAt(0)))
		.append(value.substring(1, value.length()))
		.toString();
	}
	
	public static boolean isNotNullOrBlank(String value){
		return !isNullOrBlank(value);
	}
	
	public static void main(String[] args) {
		System.out.println(toLowerCaseFirstLetter("value"));
	}
	
}
