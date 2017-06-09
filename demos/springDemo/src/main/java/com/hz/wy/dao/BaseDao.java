package com.hz.wy.dao;

import java.io.Serializable;

public interface BaseDao<T extends Serializable> {

	public int insert(T t);
	
	public int update(T t);
	
	public int delete(Long id);
	
	public T get(Long id);

}