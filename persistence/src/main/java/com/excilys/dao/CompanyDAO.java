package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import com.excilys.config.*;
import com.excilys.dto.PageDTO;
import com.excilys.mapper.CompanyDAOMapper;
import com.excilys.model.Company;


@Component
public class CompanyDAO extends DAO<Company> {

	private static final String DELETE_COMPANY_QUERY = "DELETE FROM company WHERE id=?";
	private static final String DELETE_COMPUTERS_QUERY = "DELETE FROM computer WHERE company_id=?";
	private static final String VIEW_ALL_QUERY = "SELECT id,name FROM company";
	private static final String GET_BY_ID_QUERY = "SELECT id,name FROM company WHERE id=:id";

	
	
	private ConnectionHikari connectionHikari;

	public CompanyDAO(ConnectionHikari connectionHikari) {
		this.connectionHikari=connectionHikari;
	}

	@Override
	public List<Company> getAll(PageDTO pageDto) {

		List<Company> companyList = new ArrayList<Company>();

		JdbcTemplate vJdbcTemplate = new JdbcTemplate((connectionHikari.getDataSource()));
		companyList = vJdbcTemplate.query(VIEW_ALL_QUERY, new CompanyDAOMapper());
		return companyList;

	}

	@Override
	public Company get(String id) {

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(connectionHikari.getDataSource());
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("id", id);
		return vJdbcTemplate.query(GET_BY_ID_QUERY, new CompanyDAOMapper()).get(0);
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
