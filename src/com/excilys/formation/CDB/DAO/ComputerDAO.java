package com.excilys.formation.CDB.DAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import com.excilys.formation.CDB.model.Computer;
import com.excilys.formation.CDB.service.Connexion;

public class ComputerDAO extends DAO<Computer> {

	private final String VIEW_ALL_QUERY = "SELECT * FROM computer";
	private final String GET_BY_ID_QUERY = "SELECT * FROM computer WHERE id=";
	private final String ADD_QUERY = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ";
	private final String DELETE_QUERY = " DELETE FROM computer WHERE id=";
	private ArrayList<Computer> computerList;

	public ComputerDAO() {
		super();
		computerList = new ArrayList<Computer>();
		getAll();

	}

	@Override
	public void getAll() {
		try {

			Connection conn = Connexion.getConnection();

			Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery(VIEW_ALL_QUERY);

			while (results.next()) {

				// create computer
				// add it to the list

				long ID = results.getLong(1);
				String name = results.getString("name");
				LocalDate ldIntroduced = stringToDate(results.getString("introduced"));
				LocalDate ldDiscontinued = stringToDate(results.getString("discontinued"));
				long company_ID = results.getLong("company_id");

				Computer c = new Computer(ID, name, ldIntroduced, ldDiscontinued, company_ID);

				computerList.add(c);
			}
			conn.close();

		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

	}

	public ArrayList<Computer> getComputerList() {
		return computerList;
	}

	@Override
	public Computer getByID(long id) {

		try {
			Connection conn = Connexion.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery(GET_BY_ID_QUERY + "" + id);
			while (results.next()) {
				for (Computer computer : computerList) {
					if (computer.getId() == results.getLong("id")) {
						conn.close();
						return computer;
					}
				}
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public void addComputer(Computer computerToAdd) {

		try {

			Connection conn = Connexion.getConnection();
			Statement stmt = conn.createStatement();

			String query = String.format(ADD_QUERY + " ('%s','%s','%s','%s')", computerToAdd.getName(),
					computerToAdd.getIntroduced(), computerToAdd.getDiscontinued(), computerToAdd.getCompanyID());
			stmt.executeUpdate(query);
			computerList.add(computerToAdd);
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void deleteComputer(long id) {
		try {
			Connection conn = Connexion.getConnection();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(DELETE_QUERY + "" + id);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public LocalDate stringToDate(String sDate) {

		if (sDate == null) {
			return null;
		}
		return LocalDate.parse(sDate.substring(0, 10));

	}

//	StringBuilder sb = new StringBuilder();
//	sb.append("ID: ").append(results.getString("id")).append("\n");
//	sb.append("Name: ").append(results.getString("name")).append("\n");
//	sb.append("Introduced: ").append(results.getString("introduced")).append("\n");
//	sb.append("Discontinued: ").append(results.getString("discontinued")).append("\n");
//	sb.append("Company ID: ").append(results.getString("company_id")).append("\n");
//	
//	System.out.println(sb);

}
