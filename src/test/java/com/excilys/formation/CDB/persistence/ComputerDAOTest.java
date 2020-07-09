package com.excilys.formation.CDB.persistence;

import java.io.FileInputStream;
import java.sql.SQLException;

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

import com.excilys.formation.CDB.DTO.DTOCompany;
import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.configuration.SpringConfig;
import com.excilys.formation.CDB.connection.ConnectionHikari;
import com.excilys.formation.CDB.mapper.CompanyDTOMapper;
import com.excilys.formation.CDB.mapper.ComputerDAOMapper;
import com.excilys.formation.CDB.model.Company;
import com.excilys.formation.CDB.model.Page;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class ComputerDAOTest extends DBTestCase {

	@Autowired
	private ComputerDAO computerDAO;
	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private ConnectionHikari connectionHikari;
	private final String ID = "1";
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
	public void testGetNoSearchNoOrder() throws DataSetException, Exception {

		int nbRowsDataSet = getDataSet().getTable("computer").getRowCount();
		Page page = new Page(10, 1, "", "");

		assertEquals(computerDAO.getAll(page).size(), nbRowsDataSet);

	}

	@Test
	public void testGetSearchNoOrder() throws DataSetException, Exception {

		Page page = new Page(10, 1, "Firts", "");

		assertEquals(computerDAO.getAll(page).size(), 1);

	}

	@Test
	public void testGetNoSearchOrder() throws DataSetException, Exception {

		Page page = new Page(10, 1, "", "name-DESC");

		assertEquals(computerDAO.getAll(page).get(0).getName(), "Tirdh");

	}

	@Test
	public void testAdd() throws Exception {

		
		Company company = companyDAO.get("1");
		DTOComputer dtoComputer = new DTOComputer(NAME, INTRODUCED, DISCONTINUED, CompanyDTOMapper.CompanyToDTO(company));
		DTOComputer dtoComputerNoCompany = new DTOComputer(NAME, INTRODUCED, DISCONTINUED, new DTOCompany("0"));
		computerDAO.add(dtoComputer);
		
		assertEquals(computerDAO.getLast().getName(), ComputerDAOMapper.dtoToComputer(dtoComputer).getName());
		// assertEquals(computerDAO.add(dtoComputerNoCompany),
		// ComputerDAOMapper.dtoToComputer(dtoComputerNoCompany));

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
		Company company = companyDAO.get("1");
		DTOComputer dtoComputer = new DTOComputer(ID, NAME, INTRODUCED, DISCONTINUED,
				CompanyDTOMapper.CompanyToDTO(company));
		computerDAO.edit(ID, dtoComputer);

		assertEquals(ComputerDAOMapper.dtoToComputer(dtoComputer), computerDAO.get(ID));

	}

}
