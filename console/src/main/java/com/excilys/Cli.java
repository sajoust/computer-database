package com.excilys;

import java.util.Scanner;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.model.Page;
import com.excilys.service.CompanyService;

@Component
public class Cli{
	
	
	private static Logger logger = LoggerFactory.getLogger(Cli.class);

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

				logger.info("company to delete id ?");
				String idCompany = userEntry.nextLine();
				try {
					companyService.delete(idCompany);
				} catch (PersistenceException persistException) {
					
					persistException.printStackTrace();
					logger.debug("cant access database");
				}
				break;

			default:
				logger.info("wrong entry or unimplemented");
			}


		}

		userEntry.close();
		System.exit(0);

	}

	public static String[] askInfoComputer() {
		Scanner userEntry = new Scanner(System.in);

		logger.info("* Name ?");
		String name = userEntry.nextLine();

		String introduced;
		String discontinued;
		String companyId;

		try {
			logger.info("introduced in ? (YYYY-MM-DD) (optional, press ENTER to ignore)");
			introduced = userEntry.nextLine();

			if (introduced.length() == 0) {
				introduced = null;
			}

			logger.info("discontinued in ? (YYYY-MM-DD) (optional, press ENTER to ignore)");
			discontinued = userEntry.nextLine();

			if (discontinued.length() == 0) {
				discontinued = null;
			}

			logger.info("company's ID ? (optional, press ENTER to ignore)");
			companyId = userEntry.nextLine();

			if (companyId.length() == 0) {
				companyId = null;
			}

			String[] infoComputer = { name, introduced, discontinued, companyId };

			userEntry.close();
			return infoComputer;
		} catch (IllegalArgumentException eArgumentException) {
			eArgumentException.printStackTrace();
			logger.debug("info computer wrong");
		}

		return new String[0];
		

	}

	public static void showMenu() {
		logger.info("What now ?");
		
		logger.info("1 - List computers");
		logger.info("2 - List companies");
		logger.info("3 - Show computer details");
		logger.info("4 - Add a Computer");
		logger.info("5 - Modify a Computer");
		logger.info("6 - Delete a Company");

		logger.info("press x to quit");

	}

}
