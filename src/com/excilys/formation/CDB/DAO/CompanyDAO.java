package com.excilys.formation.CDB.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import com.excilys.formation.CDB.model.Company;
import com.excilys.formation.CDB.service.Connexion;

public class CompanyDAO extends DAO<Company> {

	private final String VIEW_ALL_QUERY = "SELECT * FROM company";
	private final String GET_BY_ID_QUERY="SELECT * FROM company WHERE id=";


	private ArrayList<Company> companyList;
	
	public CompanyDAO() {
		super();
		companyList = new ArrayList<Company>();
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

				Company c = new Company(ID, name);

				companyList.add(c);
			}
			conn.close();

		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

	}

	@Override
	public Company getByID(long ID) {

		try {
			Connection conn = Connexion.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery(GET_BY_ID_QUERY + "" + ID);

			for (Company company : companyList) {
				if (company.getId() == results.getLong(1)) {
					conn.close();
					return company;
				}
			}

			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public ArrayList<Company> getCompanyList() {
		return companyList;
	}
	
	

}
