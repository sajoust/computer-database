package com.excilys.formation.CDB.connection;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionHikari {
	private static Connection conn;
	//private static Logger logger = LoggerFactory.getLogger(ConnectionHikari.class);
	private static HikariConfig config;
	private static HikariDataSource datasource;

	private ConnectionHikari() {
		config = new HikariConfig("/connector.properties");
		datasource = new HikariDataSource(config);
	}

	private static class ConnectionHikariHolder {
		private final static ConnectionHikari instance = new ConnectionHikari();
	}

	public static ConnectionHikari getInstance() {
		return ConnectionHikariHolder.instance;
	}

	/**
	 * 
	 * @return
	 */
	public Connection getConnection() {

		try {
			if (conn == null || conn.isClosed()) {
				return datasource.getConnection();
			}
		} catch (SQLException e) {
			//logger.debug("datasource null");
			e.printStackTrace();
		}
		return conn;
	}

}
