package com.excilys.formation.CDB.service;
import java.sql.*;

public class ConnectionSingleton implements AutoCloseable {
	
	
	private static String dbURL;
	private static String username;
	private static String password;
	private static String driver;
	
	private ConnectionSingleton() {
		dbURL="jdbc:mysql://localhost:3306/computer-database-db?seUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
		username = "admincdb";
		password = "qwerty1234";
		driver = "com.mysql.cj.jdbc.Driver";
	}
	
	private static class ConnectionSingletonHolder{
		private final static ConnectionSingleton instance = new ConnectionSingleton();
	}
	
	public static ConnectionSingleton getInstance() {
		return ConnectionSingletonHolder.instance;
	}

	
	public Connection getConnection() {
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


	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		this.close();
		
	}
	
	
	
	
	
}

