package com.excilys.formation.CDB.persistence;

import java.sql.ResultSet;
import java.util.ArrayList;

public abstract class DAO<T> {
	
	

	public abstract ResultSet getAll(int nbLines,int pageEnCours, String filter);
	
	public abstract ArrayList<T> getAll();

	public abstract ResultSet get(String id);
	
	
}
