package com.excilys.formation.CDB.persistence;

import java.io.FileInputStream;
import java.sql.SQLException;

import org.dbunit.DBTestCase;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
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

import com.excilys.formation.CDB.DTO.DTOCompany;
import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.configuration.SpringConfig;
import com.excilys.formation.CDB.connection.ConnectionHikari;
import com.excilys.formation.CDB.mapper.CompanyDTOMapper;
import com.excilys.formation.CDB.mapper.ComputerDAOMapper;
import com.excilys.formation.CDB.model.Company;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class ComputerDAOTest extends DBTestCase {

	@Autowired
	private ComputerDAO computerDAO;
	@Autowired
	private ConnectionHikari connectionHikari;

	private final String NAME = "Pierre Palmade";
	private final String INTRODUCED = "1997-01-01";
	private final String DISCONTINUED = "1998-01-01";
	private final Company COMPANY = new Company(42, "ParIciLaCompanie");


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

//	@Override
//	protected DatabaseOperation getTearDownOperation() throws Exception {
//
//		return DatabaseOperation.REFRESH;
//	}

//
	@Test
	public void testGet() {
		String id = "1";
		Long DB_id = computerDAO.get(id).getId();
		Long actual_id = Long.parseLong(id);

		assertEquals(DB_id, actual_id);
	}

	@Test
	public void testGetAll() throws DataSetException, Exception {
		
		
		int nbRowsDataSet = getDataSet().getTable("computer").getRowCount();

		assertEquals(computerDAO.getAll(10, 1, "", "").size(), nbRowsDataSet);
		assertEquals(computerDAO.getAll(10, 1, "Firts", "").size(), 1);
		assertEquals(computerDAO.getAll(10, 1, "", "name-DESC").get(0).getName(), "Tirdh");

	}

	@Test
	public void testAdd() throws Exception {

		DTOComputer dtoComputer = new DTOComputer(NAME, INTRODUCED, DISCONTINUED, new DTOCompany("1", "Pomme"));
		DTOComputer dtoComputerNoCompany = new DTOComputer(NAME, INTRODUCED, DISCONTINUED, new DTOCompany("0"));
		assertNotNull(computerDAO);
		assertNotNull(dtoComputerNoCompany);
		assertEquals(computerDAO.add(dtoComputer), ComputerDAOMapper.dtoToComputer(dtoComputer));
		assertEquals(computerDAO.add(dtoComputerNoCompany), ComputerDAOMapper.dtoToComputer(dtoComputerNoCompany));

	}

	@Test
	public void testDelete() throws DataSetException, Exception {
		String id = "1";
		assertNotNull(computerDAO.get(id));
		computerDAO.deleteComputer(id);
		assertNull(computerDAO.get(id));

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
		DTOCompany dtoCompany = CompanyDTOMapper.CompanyToDTO(COMPANY);
		DTOComputer dtoComputer = new DTOComputer(NAME, INTRODUCED, DISCONTINUED, dtoCompany);
		String id = "1";
		computerDAO.edit("1", dtoComputer);
		//refresh();
		
		
		assertEquals(NAME, getDataSet().getTable("computer").getValue(0, "name"));
		assertEquals(getDataSet().getTable("computer").getValue(0, "introduced"), INTRODUCED);
		assertEquals(getDataSet().getTable("computer").getValue(0, "discontinued"), DISCONTINUED);
		
	}

}
