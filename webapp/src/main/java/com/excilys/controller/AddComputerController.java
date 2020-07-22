package com.excilys.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.dto.DTOCompany;
import com.excilys.dto.DTOComputer;
import com.excilys.dto.PageDTO;
import com.excilys.exception.ComputerDateException;
import com.excilys.exception.ComputerNameException;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ValidationComputer;

@Controller
public class AddComputerController {

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	
	
	private List<DTOCompany> DTOList;

	@RequestMapping(path = "/addComputer", method = RequestMethod.GET)
	public ModelAndView get(PageDTO pageDto) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("dtoComputer", new DTOComputer());
		mv.addObject("page", pageDto);
		mv.setViewName("addComputer");
		DTOList = companyService.getAll(pageDto);
		mv.getModel().put("DTOList", DTOList);
		mv.getModel().get("errors");
		return mv;

	}

	@RequestMapping(path = "/addComputer", method = RequestMethod.POST)
	public String post(@ModelAttribute("dtoComputer") @Validated DTOComputer dtoComputer, ModelAndView mv) {

		Map<String, String> errors = new HashMap<String, String>();
		mv.getModel().put("errors", errors);
		errors = isValid(dtoComputer);

		if (errors.isEmpty()) {
			try {
				computerService.add(dtoComputer);
			} catch (PersistenceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "redirect:addComputer";
	}

	private Map<String, String> isValid(DTOComputer dtoComputer) {
		Map<String, String> errors = new HashMap<String, String>();

		try {
			ValidationComputer.nameValidation(dtoComputer);
			ValidationComputer.dateValidation(dtoComputer);
		} catch (ComputerNameException exceptionName) {
			errors.put("computerName", exceptionName.getMessage());
		} catch (ComputerDateException exceptionDate) {
			errors.put("discontinued", exceptionDate.getMessage());
		}

		return errors;
	}

}
