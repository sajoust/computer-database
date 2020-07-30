package com.excilys.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.dto.DTOComputer;
import com.excilys.dto.PageDTO;
import com.excilys.service.ComputerService;

@RestController
@RequestMapping("/computers")
public class ComputerRestController {

	
	@Autowired
	private ComputerService computerService;
	
	
	@GetMapping
	public List<DTOComputer> getAll(@RequestBody PageDTO pageDTO){
			
		return computerService.getAll(pageDTO);
	}
	
	@GetMapping("/find/{ID}")
	public DTOComputer findByID(@PathVariable String id) {

		return computerService.get(id);
	}
	
	@GetMapping("/search/{search}")
	public List<DTOComputer> searchComputers(@PathVariable String search, PageDTO pageDTO){
		
		pageDTO.setSearch(search);
		return computerService.getAll(pageDTO);
		
	}
	
	@GetMapping("/order/{order}")
	public List<DTOComputer> orderComputers(@PathVariable String order, PageDTO pageDTO){
		
		pageDTO.setOrder(order);

		return computerService.getAll(pageDTO);
		
	}	
	
	@PostMapping
	@Transactional
	public void createComputer(@RequestBody DTOComputer dtoComputer){
		
		computerService.add(dtoComputer);
	}	
	
	@PutMapping
	@Transactional
	public void editComputer(@RequestBody DTOComputer dtoComputer) {

		computerService.edit(dtoComputer.getId(), dtoComputer);
		
	}	
	
	@DeleteMapping
	@Transactional
	public void deleteComputer(@RequestParam(name = "selection") String selection){
		computerService.delete(selection);
	}
}
