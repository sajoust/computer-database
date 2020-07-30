package com.excilys.dao;

import java.util.List;

import com.excilys.model.Page;




public interface DAO<T> {
	
	

	public abstract List<T> getAll(Page currentPage);
	
	public abstract T get(String id);
	
	
}
