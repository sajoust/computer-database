package com.excilys.formation.CDB.persistence;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;

import org.dbunit.DBTestCase;
import org.dbunit.DatabaseTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import com.excilys.formation.CDB.mapper.ComputerMapper;
import com.excilys.formation.CDB.model.Computer;

import org.dbunit.operation.DatabaseOperation;

public class ComputerDAOTest extends DBTestCase {

	private ComputerDAO computerDAO = new ComputerDAO();
	
    public ComputerDAOTest(String name)
    {
        super( name );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.cj.jdbc.Driver" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:3306/computer_database_test?serverTimezone=UTC" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "pedro" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "qwerty1234" );
	// System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "" );
    }


	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/computer.xml"));
	}
	
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }
 
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.NONE;
    }
    
//    @Test
//    public void testAdd() throws SQLException {
//    	
//    	
//    	String[] computerInfo = {"ohohohohoho","1997-01-01","1998-01-01","1"};
//    	Properties defaultProperties = new Properties();
//    	String propertiesFileName ="/home/titouan/eclipse-workspace/computer-database/src/main/resources/connector.properties";
//		InputStream inputStream;
//		try {
//			inputStream = new FileInputStream(propertiesFileName);
//			defaultProperties.load(inputStream);
//			defaultProperties.setProperty("db.username", "patrick");
//			System.out.println("sgfsuKF");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NullPointerException e) {
//			System.err.println("coucou");
//			e.printStackTrace();
//		}
//		
//    	//computerDAO.add(computerInfo);
//    	
//    	
//    }
    @Test
    public void testTest() {
computerDAO.getAll(10, 1).forEach(System.out::println);
        
    }

}
