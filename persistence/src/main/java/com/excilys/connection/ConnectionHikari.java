package com.excilys.connection;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

@Component
public class ConnectionHikari {
	private static Connection conn;
	private static Logger logger = LoggerFactory.getLogger(ConnectionHikari.class);

	
	private HikariDataSource dataSource;

	@Autowired
	public ConnectionHikari(HikariDataSource dataSource) {

		this.dataSource = dataSource;

	}



	public Connection getConnection() {

		try {
			if (conn == null || conn.isClosed()) {
				return dataSource.getConnection();
			}
		} catch (SQLException e) {

			logger.error("connection issue");
			e.printStackTrace();
		}
		return conn;
	}

	public HikariDataSource getDataSource() {
		return dataSource;
	}

}
