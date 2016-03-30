package com.jengine.java.util;

import com.jengine.feature.serialize.XMLTestObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionTest {

	@Test
	public void test() {
		List<XMLTestObject> list = new ArrayList<XMLTestObject>();
		for(int i=10; i>0; i--) {
			XMLTestObject user = new XMLTestObject();
			user.setName("test" + i);
			user.setAge(i);
			user.setSex(true);
			list.add(user);
		}
		
		if(list!=null && list.size()>0) {
	        Collections.sort(list, new Comparator<XMLTestObject>() {
	            @Override
	            public int compare(XMLTestObject o1, XMLTestObject o2) {
                    return o1.getName().compareTo(o2.getName());
	            }
	        });
        }
		
		for(XMLTestObject user : list) {
			System.out.println(user.getName());
		}
	}
}
