package com.excilys.mapper;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.DTOCompany;
import com.excilys.dto.DTOComputer;
import com.excilys.model.Company;
import com.excilys.model.Computer;

public class ComputerDTOMapper {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ComputerDTOMapper.class);

	public static DTOComputer ComputerToDTO(Computer computer) {
		String id = String.valueOf(computer.getId());
		String name = computer.getName();
		String introduced = computer.getIntroduced() == null ? "" : computer.getIntroduced().toString();
		
		String discontinued = computer.getDiscontinued() == null ? "" : computer.getDiscontinued().toString();
		
		String companyName = computer.getCompany() == null ? "Unknown Company"
				: computer.getCompany().getName();
		String companyId = computer.getCompany()==null?null:String.valueOf(computer.getCompany().getId());
		DTOCompany dtoCompany = new DTOCompany(companyId, companyName);

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
		
		String companyName = dtoComputer.getDtoCompany().getName();
		Company company = dtoComputer.getDtoCompany().getId().equals("0")?null:new Company(Long.valueOf(dtoComputer.getDtoCompany().getId()), companyName);
		
		if (dtoComputer.getId() != null) {
			Long id = Long.parseLong(dtoComputer.getId());
			return new Computer(id, name, introduced, discontinued, company);
		}
		return new Computer(name, introduced, discontinued, company);
	}

}
