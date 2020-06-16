package com.excilys.formation.CDB.DAO;



public abstract class DAO<T> {
	
	

	public abstract void getAll();

	public abstract T getByID(long ID);
	
	
}
