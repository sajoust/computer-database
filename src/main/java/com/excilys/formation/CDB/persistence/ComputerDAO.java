package com.excilys.formation.CDB.persistence;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.excilys.formation.CDB.DTO.PageDTO;
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
	private static final String COUNT_QUERY = "SELECT COUNT(computer.name) FROM computer LEFT JOIN company ON computer.company_id=company.id ";
	private static final String GET_LAST_QUERY = "SELECT computer.id,computer.name,introduced,discontinued,company_id, company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id=company.id ORDER BY ID DESC LIMIT 1";

	private ConnectionHikari connectionHikari;

	@Autowired
	public ComputerDAO(ConnectionHikari connectionHikari) {
		super();
		this.connectionHikari = connectionHikari;

	}

	@Override
	public List<Computer> getAll(PageDTO pageDto) {

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(connectionHikari.getDataSource());
		StringBuilder querySQL = new StringBuilder(VIEW_ALL_QUERY);
		List<Computer> computerList = new ArrayList<>();
		querySQL.append(doFilter(pageDto.getSearch()));
		querySQL.append(doOrder(pageDto.getOrder()));
		querySQL.append(" LIMIT " + pageDto.getComputerPerPage() + " OFFSET "
				+ (pageDto.getPageToDisplay() - 1) * pageDto.getComputerPerPage());
		computerList = vJdbcTemplate.query(querySQL.toString(), new ComputerDAOMapper());
		System.out.println("QUERY VIEW ALL --------------------------  " + querySQL.toString());
		return computerList;

	}

	/**
	 * Ajoute un ordinateur a la BDD
	 * 
	 * @param computerToAdd
	 * @throws SQLException
	 */
	public void add(Computer computerToAdd) throws SQLException {

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate((connectionHikari.getDataSource()));
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("name", computerToAdd.getName(), Types.VARCHAR);
		vParams.addValue("introduced", computerToAdd.getIntroduced(), Types.DATE);
		vParams.addValue("discontinued", computerToAdd.getDiscontinued(), Types.DATE);
		Long companyId = computerToAdd.getCompany().getId();
		if (companyId == 0L) {
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

	public int deleteComputer(String id) {
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate((connectionHikari.getDataSource()));
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("id", id);
		return vJdbcTemplate.update(DELETE_QUERY, vParams);
	}

	public void edit(String id, Computer computerToEdit) {

		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate((connectionHikari.getDataSource()));
		MapSqlParameterSource vParams = new MapSqlParameterSource();
		vParams.addValue("name", computerToEdit.getName());
		vParams.addValue("introduced", computerToEdit.getIntroduced());
		vParams.addValue("discontinued", computerToEdit.getDiscontinued());
		Long companyId = computerToEdit.getCompany().getId();
		if (companyId == 0L) {
			companyId = null;

		}
		vParams.addValue("companyId", companyId, Types.BIGINT);
		vParams.addValue("id", id);
		vJdbcTemplate.update(UPDATE_QUERY, vParams);

	}

	public int countEntries(String filter) {

		JdbcTemplate vJdbcTemplate = new JdbcTemplate(connectionHikari.getDataSource());
		StringBuilder querySQL = new StringBuilder(COUNT_QUERY);
		querySQL.append(doFilter(filter));

		return vJdbcTemplate.queryForObject(querySQL.toString(), Integer.class);

	}

	public String doFilter(String filter) {

		if (!"".equals(filter)) {
			return (" WHERE computer.name LIKE '%" + filter + "%' OR company.name LIKE '%" + filter + "%'");
		}
		return "";

	}

	public String doOrder(String order) {

		if (!"".equals(order)) {
			String[] arrayOrder = order.split("-");
			return " ORDER BY " + arrayOrder[0] + " " + arrayOrder[1];

		}
		return "";
	}

}
