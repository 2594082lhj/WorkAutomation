package com.generate.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Test;

public class TestMarkUtils {

	@Test
	public void testgetLogicMarkName() {
		String source = "lfijelnlgieoir jflseif  $[LOGIC:fsfe] slfije $[logic:fesf] sfljeslfj $[logic:fesfe]lfijelnlgieoir jflseif  $[LOGIC:fsfe] slfije $[logic:fesf] sfljeslfj $[logic:xxx]";
		Assert.assertEquals("xxx", MarkUtils.getLogicMarkName(source));
	}

	@Test
	public void parseUrl() {
		String var = "���ǲ���<a href=http://www.ba*****idu.cn>www.goog[]e.cn</a>����ǲ���������<A href='http://www.google.cn'>www.google.cn</a>��";
		Matcher matcher = null;

		matcher = Pattern.compile("<[aA]\\s*(href=[^>]+)>(.*?)</[aA]>")
				.matcher(var);

		while (matcher != null && matcher.find()) {
			int a = matcher.groupCount();
			while ((a--) > 0) {
				System.out.println(matcher.group(a));
			}
		}
	}

}
