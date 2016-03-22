package com.jengine.java.util;

import com.jengine.feature.serialize.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionTest {

	@Test
	public void test() {
		List<User> list = new ArrayList<User>();
		for(int i=10; i>0; i--) {
			User user = new User();
			user.setName("test" + i);
			user.setAge(i);
			user.setSex(true);
			list.add(user);
		}
		
		if(list!=null && list.size()>0) {
	        Collections.sort(list, new Comparator<User>() {
	            @Override
	            public int compare(User o1, User o2) {
                    return o1.getName().compareTo(o2.getName());
	            }
	        });
        }
		
		for(User user : list) {
			System.out.println(user.getName());
		}
	}
}
