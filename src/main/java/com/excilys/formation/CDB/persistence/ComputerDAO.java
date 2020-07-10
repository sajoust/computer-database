package com.excilys.formation.CDB.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.connection.ConnectionHikari;
import com.excilys.formation.CDB.mapper.ComputerDAOMapper;
import com.excilys.formation.CDB.model.Computer;
import com.excilys.formation.CDB.model.Page;

@Component
public class ComputerDAO extends DAO<Computer> {

	private static final String VIEW_ALL_QUERY = "SELECT computer.id,computer.name,introduced,discontinued,company_id,company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id=company.id ";
	private static final String GET_BY_ID_QUERY = "SELECT computer.id,computer.name,introduced,discontinued,company_id, company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.id=:id";
	private static final String ADD_QUERY = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (:name,:introduced,:discontinued,:companyId)";
	private static final String DELETE_QUERY = "DELETE FROM computer WHERE id=:id";
	private static final String UPDATE_QUERY = "UPDATE computer SET name = :name , introduced = :introduced , discontinued = :discontinued , company_id = :companyId WHERE id = :id";
	private static final String COUNT_QUERY = "SELECT COUNT(computer.id) FROM computer LEFT JOIN company ON computer.company_id=company.id ";
	private static final String GET_LAST_QUERY = "SELECT computer.id,computer.name,introduced,discontinued,company_id, company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id=company.id ORDER BY ID DESC LIMIT 1";

	private ConnectionHikari connectionHikari;

	@Autowired
	public ComputerDAO(ConnectionHikari connectionHikari) {
		super();
		this.connectionHikari = connectionHikari;

	}

	@Override
	public List<Computer> getAll(Page page) {

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(connectionHikari.getDataSource());

		List<Computer> computerList = new ArrayList<>();

		StringBuilder sb = new StringBuilder();
		sb.append(VIEW_ALL_QUERY);

		sb.append(doFilter(page.getFilter()));
		sb.append(doOrder(page.getOrder()));

		sb.append(" LIMIT " + page.getNbLines());
		sb.append(" OFFSET " + page.getNbLines() * (page.getPageToDisplay() - 1));

		computerList = vJdbcTemplate.query(sb.toString(), new ComputerDAOMapper());

		return computerList;

	}

	/**
	 * Ajoute un ordinateur a la BDD
	 * 
	 * @param computerToAdd
	 * @return affiche l'ordinateur entré
	 * @throws SQLException
	 */
	public void add(DTOComputer dtoComputer) throws SQLException {

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate((connectionHikari.getDataSource()));
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("name", dtoComputer.getName(), Types.VARCHAR);
		vParams.addValue("introduced", dtoComputer.getIntroduced(), Types.DATE);
		vParams.addValue("discontinued", dtoComputer.getDiscontinued(), Types.DATE);
		String companyId = dtoComputer.getDtoCompany().getId();
		if (companyId.equals("0")) {
			companyId = null;
		}
		vParams.addValue("companyId", companyId, Types.BIGINT);

		vJdbcTemplate.update(ADD_QUERY, vParams);

	}

	@Override
	public Computer get(String id) {

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate((connectionHikari.getDataSource()));
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("id", id);
		return vJdbcTemplate.query(GET_BY_ID_QUERY, vParams, new ComputerDAOMapper()).get(0);
		
	}

	public Computer getLast() {

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(connectionHikari.getDataSource());

		return vJdbcTemplate.query(GET_LAST_QUERY, new ComputerDAOMapper()).get(0);
	}

	public void deleteComputer(String id) {
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate((connectionHikari.getDataSource()));
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("id", id);
		vJdbcTemplate.update(DELETE_QUERY, vParams);
	}

	public void edit(String id, DTOComputer dtoComputer) {
		
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate((connectionHikari.getDataSource()));
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("name", dtoComputer.getName());
		vParams.addValue("introduced", dtoComputer.getIntroduced());
		vParams.addValue("discontinued", dtoComputer.getDiscontinued());
		String companyId = dtoComputer.getDtoCompany().getId();
		if (companyId.equals("0")) {
			companyId = null;
		}
		vParams.addValue("companyId", companyId, Types.BIGINT);
		vParams.addValue("id", id);
		vJdbcTemplate.update(UPDATE_QUERY, vParams);

	}

	public int countEntries(String filter) {

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate((connectionHikari.getDataSource()));
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		
		if (!"".equals(filter)) {
			vParams.addValue("filter", filter);
		}
		

		return vJdbcTemplate.queryForObject(COUNT_QUERY, vParams, Integer.class);
	}

	public String doFilter(String filter) {

		if (!filter.equals("")) {
			return (" WHERE computer.name LIKE '%" + filter + "%' or company.name LIKE '%" + filter + "%'");
		}
		return "";

	}

	public String doOrder(String order) {

		if (!order.equals("")) {
			String[] arrayOrder = order.split("-");
			return " ORDER BY " + arrayOrder[0] + " " + arrayOrder[1];

		}
		return "";
	}

}
