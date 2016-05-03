package com.cti.repository;


public interface IRepository<T> {
	public void save(T object) throws Exception;
	
	public void update(T object) throws Exception;
	
	public void delete(T object) throws Exception;
}
