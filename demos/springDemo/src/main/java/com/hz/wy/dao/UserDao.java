package com.hz.wy.dao;

import com.hz.wy.model.User;

public interface UserDao extends BaseDao<User> {

	User getByUserName(String userName);
}
