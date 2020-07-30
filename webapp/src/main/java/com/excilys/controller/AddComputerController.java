package com.excilys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

	@GetMapping(path = "/addComputer")
	public ModelAndView get(PageDTO pageDto) {
		List<DTOCompany> dtoList;
		ModelAndView mv = new ModelAndView();
		mv.addObject("dtoComputer", new DTOComputer());
		mv.addObject("page", pageDto);
		mv.setViewName("addComputer");
		dtoList = companyService.getAll(pageDto);
		mv.getModel().put("DTOList", dtoList);
		mv.getModel().get("errors");
		return mv;

	}

	@PostMapping(path = "/addComputer")
	public String post(@ModelAttribute("dtoComputer") @Validated DTOComputer dtoComputer, ModelAndView mv) {

		Map<String, String> errors = new HashMap<>();
		mv.getModel().put("errors", errors);
		errors = isValid(dtoComputer);

		if (errors.isEmpty()) {

			computerService.add(dtoComputer);

		}

		return "redirect:addComputer";
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
