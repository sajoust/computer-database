package com.excilys.formation.CDB.service;
import java.sql.*;

public class Connexion {
	
	
	private static String dbURL = "jdbc:mysql://localhost:3306/computer-database-db?seUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String username = "admincdb";
	private static String password = "qwerty1234";
	private static String driver = "com.mysql.cj.jdbc.Driver";

	
	public static Connection getConnection() {
		try {
			Class.forName(driver);
			return DriverManager.getConnection(dbURL,username,password);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
		
	}
	
	
	
	
	
}
