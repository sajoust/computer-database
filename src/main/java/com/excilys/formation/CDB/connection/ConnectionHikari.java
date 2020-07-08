package com.excilys.formation.CDB.connection;

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
		//config = new HikariConfig("/connector.properties");
		this.dataSource = dataSource;
	}

//	private static class ConnectionHikariHolder {
//		private final static ConnectionHikari instance = new ConnectionHikari();
//	}
//
//	public static ConnectionHikari getInstance() {
//		return ConnectionHikariHolder.instance;
//	}


	public Connection getConnection() {

		try {
			if (conn == null || conn.isClosed()) {
				return dataSource.getConnection();
			}
		} catch (SQLException e) {
			//logger.debug("datasource null");
			logger.error("connection issue");
			e.printStackTrace();
		}
		return conn;
	}

}
