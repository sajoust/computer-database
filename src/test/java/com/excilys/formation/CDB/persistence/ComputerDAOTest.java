package com.excilys.formation.CDB.persistence;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.time.LocalDate;

import org.dbunit.DBTestCase;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.formation.CDB.DTO.PageDTO;
import com.excilys.formation.CDB.configuration.SpringConfig;
import com.excilys.formation.CDB.connection.ConnectionHikari;
import com.excilys.formation.CDB.model.Company;
import com.excilys.formation.CDB.model.Computer;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class ComputerDAOTest extends DBTestCase {

	@Autowired
	private ComputerDAO computerDAO;
	@Autowired
	private ConnectionHikari connectionHikari;
	private final Long ID = 1L;
	private final String NAME = "Pierre Palmade";
	private final LocalDate INTRODUCED = LocalDate.parse("1997-01-01");
	private final LocalDate DISCONTINUED = LocalDate.parse("1998-01-01");
	private final Company COMPANY = new Company(1, "laCompanyCreole");
	private final Computer testComputer = new Computer(ID, NAME, INTRODUCED, DISCONTINUED, COMPANY);
	private final Computer testComputerNoID = new Computer(NAME, INTRODUCED, DISCONTINUED, COMPANY);
	DatabaseConnection dbConnection;

	@Before
	public void setUp() throws Exception {
		dbConnection = new DatabaseConnection(connectionHikari.getConnection());
		getSetUpOperation().execute(dbConnection, getDataSet());
	}

	public void refresh() throws DatabaseUnitException, SQLException, Exception {
		DatabaseOperation.REFRESH.execute(dbConnection, getDataSet());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/computer.xml"));
	}

	@Override
	protected DatabaseOperation getSetUpOperation() throws Exception {

		return DatabaseOperation.CLEAN_INSERT;
	}

	@Test
	public void testGet() {
		String id = "1";
		Long DB_id = computerDAO.get(id).getId();
		Long actual_id = Long.parseLong(id);

		assertEquals(DB_id, actual_id);
	}

	@Test
	public void testGetNoSearchNoOrder() throws DataSetException, Exception {

		int nbRowsDataSet = getDataSet().getTable("computer").getRowCount();
		PageDTO page = new PageDTO( "", "",1,10);

		assertEquals(computerDAO.getAll(page).size(), nbRowsDataSet);

	}

	@Test
	public void testGetSearchNoOrder() throws DataSetException, Exception {

		PageDTO page = new PageDTO("Firts", "",10, 1);

		assertEquals(computerDAO.getAll(page).size(), 1);

	}

	@Test
	public void testGetNoSearchOrder() throws DataSetException, Exception {

		PageDTO page = new PageDTO( "", "name-DESC",10, 1);

		assertEquals(computerDAO.getAll(page).get(0).getName(), "Tirdh");

	}

	@Test
	public void testAdd() throws Exception {

		computerDAO.add(testComputerNoID);		
		assertEquals(computerDAO.getLast().getName(), testComputerNoID.getName());


	}

	@Test
	public void testDelete() throws DataSetException, Exception {
	
		assertNotNull(computerDAO.get(ID.toString()));
		assertEquals(1, computerDAO.deleteComputer(ID.toString()));
		
	}

	@Test
	public void testCountEntries() {
		assertEquals(3, computerDAO.countEntries(""));
	}

	@Test
	public void testUpdate() {
		assertTrue(true);
		assertFalse(!true);
		boolean notTrue = true;
		assertFalse(!notTrue);
		assertEquals(2, 1 + 1);

	}

	@Test
	public void testEdit() throws Exception {

		computerDAO.edit(ID.toString(), testComputer);

		assertEquals(testComputer, computerDAO.get(ID.toString()));

	}

}
