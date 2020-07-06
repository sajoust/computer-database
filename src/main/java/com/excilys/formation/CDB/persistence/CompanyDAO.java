package com.excilys.formation.CDB.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.formation.CDB.connection.ConnectionHikari;
import com.excilys.formation.CDB.mapper.CompanyMapper;
import com.excilys.formation.CDB.model.Company;

public class CompanyDAO extends DAO<Company> {

	private  final String DELETE_COMPANY_QUERY = "DELETE FROM company WHERE id=?";
	private final String DELETE_COMPUTERS_QUERY = "DELETE FROM computer WHERE company_id=?";
	private final String VIEW_ALL_QUERY = "SELECT * FROM company";
	private final String GET_BY_ID_QUERY = "SELECT * FROM company WHERE id=?";

	private ArrayList<Company> companyList;

	public CompanyDAO() {
		super();
	}

	@Override
	public ResultSet getAll(int nbLines, int pageEnCours, String filter, String order) {

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
	
	public void delete(String id) {
		
		try(Connection conn = ConnectionHikari.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			PreparedStatement deleteComputers = conn.prepareStatement(DELETE_COMPUTERS_QUERY);
			deleteComputers.setString(1, id);
			deleteComputers.execute();
			
			PreparedStatement deleteCompany = conn.prepareStatement(DELETE_COMPANY_QUERY);
			deleteCompany.setString(1, id);
			deleteCompany.execute();
			
			
			conn.commit();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
