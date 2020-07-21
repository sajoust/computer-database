package com.excilys.formation.CDB.servlets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.CDB.DTO.DTOCompany;
import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.DTO.PageDTO;
import com.excilys.formation.CDB.exceptions.ComputerDateException;
import com.excilys.formation.CDB.exceptions.ComputerNameException;
import com.excilys.formation.CDB.service.CompanyService;
import com.excilys.formation.CDB.service.ComputerService;
import com.excilys.formation.CDB.validation.ValidationComputer;

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
			computerService.add(dtoComputer);
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
