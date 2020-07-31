package com.excilys;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;

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

import com.excilys.config.PersistenceConfig;
import com.excilys.connection.ConnectionHikari;
import com.excilys.dao.ComputerDAO;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.Page;



@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class)
public class ComputerDAOTest extends DBTestCase {

	@Autowired
	private ComputerDAO computerDAO;
	@Autowired
	private ConnectionHikari connectionHikari;
	private final Long ID = 1L;
	private final String NAME = "Pierre Palmade";
	private final LocalDate INTRODUCED = LocalDate.parse("1997-01-01");
	private final LocalDate DISCONTINUED = LocalDate.parse("1998-01-01");
	private final Company COMPANY = new Company(1L, "laCompanyCreole");
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
		Page page = new Page( "", "",1,10);

		assertEquals(computerDAO.getAll(page).size(), nbRowsDataSet);

	}

	@Test
	public void testGetSearchNoOrder() throws DataSetException, Exception {

		Page page = new Page("Firts", "",1, 10);

		assertEquals(computerDAO.getAll(page).get(0).getName(), "Firts");

	}

	@Test
	public void testGetAllNoPage() throws DataSetException, Exception {
		int nbRowsDataSet = getDataSet().getTable("computer").getRowCount();
		assertEquals(computerDAO.getAll().size(), nbRowsDataSet);
	}
	
	@Test
	public void testGetOrder() {
		
		Page pageNameAsc = new Page("","computer.name-ASC",1,10);
		assertEquals(computerDAO.getAll(pageNameAsc).get(0).getName(), "Firts");
		Page pageNameDesc = new Page("","computer.name-DESC",1,10);
		assertEquals(computerDAO.getAll(pageNameDesc).get(0).getName(), "Tirdh");
		Page pageIntroducedASC = new Page("","introduced-ASC",1,10);
		assertEquals(computerDAO.getAll(pageIntroducedASC).get(0).getIntroduced(), LocalDate.of(1997, Month.MARCH, 1));
		Page pageIntroducedDesc = new Page("","introduced-DESC",1,10);
		assertEquals(computerDAO.getAll(pageIntroducedDesc).get(0).getIntroduced(), LocalDate.of(1997, Month.MARCH, 1));
		Page pageDiscontinuedAsc = new Page("","discontinued-ASC",1,10);
		assertEquals(computerDAO.getAll(pageDiscontinuedAsc).get(0).getDiscontinued(), LocalDate.of(2097, Month.JANUARY, 12));
		Page pageDiscontinuedDesc = new Page("","discontinued-DESC",1,10);
		assertEquals(computerDAO.getAll(pageDiscontinuedDesc).get(0).getDiscontinued(), LocalDate.of(2197, Month.JANUARY, 12));
		Page pageCompanyAsc = new Page("","company_name-ASC",1,10);
		assertEquals(computerDAO.getAll(pageCompanyAsc).get(0).getCompany().getName(), "la7emeCompany");
		Page pageCompanyDesc = new Page("","company_name-DESC",1,10);
		assertEquals(computerDAO.getAll(pageCompanyDesc).get(0).getCompany().getName(), "laCompanyCreole");
	}

	@Test
	public void testAdd() throws Exception {

		computerDAO.add(testComputerNoID);		
		assertEquals(computerDAO.getLast().getName(), testComputerNoID.getName());
	}

	@Test
	public void testDelete() throws Exception {
		String idStr = String.valueOf(ID);
		computerDAO.deleteComputer(idStr);
		refresh();
		int nbRowsDataSet = getDataSet().getTable("computer").getRowCount();
		assertEquals(2, nbRowsDataSet);
		
	}

	@Test
	public void testCountEntries() {
		assertEquals(3, computerDAO.countEntries(""));
	}
	
	@Test
	public void testCountEntriesFilter() {
		assertEquals(1, computerDAO.countEntries("Firts"));
	}

	@Test
	public void testEdit() throws Exception {

		computerDAO.edit(ID.toString(), testComputer);

		assertEquals(testComputer, computerDAO.get(ID.toString()));

	}

}

