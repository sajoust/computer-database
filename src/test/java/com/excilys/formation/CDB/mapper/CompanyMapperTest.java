package com.excilys.formation.CDB.mapper;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.formation.CDB.model.Company;

public class CompanyMapperTest {

	private ResultSet rs = Mockito.mock(ResultSet.class);
	private final long ID = 12L;
	private final String NAME = "laCompanieCréole";
	
	
	@Before
	public void setUp() {
	    MockitoAnnotations.initMocks(this);
	}
	@Test
	public void testProcessResults() throws SQLException {
		Mockito.when(rs.getLong(1)).thenReturn(ID);
		Mockito.when(rs.getString("name")).thenReturn(NAME);
		
		Company companyExpected = new Company(ID,NAME);
		assertEquals(companyExpected, CompanyDTOMapper.processResults(rs));
	}
	



}
