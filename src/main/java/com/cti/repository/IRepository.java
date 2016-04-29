package com.cti.repository;


public interface IRepository<T> {
	public boolean save(T object);
}
