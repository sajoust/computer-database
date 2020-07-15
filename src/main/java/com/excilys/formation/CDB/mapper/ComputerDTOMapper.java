package com.excilys.formation.CDB.mapper;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.CDB.DTO.DTOCompany;
import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.model.Company;
import com.excilys.formation.CDB.model.Computer;

public class ComputerDTOMapper {

	private static Logger logger = LoggerFactory.getLogger(ComputerDTOMapper.class);

	public static DTOComputer ComputerToDTO(Computer computer) {
		String id = String.valueOf(computer.getId());
		String name = computer.getName();
		String introduced = computer.getIntroduced() == null ? "" : computer.getIntroduced().toString();
		String discontinued = computer.getDiscontinued() == null ? "" : computer.getDiscontinued().toString();
		String companyName = computer.getCompany().getName() == null ? "Unknown Company"
				: computer.getCompany().getName();
		System.out.println("COMPANY NAME COMPUTERDTOMAPPER -------------------------- " + companyName);
		DTOCompany dtoCompany = new DTOCompany(String.valueOf(computer.getCompany().getId()), companyName);

		DTOComputer dtoComputer = new DTOComputer(id, name, introduced, discontinued, dtoCompany);
		return dtoComputer;
	}

	public static LocalDate stringToDate(String sDate) {

		return (sDate == null) ? null : LocalDate.parse(sDate);

	}

	public static Computer dtoToComputer(DTOComputer dtoComputer) {

		String name = dtoComputer.getName();
		LocalDate introduced = (dtoComputer.getIntroduced() == null ? null
				: LocalDate.parse(dtoComputer.getIntroduced()));
		LocalDate discontinued = (dtoComputer.getDiscontinued() == null ? null
				: LocalDate.parse(dtoComputer.getDiscontinued()));
		Long companyId = Long.parseLong(dtoComputer.getDtoCompany().getId());
		String companyName = dtoComputer.getDtoCompany().getName();
		Company company = new Company(companyId, companyName);
		if (dtoComputer.getId() != null) {
			Long id = Long.parseLong(dtoComputer.getId());
			return new Computer(id, name, introduced, discontinued, company);
		}
		return new Computer(name, introduced, discontinued, company);
	}

}
