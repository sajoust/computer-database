package com.excilys.formation.CDB.persistence;

import java.util.ArrayList;

public abstract class DAO<T> {
	
	

	public abstract ArrayList<T> getAll(int nbLines,int pageEnCours);

	public abstract T get(String id);
	
	
}
