package com.excilys.formation.CDB.mapper;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.formation.CDB.model.Computer;

import junit.framework.TestCase;



public class ComputerMapperTest {


	private ResultSet rs = Mockito.mock(ResultSet.class);
	
	private final long ID = 42L;
	private final String NAME = "unJedi";
	private final String INTRODUCED = "1997-03-01";
	private final String DISCONTINUED = "2197-03-01";
	private final long COMPANY_ID = 6L;
	
	@Before
	public void setUp() {
	    MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testProcessResults() {
		try {
			Mockito.when(rs.getLong(1)).thenReturn(ID);
			Mockito.when(rs.getString("name")).thenReturn(NAME);
			Mockito.when(rs.getString("introduced")).thenReturn(INTRODUCED);
			Mockito.when(rs.getString("discontinued")).thenReturn(DISCONTINUED);
			Mockito.when(rs.getLong("company_id")).thenReturn(COMPANY_ID);
			
			
			Computer computerExpected = new Computer (ID, NAME, LocalDate.parse(INTRODUCED),LocalDate.parse(DISCONTINUED),COMPANY_ID);
			
			assertEquals(computerExpected, ComputerMapper.processResults(rs));
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testProcessResultsNull() {
		try {
			Mockito.when(rs.getLong(1)).thenReturn(ID);
			Mockito.when(rs.getString("name")).thenReturn(NAME);
			Mockito.when(rs.getString("introduced")).thenReturn(null);
			Mockito.when(rs.getString("discontinued")).thenReturn(null);
			Mockito.when(rs.getLong("company_id")).thenReturn(COMPANY_ID);
			
			
			Computer computerExpected = new Computer (ID, NAME, null,null,COMPANY_ID);
			
			assertEquals(computerExpected, ComputerMapper.processResults(rs));
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	



	@Test
	public void testStringToDate() {
		String sDate = "1997-01-03";

		assertEquals(LocalDate.parse(sDate), ComputerMapper.stringToDate(sDate));
		

	}

}
