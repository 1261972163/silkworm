package com.jengine.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

	public static void main(String[] args) {
		Date date = new Date();
		String  s= simpleDateFormat.format(date);
		System.out.println(s);
		long l = Long.parseLong(s);
		System.out.println(l);
	}

}
