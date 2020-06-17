package com.excilys.formation.CDB.ui;

import java.sql.Date;

import java.util.Scanner;


import com.excilys.formation.CDB.model.Computer;
import com.excilys.formation.CDB.service.ComputerService;

public class CLI_UI {


	private static ComputerService computerService;
	
	
	private final static int NB_LINES=20;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		computerService = new ComputerService();
		
		Scanner userEntry = new Scanner(System.in);
		String choix ="";
		while(!choix.equals("x")) {
			showMenu();
			choix = userEntry.nextLine();
			
			switch (choix) {
			case "1":
				PageComputer pageComputer = new PageComputer(NB_LINES);
				pageComputer.loadPage(); //to do truc avec count pour avoir nb pages total
			break;

			case "2":
				PageCompany pageCompany = new PageCompany(NB_LINES);
				pageCompany.loadPage();

				break;

			case "3":
				
				System.out.println("computer's id ?");
				String id = userEntry.nextLine();
				String result = computerService.get(id);
				System.out.println(result);

				break;

			case "4":
				Computer computerToAdd = askInfoComputer();
				if (computerToAdd != null) {
					System.out.println("Computer added");


				} else {
					System.out.println("erreur creation");
				}

				break;

			case "5":
				System.out.println("computer's ID ?");		
				String computerToUpdateId = userEntry.nextLine();
				Computer computerToUpdate = askInfoComputer();
				if (computerToUpdate!=null) {
					System.out.println("Computer added: ");
					System.out.println();
					System.out.println(computerService.update(computerToUpdateId, computerToUpdate));
				}else {
					System.out.println("erreur de mise a jour");
				}


				break;
			case "6":
				System.out.println("computer to delete ID ?");
				Computer deletedComputer = computerService.delete(userEntry.nextLine());
				System.out.println("deleted computer: ");
				System.out.println(deletedComputer.toString());
				break;

			default:
				System.out.println("wrong entry or unimplemented");
			}
			System.out.println();System.out.println();
		}

		userEntry.close();
		System.exit(0);

	}

	public static Computer askInfoComputer() {
		Scanner userEntry = new Scanner(System.in);

		System.out.println("* Name ?");
		String name = userEntry.nextLine();

		String introduced;
		String discontinued;
		String company_ID;

		try {
			System.out.println("introduced in ? (YYYY-MM-DD) (optional, press ENTER to ignore)");
			introduced = userEntry.nextLine();
			Date dIntroduced;
			if (introduced.length() == 0) {
				dIntroduced = null;
			} else {
				dIntroduced = Date.valueOf(introduced);
			}

			System.out.println("discontinued in ? (YYYY-MM-DD) (optional, press ENTER to ignore)");
			discontinued = userEntry.nextLine();

			Date dDiscontinued;
			if (discontinued.length() == 0) {
				dDiscontinued = null;
			} else {
				dDiscontinued = Date.valueOf(discontinued);
			}

			System.out.println("company's ID ? (optional, press ENTER to ignore)");
			company_ID = userEntry.nextLine();
			Long lCompany_id;
			if (company_ID.length() == 0) {
				lCompany_id = null;
			} else {
				lCompany_id = Long.parseLong(company_ID);
			}
			userEntry.close();
			return new Computer(name, dIntroduced, dDiscontinued, lCompany_id);
		} catch (Exception e) {
			// TODO Auto-generated catch block

			System.err.println("pas bieng");
		}

		return null;

		// Computer ajouter = new Computer(name, introduced, discontinued, company_ID);

	}

	public static void showMenu() {
		System.out.println("What now ?");
		System.out.println();
		System.out.println("1 - List computers");
		System.out.println("2 - List companies");
		System.out.println("3 - Show computer details");
		System.out.println("4 - Create a computer");
		System.out.println("5 - Update a computer");
		System.out.println("6 - Delete a computer");
		
		System.out.println();
		System.out.println("press x to quit");

	}

}
