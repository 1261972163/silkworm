package com.jengine.java.util;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionTest {

	@Test
	public void test() {
		List<CollectionTestObject> list = new ArrayList<CollectionTestObject>();
		for(int i=10; i>0; i--) {
			CollectionTestObject user = new CollectionTestObject();
			user.setName("test" + i);
			user.setAge(i);
			user.setSex(true);
			list.add(user);
		}
		
		if(list!=null && list.size()>0) {
	        Collections.sort(list, new Comparator<CollectionTestObject>() {
	            @Override
	            public int compare(CollectionTestObject o1, CollectionTestObject o2) {
                    return o1.getName().compareTo(o2.getName());
	            }
	        });
        }
		
		for(CollectionTestObject user : list) {
			System.out.println(user.getName());
		}
	}
}

class CollectionTestObject {
	private String name;
	private Boolean sex;
	private int age;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getSex() {
		return sex;
	}
	public void setSex(Boolean sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
