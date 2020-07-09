package com.excilys.formation.CDB.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.connection.ConnectionHikari;
import com.excilys.formation.CDB.mapper.ComputerDAOMapper;
import com.excilys.formation.CDB.model.Computer;
import com.excilys.formation.CDB.model.Page;

@Component
public class ComputerDAO extends DAO<Computer> {

	private static final String VIEW_ALL_QUERY = "SELECT computer.id,computer.name,introduced,discontinued,company_id,company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id=company.id ";
	private static final String GET_BY_ID_QUERY = "SELECT computer.id,computer.name,introduced,discontinued,company_id, company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.id=?";
	private static final String ADD_QUERY = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private static final String DELETE_QUERY = "DELETE FROM computer WHERE id=?";
	private static final String UPDATE_QUERY = "UPDATE computer SET name = ? , introduced = ? , discontinued = ? , company_id = ? WHERE id = ?";
	private static final String COUNT_QUERY = "SELECT COUNT(computer.id) FROM computer LEFT JOIN company ON computer.company_id=company.id ";
	private static final String GET_LAST_QUERY = "SELECT computer.id,computer.name,introduced,discontinued,company_id, company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id=company.id ORDER BY ID DESC LIMIT 1";

	
	private ConnectionHikari connectionHikari;

	
	@Autowired
	public ComputerDAO(ConnectionHikari connectionHikari) {
		super();
		this.connectionHikari=connectionHikari;

	}

	@Override
	public List<Computer> getAll(Page page) {
		List<Computer> computerList = new ArrayList<>();
		try (Connection conn = connectionHikari.getConnection()) {

			StringBuilder sb = new StringBuilder();
			sb.append(VIEW_ALL_QUERY);
			
			sb.append(doFilter(page.getFilter()));
			sb.append(doOrder(page.getOrder()));
			
			sb.append(" LIMIT " + page.getNbLines());
			sb.append(" OFFSET " + page.getNbLines() * (page.getPageToDisplay()-1));
			
			
			PreparedStatement stmt = conn.prepareStatement(sb.toString());
			ResultSet resultSet = stmt.executeQuery();
			
			while (resultSet.next()) {
				computerList.add(ComputerDAOMapper.resultSetToComputer(resultSet));
			}
			return computerList;
			

		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		return null;

	}

	/**
	 * Ajoute un ordinateur a la BDD
	 * 
	 * @param computerToAdd
	 * @return affiche l'ordinateur entré
	 * @throws SQLException
	 */
	public void add(DTOComputer dtoComputer) throws SQLException {


		try (Connection conn = connectionHikari.getConnection()) {

			PreparedStatement stmt = conn.prepareStatement(ADD_QUERY);
			stmt.setString(1, dtoComputer.getName());
			stmt.setString(2, dtoComputer.getIntroduced());
			stmt.setString(3, dtoComputer.getDiscontinued());

			if (dtoComputer.getDtoCompany().getId().equals("0")) {
				stmt.setNull(4, Types.BIGINT);
			} else {
				stmt.setString(4, dtoComputer.getDtoCompany().getId());
			}
			
			stmt.execute();
			

		} catch (SQLException e) {

			e.printStackTrace();
		}
		

	}

	@Override
	public Computer get(String id) {

		try (Connection conn = connectionHikari.getConnection()) {

			PreparedStatement stmt = conn.prepareStatement(GET_BY_ID_QUERY);
			stmt.setString(1, id);
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				Computer c = ComputerDAOMapper.resultSetToComputer(resultSet);
				return c;
			}
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public Computer getLast() {

		try (Connection conn = connectionHikari.getConnection()) {

			PreparedStatement stmt = conn.prepareStatement(GET_LAST_QUERY);
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				Computer c = ComputerDAOMapper.resultSetToComputer(resultSet);
				return c;
			}
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	public void deleteComputer(String id) {
		try (Connection conn = connectionHikari.getConnection()) {


			PreparedStatement stmt = conn.prepareStatement(DELETE_QUERY);
			stmt.setString(1, id);
			stmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void edit(String id, DTOComputer dtoComputer) {

		try (Connection conn = connectionHikari.getConnection()) {

			PreparedStatement stmt = conn.prepareStatement(UPDATE_QUERY);

			stmt.setString(1, dtoComputer.getName());
			stmt.setObject(2, dtoComputer.getIntroduced());
			stmt.setObject(3, dtoComputer.getDiscontinued());
			stmt.setObject(4, dtoComputer.getDtoCompany().getId());

			stmt.setLong(5, Long.parseLong(id));
			
			if (dtoComputer.getDtoCompany().getId().equals("0")) {
				stmt.setNull(4, Types.BIGINT);
			} else {
				stmt.setString(4, dtoComputer.getDtoCompany().getId());
			}
			
			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int countEntries(String filter) {

		int entriesCount = 0;
		try (Connection conn = connectionHikari.getConnection()) {

			StringBuilder sb = new StringBuilder(COUNT_QUERY);
			

			if (!"".equals(filter)) {
				sb.append(" WHERE computer.name LIKE '%" + filter + "%' or company.name LIKE '%" + filter + "%'");
			}

			ResultSet resultSet = conn.prepareStatement(sb.toString()).executeQuery();
			while (resultSet.next()) {
				entriesCount = ComputerDAOMapper.countResults(resultSet);
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return entriesCount;
	}

	
	public String doFilter (String filter) {
		
		if (!filter.equals("")) {
			 return (" WHERE computer.name LIKE '%" + filter + "%' or company.name LIKE '%" + filter + "%'");
		}
		return "";
		
	}
	
	public String doOrder(String order) {
		
		
		if (!order.equals("")) {
			String[] arrayOrder = order.split("-");
			return " ORDER BY "+arrayOrder[0]+" "+arrayOrder[1];
			
		}
		return "";
	}

}
