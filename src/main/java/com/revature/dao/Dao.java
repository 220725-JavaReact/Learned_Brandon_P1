package com.revature.dao;

import java.util.ArrayList;

public interface Dao<T> {

	public void addInstance(T instance);
	public ArrayList<T> getAllInstance();
	public T getByAttribute(String attribute);
	public T getById(int id);
	
}
