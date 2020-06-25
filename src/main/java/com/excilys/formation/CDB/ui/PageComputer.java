package com.excilys.formation.CDB.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.excilys.formation.CDB.model.Computer;
import com.excilys.formation.CDB.service.ComputerService;

public class PageComputer {

	private int nbLines;
	private int pageEnCours = 1;
	private List<Computer> results;
	private ComputerService computerService;
	private int nbPages;

	public PageComputer(int nbLines) {
		super();
		this.nbLines = nbLines;
		results = new ArrayList<Computer>();
		computerService = new ComputerService();
		nbPages = (computerService.countEntries()/nbLines)+1; //=6
	}

	public void loadPage() {

		Scanner pageNav = new Scanner(System.in);
		String choix = "";
		while (!choix.equals("x")) {
			results = computerService.getAll(nbLines, pageEnCours);
			for (Computer computer : results) {
				System.out.println(computer.toString());
			}
			
			
			switch (choix) {
			case "z":
				pageEnCours++;
				if (pageEnCours>nbPages) {
					pageEnCours=nbPages;
				}
				results.clear();
				results = computerService.getAll(nbLines, pageEnCours);
				for (Computer computer : results) {
					System.out.println(computer.toString());
				}
				break;
			case "a":
				pageEnCours--;
				if (pageEnCours<1) {
					pageEnCours=1;
				}
				results.clear();
				results = computerService.getAll(nbLines, pageEnCours);
				for (Computer computer : results) {
					System.out.println(computer.toString());
				}
				break;
			default:
				break;
			}
			menu();
			choix = pageNav.nextLine();

		}


	}

	public void menu() {

		System.out.println("page " + (pageEnCours) +"/"+nbPages+ "	next: z		previous: a 	quit: x");
	}

}
