package com.cti.dao;

public interface Dao<T> {
	public void save(T model);
	public T findByKey(int id);
	public T findByKey(String id);
}
