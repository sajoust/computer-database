package com.excilys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.dto.DTOCompany;
import com.excilys.dto.DTOComputer;
import com.excilys.dto.PageDTO;
import com.excilys.exception.ComputerDateException;
import com.excilys.exception.ComputerNameException;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ValidationComputer;

@Controller
public class EditComputerController {

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	

	@GetMapping(path = "/editComputer")
	public ModelAndView get(PageDTO pageDto, @RequestParam String computerToEditiD, ModelAndView mv) {
		List<DTOCompany> companyDtoList;
		DTOComputer dtoComputer = computerService.get(computerToEditiD);
		mv.addObject("dtoComputer", new DTOComputer());
		mv.setViewName("editComputer");
		companyDtoList = companyService.getAll(pageDto);
		mv.addObject("DTOList", companyDtoList);

		mv.getModel().put("computerToEditiD", computerToEditiD);
		mv.getModel().put("DTOList", companyDtoList);
		mv.getModel().put("computerToEditName", dtoComputer.getName());
		mv.getModel().put("introducedComputerToEdit", dtoComputer.getIntroduced());
		mv.getModel().put("discontinuedComputerToEdit", dtoComputer.getDiscontinued());
		mv.getModel().put("companyIDComputerToEdit", dtoComputer.getDtoCompany().getId());
		mv.getModel().put("companyNameComputerToEdit", dtoComputer.getDtoCompany().getName());
		

		return mv;

	}

	@PostMapping(path = "/editComputer")
	public RedirectView post(@ModelAttribute("dtoComputer") DTOComputer dtoComputer, ModelAndView mv) {

		Map<String, String> errors = isValid(dtoComputer);

		mv.getModel().put("errors", errors);


		if (errors.isEmpty()) {
			try {
				computerService.edit(dtoComputer.getId(), dtoComputer);
			} catch (PersistenceException ePersistenceException) {
	
				ePersistenceException.printStackTrace();
			}
			return new RedirectView("home");

		} else {
			
			return new RedirectView("redirect:/editComputer?computerToEditD=" + dtoComputer.getId());
		}


	}

	private Map<String, String> isValid(DTOComputer dtoComputer) {
		Map<String, String> errors = new HashMap<>();

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
