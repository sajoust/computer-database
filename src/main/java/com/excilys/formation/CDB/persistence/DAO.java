package com.excilys.formation.CDB.persistence;

import java.util.List;

public abstract class DAO<T> {
	
	

	public abstract List<T> getAll(int nbLines,int pageEnCours, String filter, String order);
	
	public abstract T get(String id);
	
	
}
