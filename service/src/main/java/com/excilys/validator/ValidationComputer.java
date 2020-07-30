package com.excilys.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.excilys.dto.DTOComputer;
import com.excilys.exception.ComputerDateException;
import com.excilys.exception.ComputerNameException;

public class ValidationComputer {
	
	private static final  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private ValidationComputer() {
		throw new AssertionError();
	}
	public static void nameValidation(DTOComputer dtoComputer) throws ComputerNameException {

		if (dtoComputer.getName().equals("")) {
			throw new ComputerNameException();
		}
	}
	
	public static void dateValidation(DTOComputer dtoComputer) throws ComputerDateException {
		dtoComputer.setIntroduced(dtoComputer.getIntroduced().equals("")?null:dtoComputer.getIntroduced());
		dtoComputer.setDiscontinued(dtoComputer.getDiscontinued().equals("")?null:dtoComputer.getDiscontinued());
		
		
		if (dtoComputer.getIntroduced()!=null && dtoComputer.getDiscontinued()!=null) {
			LocalDate introduced=LocalDate.parse(dtoComputer.getIntroduced(),dtf);
			LocalDate discontinued=LocalDate.parse(dtoComputer.getDiscontinued(),dtf);
			if (introduced.compareTo(discontinued)>0) {
				throw new ComputerDateException();
			}

		}

		
	}
}
