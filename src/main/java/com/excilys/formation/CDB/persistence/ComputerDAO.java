package com.excilys.formation.CDB.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.connection.ConnectionHikari;
import com.excilys.formation.CDB.mapper.ComputerMapper;
import com.excilys.formation.CDB.model.Computer;


public class ComputerDAO extends DAO<Computer> {

	private final String VIEW_ALL_QUERY = "SELECT computer.id,computer.name,introduced,discontinued,company_id,company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id=company.id ";
	private final String GET_BY_ID_QUERY = "SELECT computer.id,computer.name,introduced,discontinued,company_id, company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.id=?";
	private final String GET_BY_NAME_QUERY = "SELECT * FROM computer WHERE name=?";
	private final String ADD_QUERY = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private final String DELETE_QUERY = "DELETE FROM computer WHERE id=?";
	private final String UPDATE_QUERY = "UPDATE computer SET name = ? , introduced = ? , discontinued = ? , company_id = ? WHERE id = ?";
	private final String COUNT_QUERY = "SELECT COUNT(computer.id) FROM computer LEFT JOIN company ON computer.company_id=company.id ";

	@Autowired
	private ConnectionHikari connectionHikari;

	public ComputerDAO(ConnectionHikari connectionHikari) {
		super();
		this.connectionHikari=connectionHikari;

	}

	@Override
	public ResultSet getAll(int nbLines, int pageToDisplay, String filter, String order) {

		
		//Connection conn = ConnectionHikari.getInstance().getConnection()
		try (Connection conn = connectionHikari.getConnection()) {

			StringBuilder sb = new StringBuilder();
			sb.append(VIEW_ALL_QUERY);
			
			sb.append(doFilter(filter));
			sb.append(doOrder(order));
			
			sb.append(" LIMIT " + nbLines);
			sb.append(" OFFSET " + nbLines * (pageToDisplay-1));
			
			
			PreparedStatement stmt = conn.prepareStatement(sb.toString());
			System.out.println("statement " + stmt.toString());
			ResultSet resultSet = stmt.executeQuery();
			stmt.close();
			return resultSet;

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
	public ResultSet get(String id) {

		try (Connection conn = connectionHikari.getConnection()) {

			PreparedStatement stmt = conn.prepareStatement(GET_BY_ID_QUERY);
			stmt.setString(1, id);
			ResultSet resultSet = stmt.executeQuery();

			return resultSet;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Computer getByName(String name) {

		try (Connection conn = connectionHikari.getConnection()) {
			Computer computer = new Computer();
			PreparedStatement stmt = conn.prepareStatement(GET_BY_NAME_QUERY);
			stmt.setString(1, name);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				computer = ComputerMapper.processResults(resultSet);
			}
			return computer;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;

	}

	public void deleteComputer(String id) {
		try (Connection conn = connectionHikari.getConnection()) {

			// Connection conn = Connexion.getConnection();
			PreparedStatement stmt = conn.prepareStatement(DELETE_QUERY);
			stmt.setString(1, id);
			System.out.println("STATEMENT IN DELETE DAO   " + stmt.toString());
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

			System.out.println("STATEMENT EDIT       "+stmt.toString());
			stmt.setString(5, id);
			
			
			stmt.executeUpdate();
			//conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int countEntries(String filter) {

		try (Connection conn = connectionHikari.getConnection()) {

			StringBuilder sb = new StringBuilder(COUNT_QUERY);
			int entriesCount = 0;

			if (filter != "no_filter" && filter != "#") {
				sb.append(" WHERE computer.name LIKE '%" + filter + "%' or company.name LIKE '%" + filter + "%'");
			}

			ResultSet resultSet = conn.prepareStatement(sb.toString()).executeQuery();
			while (resultSet.next()) {
				entriesCount = ComputerMapper.countResults(resultSet);
			}
			return entriesCount;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public List<Computer> getAll() {
		return null;
	}
	
	public String doFilter (String filter) {
		
		if (!filter.equals("")) {
			 return (" WHERE computer.name LIKE '%" + filter + "%' or company.name LIKE '%" + filter + "%'");
		}
		return "";
		
	}
	
	public String doOrder(String order) {
		
		System.out.println("ORDER AVANT TEST COMPUTER DAO     "+order);
		
		
		if (!order.equals("")) {
			System.out.println("PASSE IF DOORDER DAO");
			String[] arrayOrder = order.split("-");
			return " ORDER BY "+arrayOrder[0]+" "+arrayOrder[1];
			
		}
		return "";
	}

}
