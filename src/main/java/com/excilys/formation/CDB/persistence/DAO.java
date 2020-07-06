package com.excilys.formation.CDB.persistence;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class DAO<T> {
	
	

	public abstract ResultSet getAll(int nbLines,int pageEnCours, String filter, String order);
	
	public abstract List<T> getAll();

	public abstract ResultSet get(String id);
	
	
}
