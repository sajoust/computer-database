package com.excilys.formation.CDB.persistence;

import java.sql.*;
import java.util.ArrayList;



import com.excilys.formation.CDB.mapper.ComputerMapper;
import com.excilys.formation.CDB.model.Computer;
import com.excilys.formation.CDB.service.Connexion;

public class ComputerDAO extends DAO<Computer> {

	private final String VIEW_ALL_QUERY = "SELECT * FROM computer LIMIT ? OFFSET ? ";
	private final String GET_BY_ID_QUERY = "SELECT * FROM computer WHERE id=?";
	private final String ADD_QUERY = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private final String DELETE_QUERY = " DELETE FROM computer WHERE id=?";
	private final String UPDATE_QUERY = "UPDATE computer SET name = ? , introduced = ? , discontinued = ? , company_id = ? WHERE id = ?";
	private final  String COUNT_QUERY = "SELECT COUNT(*) FROM computer";
	
	
	
	private ComputerMapper mapper;

	public ComputerDAO() {
		super();
		mapper = new ComputerMapper();
		
		
	}
	/**
	 * Ajoute un ordinateur a la BDD
	 * @param computerToAdd
	 * @return affiche l'ordinateur entré
	 */
	public Computer add(Computer computerToAdd) {
		

		try {

			Connection conn = Connexion.getConnection();
			PreparedStatement stmt = conn.prepareStatement(ADD_QUERY);
			
			Date introduced;
			if (computerToAdd.getIntroduced()!=null) {
				introduced = computerToAdd.getIntroduced();
			}else {
				introduced = null;
			}
			Date discontinued;
			if (computerToAdd.getDiscontinued()!=null) {
				discontinued = computerToAdd.getDiscontinued();
			}else {
				discontinued = null;
			}
			
			stmt.setString(1, computerToAdd.getName());
			stmt.setDate(2, introduced);
			stmt.setDate(3, discontinued);
			if (computerToAdd.getCompanyID()==null) {
				stmt.setNull(4, Types.BIGINT);
			}else {
				stmt.setLong(4, computerToAdd.getCompanyID());
			}

			
			stmt.execute();
			
			conn.close();
			
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
		
	}
	
	@Override
	public Computer get(String id) {

		try {
			Computer computer = new Computer();
			
			Connection conn = Connexion.getConnection();
			PreparedStatement stmt = conn.prepareStatement(GET_BY_ID_QUERY);
			stmt.setString(1, id);
			ResultSet resultSet = stmt.executeQuery();
			
			
			while (resultSet.next()) {
				computer = mapper.processResults(resultSet);
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
		
		ArrayList<Computer> computerList=new ArrayList<Computer>();
		
		try {

			Connection conn = Connexion.getConnection();

			PreparedStatement stmt = conn.prepareStatement(VIEW_ALL_QUERY);
			stmt.setInt(1, nbLines);
			stmt.setInt(2, nbLines*(pageEnCours-1));
			ResultSet resultSet = stmt.executeQuery();
			
			while (resultSet.next()) {
				
				computerList.add(mapper.processResults(resultSet));
			}
			
			
			conn.close();
			return computerList;

		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return null;

	}


	public Computer deleteComputer(String id) {
		try {
			Connection conn = Connexion.getConnection();
			PreparedStatement stmt = conn.prepareStatement(DELETE_QUERY);
			stmt.setString(1, id);
			Computer computerToDelete=get(id);
			stmt.execute();
			
			conn.close();
			
			return computerToDelete;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Computer update(String id, Computer computerToUpdate) {
		
		try {
			Connection conn = Connexion.getConnection();
			PreparedStatement stmt = conn.prepareStatement(UPDATE_QUERY);
			
			Date introduced;
			if (computerToUpdate.getIntroduced()!=null) {
				introduced = computerToUpdate.getIntroduced();
			}else {
				introduced = null;
			}
			Date discontinued;
			if (computerToUpdate.getDiscontinued()!=null) {
				discontinued = computerToUpdate.getDiscontinued();
			}else {
				discontinued = null;
			}
			
			stmt.setString(1, computerToUpdate.getName());
			stmt.setDate(2, introduced);
			stmt.setDate(3, discontinued);
			if (computerToUpdate.getCompanyID()==null) {
				stmt.setNull(4, Types.BIGINT);
			}else {
				stmt.setLong(4, computerToUpdate.getCompanyID());
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
			Connection conn = Connexion.getConnection();
			PreparedStatement stmt = conn.prepareStatement(COUNT_QUERY);
			ResultSet resultSet = stmt.executeQuery();
			int entriesCount=0;
			
			while(resultSet.next()){
				entriesCount = mapper.countResults(resultSet);
			}
			conn.close();
			
			return entriesCount;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}


}
