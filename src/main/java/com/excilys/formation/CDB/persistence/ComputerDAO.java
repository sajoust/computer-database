package com.excilys.formation.CDB.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import com.excilys.formation.CDB.mapper.ComputerMapper;
import com.excilys.formation.CDB.model.Computer;
import com.excilys.formation.CDB.service.ConnectionSingleton;

public class ComputerDAO extends DAO<Computer> {

	private final String VIEW_ALL_QUERY = "SELECT * FROM computer LIMIT ? OFFSET ? ";
	private final String GET_BY_ID_QUERY = "SELECT * FROM computer WHERE id=?";
	private final String ADD_QUERY = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private final String DELETE_QUERY = " DELETE FROM computer WHERE id>=?";
	private final String UPDATE_QUERY = "UPDATE computer SET name = ? , introduced = ? , discontinued = ? , company_id = ? WHERE id = ?";
	private final String COUNT_QUERY = "SELECT COUNT(id) FROM computer";
	

	//private ComputerMapper ComputerMapper;

	public ComputerDAO() {
		super();
		//ComputerMapper = new ComputerMapper();

	}

	/**
	 * Ajoute un ordinateur a la BDD
	 * 
	 * @param computerToAdd
	 * @return affiche l'ordinateur entré
	 * @throws SQLException 
	 */
	public void add(String[] computerInfo) throws SQLException {

		

			try(Connection conn = ConnectionSingleton.getInstance().getConnection()) {
				

				PreparedStatement stmt = conn.prepareStatement(ADD_QUERY);
				stmt.setString(1, computerInfo[0]);
				stmt.setObject(2, ComputerMapper.stringToDate(computerInfo[1]));
				stmt.setObject(3, ComputerMapper.stringToDate(computerInfo[2]));
				if (computerInfo[3] == null) {
					stmt.setNull(4, Types.BIGINT);
				} else {
					stmt.setLong(4, Long.parseLong(computerInfo[3]));
				}

				stmt.execute();
				System.out.println("computer added");
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			

	}

	@Override
	public Computer get(String id) {

		try {
			Computer computer = new Computer();
			Connection conn = ConnectionSingleton.getInstance().getConnection();

			//Connection conn = Connexion.getConnection();
			PreparedStatement stmt = conn.prepareStatement(GET_BY_ID_QUERY);
			stmt.setString(1, id);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				computer = ComputerMapper.processResults(resultSet);
			}
			conn.close();
			return computer;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public ArrayList<Computer> getAll(int nbLines, int pageEnCours) {

		ArrayList<Computer> computerList = new ArrayList<Computer>();

		try (Connection conn = ConnectionSingleton.getInstance().getConnection()){
			

			//Connection conn = Connexion.getConnection();

			PreparedStatement stmt = conn.prepareStatement(VIEW_ALL_QUERY);
			stmt.setInt(1, nbLines);
			stmt.setInt(2, nbLines * (pageEnCours - 1));
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				computerList.add(ComputerMapper.processResults(resultSet));
			}

			
			return computerList;

		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return null;

	}

	public Computer deleteComputer(String id) {
		try {
			Connection conn = ConnectionSingleton.getInstance().getConnection();

			//Connection conn = Connexion.getConnection();
			PreparedStatement stmt = conn.prepareStatement(DELETE_QUERY);
			stmt.setString(1, id);
			Computer computerToDelete = get(id);
			stmt.execute();

			conn.close();

			return computerToDelete;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Computer update(String id, String[] computerToUpdateInfo) {

		try {
			//Connection conn = Connexion.getConnection();
			Connection conn = ConnectionSingleton.getInstance().getConnection();

			PreparedStatement stmt = conn.prepareStatement(UPDATE_QUERY);



			stmt.setString(1, computerToUpdateInfo[0]);
			stmt.setObject(2, ComputerMapper.stringToDate(computerToUpdateInfo[1]));
			stmt.setObject(3, ComputerMapper.stringToDate(computerToUpdateInfo[2]));
			if (computerToUpdateInfo[3] == null) {
				stmt.setNull(4, Types.BIGINT);
			} else {
				stmt.setLong(4, Long.parseLong(computerToUpdateInfo[3]));
			}
			stmt.setString(5, id);
			stmt.execute();
			conn.close();

			return get(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public int countEntries() {

		try {
			//Connection conn = Connexion.getConnection();
			Connection conn = ConnectionSingleton.getInstance().getConnection();

			PreparedStatement stmt = conn.prepareStatement(COUNT_QUERY);
			ResultSet resultSet = stmt.executeQuery();
			int entriesCount = 0;

			while (resultSet.next()) {
				entriesCount = ComputerMapper.countResults(resultSet);
			}
			conn.close();
			System.out.println("nb entries = "+entriesCount);
			return entriesCount;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

}
