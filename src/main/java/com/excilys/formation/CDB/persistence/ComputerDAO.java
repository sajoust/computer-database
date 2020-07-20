package com.excilys.formation.CDB.persistence;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.CDB.DTO.PageDTO;
import com.excilys.formation.CDB.connection.ConnectionHikari;
import com.excilys.formation.CDB.mapper.ComputerDAOMapper;
import com.excilys.formation.CDB.model.Computer;
import com.excilys.formation.CDB.model.QCompany;
import com.excilys.formation.CDB.model.QComputer;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class ComputerDAO extends DAO<Computer> {

	@SuppressWarnings("unused")
	private static final String VIEW_ALL_QUERY = "SELECT computer.id,computer.name,introduced,discontinued,company_id,company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id=company.id ";
	private static final String GET_BY_ID_QUERY = "SELECT computer.id,computer.name,introduced,discontinued,company_id, company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.id=:id";
	private static final String ADD_QUERY = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (:name,:introduced,:discontinued,:companyId)";
	private static final String DELETE_QUERY = "DELETE FROM computer WHERE id=:id";
	private static final String UPDATE_QUERY = "UPDATE computer SET name = :name , introduced = :introduced , discontinued = :discontinued , company_id = :companyId WHERE id = :id";
	private static final String COUNT_QUERY = "SELECT COUNT(computer.name) FROM computer LEFT JOIN company ON computer.company_id=company.id ";
	private static final String GET_LAST_QUERY = "SELECT computer.id,computer.name,introduced,discontinued,company_id, company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id=company.id ORDER BY ID DESC LIMIT 1";

	@PersistenceContext
	private EntityManager entityManager;

	private ConnectionHikari connectionHikari;

	@Autowired
	public ComputerDAO(ConnectionHikari connectionHikari) {
		super();
		this.connectionHikari = connectionHikari;

	}

	@Override
	public List<Computer> getAll(PageDTO pageDto) {

		List<Computer> computerList = new ArrayList<>();

		JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;

		computerList = query.from(computer)
				.leftJoin(company).on(computer.company.id.eq(company.id))
				.where(computer.name.contains(pageDto.getSearch()).or(company.name.contains(pageDto.getSearch())))
				.orderBy(doOrder(pageDto))
				.limit(pageDto.getComputerPerPage())
				.offset((pageDto.getPageToDisplay()-1)*pageDto.getComputerPerPage())
				.fetch();

		return computerList;

	}
//	public void add(Computer computerToAdd) throws SQLException {
//
//		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate((connectionHikari.getDataSource()));
//		MapSqlParameterSource vParams = new MapSqlParameterSource();
//		vParams.addValue("name", computerToAdd.getName(), Types.VARCHAR);
//		vParams.addValue("introduced", computerToAdd.getIntroduced(), Types.DATE);
//		vParams.addValue("discontinued", computerToAdd.getDiscontinued(), Types.DATE);
//		Long companyId = computerToAdd.getCompany().getId();
//		if (companyId == 0L) {
//			companyId = null;
//		}
//		vParams.addValue("companyId", companyId, Types.BIGINT);
//
//		vJdbcTemplate.update(ADD_QUERY, vParams);
//
//	}
	
	public void add(Computer computerToAdd) throws SQLException{
		
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		queryFactory.update(path);//TODO: reprendre ici
		
		
		
		
	}

	private OrderSpecifier<?> doOrder(PageDTO pageDTO) {
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		

		String[] order = pageDTO.getOrder().split("-");

		switch (order[0]) {
		case "computer.name":
			if (order[1].equals("ASC")) {
				return computer.name.asc();
			}
			return computer.name.desc();

		case "introduced":
			if (order[1].equals("ASC")) {
				return computer.introduced.asc().nullsLast();
			}
			return computer.introduced.desc().nullsLast();
		case "discontinued":
			if (order[1].equals("ASC")) {
				return computer.discontinued.asc().nullsLast();
			}
			return computer.discontinued.desc().nullsLast();
		case "company_name":
			if (order[1].equals("ASC")) {
				return company.name.asc().nullsLast();
			}
			return company.name.desc().nullsLast();
		default:
			
			return computer.id.asc();
	
		}



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

//	public String doOrder(String order) {
//
//		if (!"".equals(order)) {
//			String[] arrayOrder = order.split("-");
//			return " ORDER BY " + arrayOrder[0] + " " + arrayOrder[1];
//
//		}
//		return "";
//	}

}
