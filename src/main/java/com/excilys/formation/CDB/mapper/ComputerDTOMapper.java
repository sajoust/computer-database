package com.excilys.formation.CDB.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.CDB.DTO.DTOCompany;
import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.model.Computer;

public class ComputerDTOMapper {


	private static Logger logger = LoggerFactory.getLogger(ComputerDTOMapper.class);

	
	public static DTOComputer ComputerToDTO(Computer computer) {
		String id = String.valueOf(computer.getId());
		String name = computer.getName();
		String introduced = computer.getIntroduced()==null?"":computer.getIntroduced().toString();
		String discontinued = computer.getDiscontinued()==null?"":computer.getDiscontinued().toString();
		DTOCompany dtoCompany = new DTOCompany(String.valueOf(computer.getCompany().getId()),computer.getCompany().getName());
		
		
		DTOComputer dtoComputer = new DTOComputer(id,name,introduced,discontinued,dtoCompany);
		return dtoComputer;
	}


	public static LocalDate stringToDate(String sDate) {

		return (sDate == null) ? null : LocalDate.parse(sDate);

	}

}
