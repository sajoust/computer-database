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
import org.springframework.web.servlet.view.RedirectView;

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
	private List<DTOCompany> companyDtoList;

	@RequestMapping(path = "/editComputer", method = RequestMethod.GET)
	public ModelAndView get(PageDTO pageDto, @RequestParam String computerToEdit, ModelAndView mv) {

		DTOComputer dtoComputer = computerService.get(computerToEdit);
		mv.addObject("dtoComputer", new DTOComputer());
		mv.setViewName("editComputer");
		companyDtoList = companyService.getAll(pageDto);
		mv.addObject("DTOList", companyDtoList);

		mv.getModel().put("computerToEdit", computerToEdit);
		mv.getModel().put("DTOList", companyDtoList);
		mv.getModel().put("computerToEditName", dtoComputer.getName());
		mv.getModel().put("introducedComputerToEdit", dtoComputer.getIntroduced());
		mv.getModel().put("discontinuedComputerToEdit", dtoComputer.getDiscontinued());
		mv.getModel().put("companyIDComputerToEdit", dtoComputer.getDtoCompany().getId());
		mv.getModel().put("companyNameComputerToEdit", dtoComputer.getDtoCompany().getName());
		

		return mv;

	}

	@RequestMapping(path = "/editComputer", method = RequestMethod.POST)
	public RedirectView post(@ModelAttribute("dtoComputer") DTOComputer dtoComputer, ModelAndView mv) {

		Map<String, String> errors = isValid(dtoComputer);

		mv.getModel().put("errors", errors);
		System.out.println("POST EDIT computer id -----------------" + dtoComputer.getId());
		System.out.println("POST EDIT company id -------------------" + dtoComputer.getDtoCompany().getId());

		if (errors.isEmpty()) {
			computerService.edit(dtoComputer.getId(), dtoComputer);
			return new RedirectView("home");

		} else {
			
			return new RedirectView("redirect:/editComputer?computerToEdit=" + dtoComputer.getId());
		}


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
