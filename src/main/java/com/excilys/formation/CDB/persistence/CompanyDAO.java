package com.excilys.formation.CDB.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.CDB.connection.ConnectionHikari;
import com.excilys.formation.CDB.mapper.CompanyMapper;
import com.excilys.formation.CDB.model.Company;
import com.excilys.formation.CDB.service.ConnectionSingleton;

public class CompanyDAO extends DAO<Company> {

	private final String VIEW_ALL_QUERY = "SELECT * FROM company";
	private final String GET_BY_ID_QUERY = "SELECT * FROM company WHERE id=?";

	private ArrayList<Company> companyList;

	public CompanyDAO() {
		super();
	}

	@Override
	public ResultSet getAll(int nbLines, int pageEnCours, String filter) {

		// ArrayList<Company> companyList = new ArrayList<Company>();

		try (Connection conn = ConnectionHikari.getInstance().getConnection()) {

			PreparedStatement stmt = conn.prepareStatement(VIEW_ALL_QUERY);
			ResultSet resultSet = stmt.executeQuery();

			conn.close();
			return resultSet;

		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return null;

	}

	@Override
	public ResultSet get(String id) {

		try (Connection conn = ConnectionHikari.getInstance().getConnection()) {

			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(GET_BY_ID_QUERY + "" + id);

			conn.close();
			return resultSet;


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Company> getCompanyList() {
		return companyList;
	}

	@Override
	public ArrayList<Company> getAll() {
		ArrayList<Company> companyList = new ArrayList<Company>();

		try (Connection conn = ConnectionHikari.getInstance().getConnection()) {

			PreparedStatement stmt = conn.prepareStatement(VIEW_ALL_QUERY);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				companyList.add(CompanyMapper.processResults(resultSet));
			}

			conn.close();
			return companyList;

		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return null;
	}

}
