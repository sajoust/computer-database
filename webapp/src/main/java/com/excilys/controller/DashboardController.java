package com.excilys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.DTO.PageDTO;
import com.excilys.formation.CDB.service.ComputerService;

@Controller
public class DashboardController {

	@Autowired
	private ComputerService computerService;
	private List<DTOComputer> computerDTOList;

	@RequestMapping(path = "/home", method = RequestMethod.GET)
	public ModelAndView get(PageDTO pageDto) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("Dashboard");
		
		
		
		int nbEntries = computerService.countEntries(pageDto.getSearch());
		int nbPages = (nbEntries / pageDto.getComputerPerPage()) + 1;
		pageDto.setPageToDisplay(Math.min(pageDto.getPageToDisplay(), nbPages));
		computerDTOList = computerService.getAll(pageDto);
		
		
		mv.getModel().put("search", pageDto.getSearch());
		mv.getModel().put("nbPages", nbPages);
		mv.getModel().put("pageToDisplay", pageDto.getPageToDisplay());
		mv.getModel().put("computerPerPage", pageDto.getComputerPerPage());
		mv.getModel().put("DTOList", computerDTOList);
		mv.getModel().put("entriesCount", nbEntries);
		mv.getModel().put("order", pageDto.getOrder());

		return mv;

	}

}
