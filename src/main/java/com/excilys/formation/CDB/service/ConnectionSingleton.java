package com.excilys.formation.CDB.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionSingleton implements AutoCloseable {



	private ConnectionSingleton() {
		
	}

	private static class ConnectionSingletonHolder {
		private final static ConnectionSingleton instance = new ConnectionSingleton();
	}

	public static ConnectionSingleton getInstance() {
		return ConnectionSingletonHolder.instance;
	}

	public Connection getConnection() {
		try {
			// Class.forName(driver);
			InputStream inputStream = ConnectionSingleton.class.getClassLoader()
					.getResourceAsStream("connector.properties");

			Properties defaultProperties = new Properties();
			defaultProperties.load(inputStream);
			String url = (String) defaultProperties.get("db.url");
			String username = (String) defaultProperties.get("db.username");
			String password = (String) defaultProperties.get("db.password");

			inputStream.close();

			return DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();

		} catch (IOException e) {
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
