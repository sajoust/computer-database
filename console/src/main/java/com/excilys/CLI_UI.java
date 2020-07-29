package com.excilys;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.dto.DTOComputer;
import com.excilys.model.Page;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

@Component
public class CLI_UI {


	@Autowired
	private static CompanyService companyService;
	



	public static void main(String[] args) {

		Scanner userEntry = new Scanner(System.in);
		String choix = "";
		while (!choix.equals("x")) {

			showMenu();

			choix = userEntry.nextLine();

			switch (choix) {
			case "1":
				
				
				Navigable nav = new Navigable(new Page());
				nav.navigate();
				
				break;
				
				
			case "2":

				break;

			case "3":

				break;
			case "4":

				System.out.println("company to delete id ?");
				String idCompany = userEntry.nextLine();
				try {
					companyService.delete(idCompany);
				} catch (PersistenceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				System.out.println("wrong entry or unimplemented");
			}

			System.out.println();
			System.out.println();

		}

		userEntry.close();
		System.exit(0);

	}

	public static String[] askInfoComputer() {
		Scanner userEntry = new Scanner(System.in);

		System.out.println("* Name ?");
		String name = userEntry.nextLine();

		String introduced;
		String discontinued;
		String company_id;

		try {
			System.out.println("introduced in ? (YYYY-MM-DD) (optional, press ENTER to ignore)");
			introduced = userEntry.nextLine();

			if (introduced.length() == 0) {
				introduced = null;
			}

			System.out.println("discontinued in ? (YYYY-MM-DD) (optional, press ENTER to ignore)");
			discontinued = userEntry.nextLine();

			if (discontinued.length() == 0) {
				discontinued = null;
			}

			System.out.println("company's ID ? (optional, press ENTER to ignore)");
			company_id = userEntry.nextLine();

			if (company_id.length() == 0) {
				company_id = null;
			}

			String[] infoComputer = { name, introduced, discontinued, company_id };

			userEntry.close();
			return infoComputer;
		} catch (Exception e) {
			// TODO Auto-generated catch block

			System.err.println("erreur de saisie de données");
		}

		return null;

	}

	public static void showMenu() {
		System.out.println("What now ?");
		System.out.println();
		System.out.println("1 - List computers");
		System.out.println("2 - List companies");
		System.out.println("3 - Show computer details");
		System.out.println("4 - Add a Computer");
		System.out.println("5 - Modify a Computer");
		System.out.println("6 - Delete a Company");

		System.out.println();
		System.out.println("press x to quit");

	}

}
