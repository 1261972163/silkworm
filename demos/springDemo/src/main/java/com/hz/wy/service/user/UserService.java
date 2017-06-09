package com.hz.wy.service.user;

import com.hz.wy.model.User;

public interface UserService {
	void add(User user);
	void update(User user);
	void delete(Long id);
	User get(Long id);
	User get(String userName);
}
