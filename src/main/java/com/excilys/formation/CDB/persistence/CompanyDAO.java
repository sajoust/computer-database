package com.excilys.formation.CDB.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.formation.CDB.connection.ConnectionHikari;
import com.excilys.formation.CDB.mapper.CompanyDAOMapper;
import com.excilys.formation.CDB.model.Company;
import com.excilys.formation.CDB.model.Page;

@Component
public class CompanyDAO extends DAO<Company> {

	private static final String DELETE_COMPANY_QUERY = "DELETE FROM company WHERE id=?";
	private static final String DELETE_COMPUTERS_QUERY = "DELETE FROM computer WHERE company_id=?";
	private static final String VIEW_ALL_QUERY = "SELECT id,name FROM company";
	private static final String GET_BY_ID_QUERY = "SELECT id,name FROM company WHERE id=?";

	
	
	private ConnectionHikari connectionHikari;

	public CompanyDAO(ConnectionHikari connectionHikari) {
		this.connectionHikari=connectionHikari;
	}

	@Override
	public List<Company> getAll(Page page) {

		List<Company> companyList = new ArrayList<Company>();

		try (Connection conn = connectionHikari.getConnection()) {

			PreparedStatement stmt = conn.prepareStatement(VIEW_ALL_QUERY);
			ResultSet resultSet = stmt.executeQuery();

			conn.close();
			while (resultSet.next()) {
				companyList.add(CompanyDAOMapper.resultSetToCompany(resultSet));
			}
			return companyList;

		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return null;

	}

	@Override
	public Company get(String id) {

		try (Connection conn = connectionHikari.getConnection()) {

			PreparedStatement stmt = conn.prepareStatement(GET_BY_ID_QUERY);
			stmt.setString(1, id);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				Company c = CompanyDAOMapper.resultSetToCompany(resultSet);
				return c;
			}
		


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	
	public void delete(String id) {
		
		try(Connection conn = connectionHikari.getConnection()) {
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
