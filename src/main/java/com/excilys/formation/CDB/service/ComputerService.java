package com.excilys.formation.CDB.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.DTO.PageDTO;
import com.excilys.formation.CDB.mapper.ComputerDTOMapper;
import com.excilys.formation.CDB.model.Computer;
import com.excilys.formation.CDB.persistence.ComputerDAO;

@Service
public class ComputerService {

	private ComputerDAO computerDAO;
	private static Logger logger = LoggerFactory.getLogger(ComputerService.class);

	@Autowired
	public ComputerService(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	
	}

	public List<DTOComputer> getAll(PageDTO pageDto) {

		List<Computer> computerList = computerDAO.getAll(pageDto);
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



	public void add(DTOComputer dtoComputer) {

		try {	
			computerDAO.add(ComputerDTOMapper.dtoToComputer(dtoComputer));
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void delete(String id) {
		computerDAO.deleteComputer(id);
	}

	public void edit(String id, DTOComputer dtoComputer) {

		computerDAO.edit(id, ComputerDTOMapper.dtoToComputer(dtoComputer));

	}

	public int countEntries(String filter) {
		return computerDAO.countEntries(filter);
	}

}
