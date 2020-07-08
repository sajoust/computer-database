package com.excilys.formation.CDB.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PageCompany {


	private int pageEnCours = 0;
	private List<String> results;


	public PageCompany() {
		super();
		
		results = new ArrayList<String>();
	}

	public void loadPage() {

		Scanner pageNav = new Scanner(System.in);
		
		String choix = "";
		while (!choix.equals("x")) {
			
			for (String string : results) {
				System.out.println(string);
			}
			menu();
			choix = pageNav.nextLine();
			switch (choix) {
			case "z":
				pageEnCours++;
				results.clear();
				//results = companyService.getAll(nbLines, pageEnCours);
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
				//results = companyService.getAll(nbLines, pageEnCours);
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
