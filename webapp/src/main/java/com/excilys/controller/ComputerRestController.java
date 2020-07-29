package com.excilys.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.dto.DTOComputer;
import com.excilys.service.ComputerService;

@RestController
@RequestMapping("/homeRest")
public class ComputerRestController {

	
	@Autowired
	private ComputerService computerService;
	
	
	@GetMapping
	public List<DTOComputer> getAll(){
		List<DTOComputer> listComputers = computerService.getAll();
		listComputers = computerService.getAll();
		return listComputers;
	}
	
	@GetMapping("/find/{ID}")
	public DTOComputer findByID(@PathVariable String ID) {

		@SuppressWarnings("unused")
		Optional<DTOComputer> Optionalcomputer = Optional.empty();		
		DTOComputer dtoComputer = computerService.get(ID);
		
		return dtoComputer;
	}
	
	@PostMapping("/add")
	@Transactional
	public void createComputer(@RequestBody DTOComputer dtoComputer) throws PersistenceException, SQLException {
		System.out.println("COMPUTER TO ADD -----------------"+ dtoComputer);
		computerService.add(dtoComputer);
	}
	
	@PostMapping("/edit")
	@Transactional
	public void editComputer(@RequestBody DTOComputer dtoComputer) throws PersistenceException, SQLException {
		System.out.println("COMPUTER TO edit -----------------"+ dtoComputer);
		computerService.edit(dtoComputer.getId(), dtoComputer);
	}
	
	
	
	@PostMapping
	@Transactional
	public void deleteComputer() throws PersistenceException, SQLException {
		computerService.delete("1");
	}
}
