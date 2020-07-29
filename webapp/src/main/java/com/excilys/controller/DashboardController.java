package com.excilys.controller;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.dto.DTOComputer;
import com.excilys.dto.PageDTO;
import com.excilys.service.ComputerService;

@Controller
@RequestMapping("/home")
public class DashboardController {

	@Autowired
	private ComputerService computerService;
	private List<DTOComputer> computerDTOList;

	@GetMapping
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
	
	@PostMapping
	public String deleteComputer(@RequestParam(name = "selection") String selection ) throws PersistenceException, SQLException {
		String results[] = selection.split(",");
		System.out.println(results);
		for(String str : results) {
			computerService.delete(str);
		}
		return "redirect:/home";
	}

}
