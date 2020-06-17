package com.excilys.formation.CDB.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.formation.CDB.mapper.CompanyMapper;
import com.excilys.formation.CDB.model.Company;

import com.excilys.formation.CDB.service.Connexion;

public class CompanyDAO extends DAO<Company> {

	private final String VIEW_ALL_QUERY = "SELECT * FROM computer LIMIT ? OFFSET ? ";
	private final String GET_BY_ID_QUERY = "SELECT * FROM computer WHERE id=?";
	
	private CompanyMapper mapper;

	private ArrayList<Company> companyList;

	public CompanyDAO() {
		super();
		mapper = new CompanyMapper();
		

	}

	@Override
	public ArrayList<Company> getAll(int nbLines, int pageEnCours) {

		ArrayList<Company> companyList = new ArrayList<Company>();

		try {

			Connection conn = Connexion.getConnection();

			PreparedStatement stmt = conn.prepareStatement(VIEW_ALL_QUERY);
			stmt.setInt(1, nbLines);
			stmt.setInt(2, nbLines * pageEnCours);
			System.out.println(stmt.toString());
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				companyList.add(mapper.processResults(resultSet));
			}

			conn.close();
			return companyList;

		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return null;

	}

	@Override
	public Company get(String id) {

		try {
			Connection conn = Connexion.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery(GET_BY_ID_QUERY + "" + id);

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
