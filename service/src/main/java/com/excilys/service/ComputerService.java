package com.excilys.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.dao.ComputerDAO;
import com.excilys.dto.DTOComputer;
import com.excilys.dto.PageDTO;
import com.excilys.mapper.ComputerDTOMapper;
import com.excilys.mapper.PageMapper;
import com.excilys.model.Computer;
import com.excilys.model.Page;

@Service
public class ComputerService {

	private ComputerDAO computerDAO;
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ComputerService.class);

	@Autowired
	public ComputerService(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	
	}

	public List<DTOComputer> getAll(PageDTO pageDto) {

		Page currentPage = PageMapper.dtoToPage(pageDto);
		List<Computer> computerList = computerDAO.getAll(currentPage);
		List<DTOComputer> dtoComputerList = new ArrayList<>();

		for (Computer computer : computerList) {

			dtoComputerList.add(ComputerDTOMapper.ComputerToDTO(computer));
		}
		return dtoComputerList;
	}
	
	public List<DTOComputer> getAll(){
		List<Computer> computerList = computerDAO.getAll();
		List<DTOComputer> dtoComputerList = new ArrayList<>();
		
		for (Computer computer : computerList) {

			dtoComputerList.add(ComputerDTOMapper.ComputerToDTO(computer));
		}
		return dtoComputerList;
	}

	public DTOComputer get(String id) {

		Computer computer = computerDAO.get(id);
		
		DTOComputer dtoComputer = ComputerDTOMapper.ComputerToDTO(computer);

		return dtoComputer;

	}



	public void add(DTOComputer dtoComputer) throws PersistenceException, SQLException {

		
			computerDAO.add(ComputerDTOMapper.dtoToComputer(dtoComputer));

	}

	public void delete(String id) throws PersistenceException, SQLException {
		computerDAO.deleteComputer(id);
	}

	public void edit(String id, DTOComputer dtoComputer) throws PersistenceException, SQLException {

		computerDAO.edit(id, ComputerDTOMapper.dtoToComputer(dtoComputer));

	}

	public int countEntries(String filter) {
		return computerDAO.countEntries(filter);
	}

}
