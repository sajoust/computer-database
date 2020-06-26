package com.excilys.formation.CDB.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.formation.CDB.mapper.ComputerMapper;
import com.excilys.formation.CDB.model.Computer;
import com.excilys.formation.CDB.persistence.ComputerDAO;

public class ComputerService {

	
	//h2 = BDD en memoire brut
	private static ComputerDAO computerDAO;
	
	
	//prend un computer et renvoie string
	
	private ComputerService() {
		computerDAO = new ComputerDAO();
	}
	
	private static class ComputerServiceHolder {
		private final static ComputerService instance = new ComputerService();
	}
	
	public static ComputerService getInstance() {
		return ComputerServiceHolder.instance;
	}

	public List<Computer> getAll(int nbLines, int pageEnCours) {
//		List<String> strList = new ArrayList<String>();
//		List<Computer> computerList = computerDAO.getAll(nbLines,pageEnCours);
//		for (Computer computer : computerList) {
//			strList.add(computer.toString());
//		}
//		return strList;
		
		return computerDAO.getAll(nbLines,pageEnCours);
	}
	
	public String get(String id) {
				
		return computerDAO.get(id).toString();
		
	}
	
	public Computer getByName(String name) {
		return computerDAO.getByName(name);
	}
	
	
	public void add(String[] infoComputer) {
				
		try {
			computerDAO.add(ComputerMapper.stringTabToComputer(infoComputer));
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
