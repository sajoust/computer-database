package com.excilys.formation.CDB.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.CDB.DTO.DTOCompany;
import com.excilys.formation.CDB.DTO.DTOComputer;
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

	public List<DTOComputer> getAll(int nbLines, int pageEnCours, String filter) {
		List<Computer> computerList = computerDAO.getAll(nbLines, pageEnCours, filter);
		List<DTOComputer> dtoComputerList=new ArrayList<>();
		List<DTOCompany> dtoCompanyList = CompanyService.getInstance().getAll();
		for (Computer computer : computerList) {
			dtoComputerList.add(ComputerMapper.ComputerToDTO(computer));
		}

		
		
		return dtoComputerList;
	}
	
	public String get(String id) {
				
		return computerDAO.get(id).toString();
		
	}
	
	public Computer getByName(String name) {
		return computerDAO.getByName(name);
	}
	
	
	public void add(DTOComputer dtoComputer) {
				
		try {
			computerDAO.add(dtoComputer);
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
