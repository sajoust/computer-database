package com.excilys.dao;

import java.util.List;

import com.excilys.dto.PageDTO;




public abstract class DAO<T> {
	
	

	public abstract List<T> getAll(PageDTO pageDto);
	
	public abstract T get(String id);
	
	
}
