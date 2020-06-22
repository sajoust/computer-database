package com.excilys.formation.CDB.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.formation.CDB.model.Computer;
import com.excilys.formation.CDB.persistence.ComputerDAO;

public class ComputerService {

	
	
	private static ComputerDAO computerDAO;
	
	
	//prend un computer et renvoie string
	
	public ComputerService() {
		computerDAO = new ComputerDAO();
	}
	

	public ArrayList<String> getAll(int nbLines, int pageEnCours) {
		ArrayList<String> strList = new ArrayList<String>();
		ArrayList<Computer> computerList = computerDAO.getAll(nbLines,pageEnCours);
		for (Computer computer : computerList) {
			strList.add(computer.toString());
		}
		return strList;
	}
	
	public String get(String id) {
				
		return computerDAO.get(id).toString();
		
	}
	
	
	public void add(String[] infoComputer) {
				
		try {
			computerDAO.add(infoComputer);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Computer delete(String id) {
		return computerDAO.deleteComputer(id);
	}

	public Computer update(String id, String[] computerToUpdateInfo) {
				
		return computerDAO.update(id, computerToUpdateInfo);
		
	}
	
	public int countEntries() {
		return computerDAO.countEntries();
	}
	

	

}
