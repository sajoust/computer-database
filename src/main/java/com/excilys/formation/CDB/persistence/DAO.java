package com.excilys.formation.CDB.persistence;

import java.util.List;

import com.excilys.formation.CDB.model.Page;

public abstract class DAO<T> {
	
	

	public abstract List<T> getAll(Page page);
	
	public abstract T get(String id);
	
	
}
