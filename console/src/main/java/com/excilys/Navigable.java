package com.excilys;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.dto.DTOComputer;
import com.excilys.model.Page;
import com.excilys.service.ComputerService;


@Component
public class Navigable {

	private Page page;
	private int nbComputers;
	private int nbPage;
	
	private static Logger logger = LoggerFactory.getLogger(Navigable.class);
	
	@Autowired
	private ComputerService computerService;
	
	public Navigable(Page page) {
		this.page=page;
		nbComputers = computerService.countEntries("");
		nbPage = nbComputers/page.getComputerPerPage();
	}
	
	public void navigate() {
		Scanner userEntry = new Scanner(System.in);
		String choix="";
		while(!choix.equals("x")) {
			
			choix = userEntry.nextLine();
			
			switch (choix) {
			case "a":
				page.setPageToDisplay(Math.max(1, page.getPageToDisplay()-1));
				break;
				
			case "z":
				page.setPageToDisplay(Math.min(nbPage, page.getPageToDisplay()+1));
				break;

			default:
				break;
			}
			
			List<DTOComputer> computersList = computerService.getAll(); 
			for (DTOComputer dtoComputer : computersList) {
				logger.info(dtoComputer.toString());
			}
		}
		userEntry.close();
	}
	
	
}
