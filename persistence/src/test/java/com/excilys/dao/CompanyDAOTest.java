package com.excilys.dao;

import java.io.FileInputStream;

import org.dbunit.DBTestCase;
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
import com.excilys.model.Page;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class)
public class CompanyDAOTest extends DBTestCase {

	
	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private ConnectionHikari connectionHikari;
	DatabaseConnection dbConnection;

	@Before
	public void setUp() throws Exception {

		dbConnection = new DatabaseConnection(connectionHikari.getConnection());
		getSetUpOperation().execute(dbConnection, getDataSet());
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
	public void testGetAllPage() throws DataSetException, Exception {
		Page page = new Page();
		int nbRowsDataSet = getDataSet().getTable("company").getRowCount();
		assertEquals(nbRowsDataSet, companyDAO.getAll(page).size());
	}

	@Test
	public void testGetString() {
		assertEquals(companyDAO.get("1").getName(), "laCompanyCreole");
	}

	@Test
	public void testDelete() throws DataSetException, Exception {
		companyDAO.delete("2");
		int nbRowsDataSet = getDataSet().getTable("company").getRowCount();
		assertEquals(1, nbRowsDataSet);
	}

}
