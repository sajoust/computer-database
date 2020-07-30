package com.excilys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.dto.DTOCompany;
import com.excilys.dto.PageDTO;
import com.excilys.service.CompanyService;

@RestController
@RequestMapping("/companies")
public class CompanyRestController {

	
	@Autowired
	CompanyService companyService;
	
	@GetMapping
	public List<DTOCompany> getAll(@RequestBody PageDTO pageDTO){
		
		return companyService.getAll(pageDTO);
	}
	
	@GetMapping("/find/{ID}")
	public DTOCompany findById(@PathVariable String id) {
		
		return companyService.get(id);
		
	}
	
	@GetMapping("/search/{search}")
	public List<DTOCompany> searchCompanies(@PathVariable String search, PageDTO pageDTO){
		pageDTO.setSearch(search);
		return companyService.getAll(pageDTO);
	}
	
	@GetMapping("/order/{order}")
	public List<DTOCompany> orderCompanys(@PathVariable String order, PageDTO pageDTO){
		
		pageDTO.setOrder(order);
		

		return companyService.getAll(pageDTO);
		
	}
	
}
