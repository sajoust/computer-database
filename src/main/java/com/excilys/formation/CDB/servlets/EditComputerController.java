package com.excilys.formation.CDB.servlets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
public class EditComputerController {
	
	
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	private List<DTOCompany> DTOList;
	
	
	@RequestMapping(path = "/editComputer", method = RequestMethod.GET)
	public ModelAndView get(PageDTO pageDto, @RequestParam String computerToEdit, ModelAndView mv) {

		
		DTOComputer dtoComputer = computerService.get(computerToEdit);
		mv.addObject("dtoComputer", new DTOComputer());
		mv.setViewName("editComputer");
		DTOList = companyService.getAll(pageDto);
		
		mv.getModel().put("DTOList", DTOList);
		mv.getModel().put("computerToEditName", dtoComputer.getName());
		mv.getModel().put("introducedComputerToEdit", dtoComputer.getIntroduced());
		mv.getModel().put("discontinuedComputerToEdit", dtoComputer.getDiscontinued());
		mv.getModel().put("companyIDComputerToEdit", dtoComputer.getDtoCompany().getId());
		mv.getModel().put("companyNameComputerToEdit", dtoComputer.getDtoCompany().getName());
		

		return mv;

	}
	
	
	@RequestMapping(path = "/editComputer", method = RequestMethod.POST)
	public ModelAndView post(@ModelAttribute("dtoComputer") DTOComputer dtoComputer, ModelAndView mv) {

		Map<String, String> errors = isValid(dtoComputer);
		
		mv.getModel().put("errors", errors);
		if (errors.isEmpty()) {
			computerService.edit(dtoComputer.getId(),dtoComputer);
		}
		return mv;
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
