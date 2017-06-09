package com.hz.wy.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hz.wy.dao.UserDao;
import com.hz.wy.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public void add(User user) {
		userDao.insert(user);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public void delete(Long id) {
		userDao.delete(id);
	}

	@Override
	public User get(Long id) {
		return userDao.get(id);
	}

	@Override
	public User get(String userName) {
		return userDao.getByUserName(userName);
	}


}
