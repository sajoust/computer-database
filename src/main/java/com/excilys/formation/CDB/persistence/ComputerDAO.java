package com.excilys.formation.CDB.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.connection.ConnectionHikari;
import com.excilys.formation.CDB.mapper.ComputerMapper;
import com.excilys.formation.CDB.model.Computer;
import com.excilys.formation.CDB.service.ConnectionSingleton;

public class ComputerDAO extends DAO<Computer> {

	private final String VIEW_ALL_QUERY = "SELECT computer.id,computer.name,introduced,discontinued,company_id,company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id=company.id ";
	private final String GET_BY_ID_QUERY = "SELECT computer.id,computer.name,introduced,discontinued,company_id, company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.id=?";
	private final String GET_BY_NAME_QUERY = "SELECT * FROM computer WHERE name=?";
	private final String ADD_QUERY = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private final String DELETE_QUERY = "DELETE FROM computer WHERE id=?";
	private final String UPDATE_QUERY = "UPDATE computer SET name = ? , introduced = ? , discontinued = ? , company_id = ? WHERE id = ?";
	private final String COUNT_QUERY = "SELECT COUNT(computer.id) FROM computer LEFT JOIN company ON computer.company_id=company.id ";

	// private ComputerMapper ComputerMapper;

	public ComputerDAO() {
		super();
		// ComputerMapper = new ComputerMapper();

	}

	@Override
	public ResultSet getAll(int nbLines, int pageEnCours, String filter) {

		ArrayList<Computer> computerList = new ArrayList<Computer>();

		try (Connection conn = ConnectionHikari.getInstance().getConnection()) {

			StringBuilder sb = new StringBuilder();
			sb.append(VIEW_ALL_QUERY);

			if (filter != "no_filter" && filter != "") {
				sb.append(" WHERE computer.name LIKE '%" + filter + "%' or company.name LIKE '%" + filter + "%'");
			}

			sb.append(" LIMIT " + nbLines);
			sb.append(" OFFSET " + nbLines * (pageEnCours - 1));
			//sb.append(" ORDER BY company.name, introduced DESC");
			PreparedStatement stmt = conn.prepareStatement(sb.toString());
			System.out.println("statement " + stmt.toString());
			ResultSet resultSet = stmt.executeQuery();

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

		// try(Connection conn = ConnectionSingleton.getInstance().getConnection()) {
		try (Connection conn = ConnectionHikari.getInstance().getConnection()) {

			PreparedStatement stmt = conn.prepareStatement(ADD_QUERY);
			stmt.setString(1, dtoComputer.getName());
			stmt.setString(2, dtoComputer.getIntroduced());
			stmt.setString(3, dtoComputer.getDiscontinued());
			// stmt.setString(4, dtoComputer.getCompanyID());
			if (dtoComputer.getDtoCompany().getId().equals("0")) {
				stmt.setNull(4, Types.BIGINT);
			} else {
				stmt.setString(4, dtoComputer.getDtoCompany().getId());
			}

			stmt.execute();
			System.out.println("computer added");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public ResultSet get(String id) {

		try (Connection conn = ConnectionHikari.getInstance().getConnection()) {

			PreparedStatement stmt = conn.prepareStatement(GET_BY_ID_QUERY);
			stmt.setString(1, id);
			ResultSet resultSet = stmt.executeQuery();
			conn.close();
			return resultSet;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Computer getByName(String name) {

		try (Connection conn = ConnectionHikari.getInstance().getConnection()) {
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
		try (Connection conn = ConnectionHikari.getInstance().getConnection()) {

			// Connection conn = Connexion.getConnection();
			PreparedStatement stmt = conn.prepareStatement(DELETE_QUERY);
			stmt.setString(1, id);
			System.out.println("STATEMENT IN DELETE DAO   " + stmt.toString());
			stmt.execute();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void edit(String id, DTOComputer dtoComputer) {

		try (Connection conn = ConnectionHikari.getInstance().getConnection()) {

			PreparedStatement stmt = conn.prepareStatement(UPDATE_QUERY);

			stmt.setString(1, dtoComputer.getName());
			stmt.setObject(2, dtoComputer.getIntroduced());
			stmt.setObject(3, dtoComputer.getDiscontinued());
			stmt.setObject(4, dtoComputer.getDtoCompany().getId());

			stmt.setString(5, id);
			stmt.execute();
			conn.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public int countEntries(String filter) {

		try (Connection conn = ConnectionHikari.getInstance().getConnection()) {

			StringBuilder sb = new StringBuilder(COUNT_QUERY);
			// PreparedStatement stmt = conn.prepareStatement(COUNT_QUERY);
			// ResultSet resultSet = stmt.executeQuery();
			int entriesCount = 0;

			if (filter != "no_filter" && filter != "") {
				sb.append(" WHERE computer.name LIKE '%" + filter + "%' or company.name LIKE '%" + filter + "%'");
			}

			ResultSet resultSet = conn.prepareStatement(sb.toString()).executeQuery();
			while (resultSet.next()) {
				entriesCount = ComputerMapper.countResults(resultSet);
			}
			conn.close();
			return entriesCount;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public ArrayList<Computer> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
