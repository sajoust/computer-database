package com.excilys.formation.CDB.service;


import java.sql.*;


import com.excilys.formation.CDB.DAO.Connexion;

public class EntriesViewer {
	
	private String newLine = System.getProperty("line.separator");

	public static void viewEntries(String choice) {
		try {

			Connection conn = Connexion.getConnection();
			
			Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery("select * from "+choice);
			
			while (results.next()) {
				StringBuilder sb = new StringBuilder();
				sb.append("Name: ").append(results.getString("name"));
				
				System.out.println(sb);
				
			}
			conn.close();
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void viewEntry(long ID) {
		
		
		try {
			Connection conn = Connexion.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery("select * from computer where id="+ID);
			
			while (results.next()) {
				StringBuilder sb = new StringBuilder();
				sb.append("ID: ").append(results.getString("id")).append("\n");
				sb.append("Name: ").append(results.getString("name")).append("\n");
				sb.append("Introduced: ").append(results.getString("introduced")).append("\n");
				sb.append("Discontinued: ").append(results.getString("discontinued")).append("\n");
				sb.append("Company ID: ").append(results.getString("company_id")).append("\n");
				
				System.out.println(sb);
				
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
