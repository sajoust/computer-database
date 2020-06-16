package com.excilys.formation.CDB.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import com.excilys.formation.CDB.DAO.CompanyDAO;
import com.excilys.formation.CDB.DAO.ComputerDAO;
import com.excilys.formation.CDB.model.Company;
import com.excilys.formation.CDB.model.Computer;

public class CLI_UI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		showMenu();
		Scanner userEntry = new Scanner(System.in);
		
		ComputerDAO computerDAO = new ComputerDAO();
		CompanyDAO companyDAO = new CompanyDAO();
		
		switch (userEntry.nextInt()) {
		case 1:
			ArrayList<Computer> computerList = computerDAO.getComputerList();
			for (Computer c : computerList) {
				System.out.println(c.toString());
			}
			break;
			
		case 2:
			ArrayList<Company> companyList = companyDAO.getCompanyList();
			for (Company c : companyList) {
				System.out.println(c.toString());				
			}
			break;
			
		case 3:
			System.out.println("computer's ID ?");			
			Computer c = computerDAO.getByID(userEntry.nextLong());
			System.out.println(c.toString());
			break;
		

		case 4:
			Computer computerToAdd = askInfoComputer();
			computerDAO.addComputer(computerToAdd);
						
			break;
			
		case 5:
			
			break;
		case 6:
			System.out.println("computer's ID ?");			
			computerDAO.deleteComputer(userEntry.nextLong());
			System.out.println("deleted");
			break;
			
		default:
			System.out.println("wrong entry or unimplemented");
		}
		userEntry.close();
		
	}
	
	public static Computer askInfoComputer() {
		Scanner userEntry = new Scanner(System.in);
		
		System.out.println("* Name ?");
		String name = userEntry.nextLine();
		
		
		System.out.println("introduced in ? (YYYY-MM-DD) (optional, enter x to ignore)");
		String introduced = userEntry.nextLine();
		if ((introduced=="x")||(introduced == "X")) {
			introduced = null;
		}
		System.out.println("discontinued in ? (YYYY-MM-DD) (optional, enter x to ignore)");
		String discontinued = userEntry.nextLine();
		if ((discontinued=="x")||(discontinued == "X")) {
			discontinued = null;
		}
		
		System.out.println("company's ID ? (optional, enter x to ignore)");
		String company_ID = userEntry.nextLine();
		if ((company_ID=="x")||(company_ID == "X")) {
			company_ID = null;
		}
		
		userEntry.close();
		return new Computer(name, LocalDate.parse(introduced), LocalDate.parse(discontinued), Long.parseLong(company_ID));
		
		//Computer ajouter = new Computer(name, introduced, discontinued, company_ID);
		
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

		
		
	}

}
