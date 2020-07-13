package com.excilys.formation.CDB.persistence;

import java.util.List;

import com.excilys.formation.CDB.DTO.PageDTO;
import com.excilys.formation.CDB.model.Page;

public abstract class DAO<T> {
	
	

	public abstract List<T> getAll(PageDTO pageDto);
	
	public abstract T get(String id);
	
	
}
