package com.excilys.formation.CDB.persistence;

import java.io.FileInputStream;
import java.sql.SQLException;

import org.dbunit.DBTestCase;
import org.dbunit.DatabaseUnitException;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import com.excilys.formation.CDB.mapper.ComputerMapper;
import com.excilys.formation.CDB.model.Computer;

public class ComputerDAOTest extends DBTestCase {

	private ComputerDAO computerDAO = new ComputerDAO();
	private final String ID = "300";
	private final String NAME = "Pierre Palmade";
	private final String INTRODUCED = "1997-01-01";
	private final String DISCONTINUED = "1998-01-01";
	private final String COMPANY_ID = "1";
	private final String[] computerInfoTest = { NAME, INTRODUCED, DISCONTINUED, COMPANY_ID };
	
	IDatabaseConnection connection;

	public ComputerDAOTest(String name) throws ClassNotFoundException, SQLException, DatabaseUnitException {
		super(name);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.cj.jdbc.Driver");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
				"jdbc:mysql://localhost:3306/computer_database_test?serverTimezone=UTC");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "pedro");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "qwerty1234");
		 System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "" );
//		Class.forName("com.mysql.cj.jdbc.Driver");
//		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer_database_test?serverTimezone=UTC", "pedro", "qwerty1234");
//
//		connection = new DatabaseConnection(conn);

	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/computer.xml"));
	}

	protected DatabaseOperation getSetUpOperation() throws Exception {
		return DatabaseOperation.CLEAN_INSERT;
	}

	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.REFRESH;
	}

	@Test
	public void testGet() {
		String id = "1";
		Long DB_id = computerDAO.get(id).getId();
		Long actual_id = Long.parseLong(id);

		assertEquals(DB_id, actual_id);
	}

	@Test
	public void testGetAll() {
		try {
			int nbRowsDataSet = getDataSet().getTable("computer").getRowCount();

			assertEquals(computerDAO.getAll(10, 1).size(), nbRowsDataSet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetByName() {
		String name = "Firts";
		assertEquals(computerDAO.getByName(name).getName(), name);
	}

	@Test
	public void testAdd() {
		try {
			computerDAO.add(computerInfoTest);
			Computer c = computerDAO.getByName(NAME);

			assertEquals(ComputerMapper.stringTabToComputer(computerInfoTest).getName(), c.getName());

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testDelete() throws DataSetException, Exception {
		String id = "1";
		Long actualId = Long.parseLong(id);
		Long dbId = computerDAO.deleteComputer(id).getId();

		assertEquals(dbId, actualId);


	}

	@Test
	public void testUpdate() {

	}

}
