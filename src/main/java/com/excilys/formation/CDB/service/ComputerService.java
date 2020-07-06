package com.excilys.formation.CDB.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.formation.CDB.DTO.DTOCompany;
import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.mapper.ComputerMapper;
import com.excilys.formation.CDB.model.Computer;
import com.excilys.formation.CDB.persistence.ComputerDAO;

public class ComputerService {

	// h2 = BDD en memoire brut
	private static ComputerDAO computerDAO;
	
	@Autowired
	private ComputerMapper computerMapper;
	//ApplicationContext context;
	// prend un computer et renvoie string
	
	
	
	private ComputerService() {
		computerDAO = new ComputerDAO();
	}

	private static class ComputerServiceHolder {
		private final static ComputerService instance = new ComputerService();
	}

	public static ComputerService getInstance() {
		return ComputerServiceHolder.instance;
	}

	public List<DTOComputer> getAll(int nbLines, int pageEnCours, String filter, String order) {
		System.out.println("SERVICE GET ALL FILTER:   " + filter);
		ResultSet resultSet = computerDAO.getAll(nbLines, pageEnCours, filter, order);
		List<Computer> computerList = new ArrayList<>();
		List<DTOComputer> dtoComputerList = new ArrayList<>();
		List<DTOCompany> dtoCompanyList = CompanyService.getInstance().getAll();

		try {
			while (resultSet.next()) {
				computerList.add(ComputerMapper.processResults(resultSet));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Computer computer : computerList) {
			dtoComputerList.add(ComputerMapper.ComputerToDTO(computer));
		}

		return dtoComputerList;
	}

	public DTOComputer get(String id) {

		ResultSet resultSet = computerDAO.get(id);
		Computer c = null;
		try {
			while (resultSet.next()) {
				System.out.println("RESULT SET DANS GET DE SERVICE  " + resultSet.getString(1));
				c = ComputerMapper.processResults(resultSet);
			}
		} catch (SQLException e) {
			System.err.println("ERREUR DANS GET DE SERVICE ");
			e.printStackTrace();
		}
		// System.out.println("VOICI LE COMPUTER FAIT DANS SERVICE FONCTION GET " +
		// c.toString());

		DTOComputer dtoComputer = ComputerMapper.ComputerToDTO(c);
		return dtoComputer;

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

	public void delete(String id) {
		computerDAO.deleteComputer(id);
	}

	public void edit(String id, DTOComputer dtoComputer) {

		computerDAO.edit(id, dtoComputer);

	}

	public int countEntries(String filter) {
		return computerDAO.countEntries(filter);
	}

}
