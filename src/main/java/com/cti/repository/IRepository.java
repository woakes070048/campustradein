package com.cti.repository;


public interface IRepository<T> {
	public void save(T object) throws Exception;
}
