package com.excilys.formation.CDB.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.exceptions.ComputerDateException;
import com.excilys.formation.CDB.exceptions.ComputerNameException;

public class ValidationComputerSpring  {
	
	final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static void nameValidation(DTOComputer dtoComputer) throws ComputerNameException {

		if (dtoComputer.getName().equals("")) {
			throw new ComputerNameException();
		}
	}
	
	public static void dateValidation(DTOComputer dtoComputer) throws ComputerDateException {
		dtoComputer.setIntroduced(dtoComputer.getIntroduced()==""?null:dtoComputer.getIntroduced());
		dtoComputer.setDiscontinued(dtoComputer.getDiscontinued()==""?null:dtoComputer.getDiscontinued());
		
		
		if (dtoComputer.getIntroduced()!=null && dtoComputer.getDiscontinued()!=null) {
			LocalDate introduced=LocalDate.parse(dtoComputer.getIntroduced(),dtf);
			LocalDate discontinued=LocalDate.parse(dtoComputer.getDiscontinued(),dtf);
			if (introduced.compareTo(discontinued)>0) {
				throw new ComputerDateException();
			}

		}

		
	}


}
