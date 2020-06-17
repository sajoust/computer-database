package com.excilys.formation.CDB.ui;

import java.util.ArrayList;

import java.util.Scanner;
import com.excilys.formation.CDB.service.ComputerService;

public class PageComputer {

	private int nbLines;
	private int pageEnCours = 0;
	private ArrayList<String> results;

	public PageComputer(int nbLines) {
		super();
		this.nbLines = nbLines;
		results = new ArrayList<String>();
	}

	public void loadPage() {

		Scanner pageNav = new Scanner(System.in);
		ComputerService computerService = new ComputerService();
		String choix = "";
		while (!choix.equals("x")) {
			System.out.println("coucou");
			results = computerService.getAll(nbLines, pageEnCours);
			for (String string : results) {
				System.out.println(string);
			}
			menu();
			choix = pageNav.nextLine();
			switch (choix) {
			case "z":
				pageEnCours++;
				results.clear();
				results = computerService.getAll(nbLines, pageEnCours);
				for (String string : results) {
					System.out.println(string);
				}
				break;
			case "a":
				pageEnCours--;
				if (pageEnCours<0) {
					pageEnCours=0;
				}
				results.clear();
				results = computerService.getAll(nbLines, pageEnCours);
				for (String string : results) {
					System.out.println(string);
				}
				break;
			default:
				break;
			}

		}
		pageNav.close();

	}

	public void menu() {

		System.out.println("page " + (pageEnCours + 1) + "	next: z		previous: a 	quit: x");
	}

}
