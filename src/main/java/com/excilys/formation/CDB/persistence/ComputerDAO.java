package com.excilys.formation.CDB.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.excilys.formation.CDB.DTO.PageDTO;
import com.excilys.formation.CDB.model.Computer;
import com.excilys.formation.CDB.model.QCompany;
import com.excilys.formation.CDB.model.QComputer;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class ComputerDAO extends DAO<Computer> {

	

	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public List<Computer> getAll(PageDTO pageDto) {

		List<Computer> computerList = new ArrayList<>();

		JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
		QComputer qComputer = QComputer.computer;
		QCompany qCompany = QCompany.company;

		computerList = query.from(qComputer)
				.leftJoin(qCompany).on(qComputer.company.id.eq(qCompany.id))
				.where(qComputer.name.contains(pageDto.getSearch()).or(qCompany.name.contains(pageDto.getSearch())))
				.orderBy(doOrder(pageDto))
				.limit(pageDto.getComputerPerPage())
				.offset((pageDto.getPageToDisplay()-1)*pageDto.getComputerPerPage())
				.fetch();

		return computerList;

	}
	
	@Override
	public Computer get(String id) {
		JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
		QComputer qComputer = QComputer.computer;
		
		return query.from(qComputer)
				.where(qComputer.id.eq(Long.valueOf(id))).fetchOne();
	}

	@Transactional
	public void add(Computer computerToAdd) throws SQLException, PersistenceException{
		entityManager.persist(computerToAdd);
	}
	
	@Transactional
	public void edit(String id, Computer computerToEdit) throws SQLException, PersistenceException{
		entityManager.merge(computerToEdit);
	}
	
	@Transactional
	public void deleteComputer(String id) throws SQLException, PersistenceException {				
		entityManager.remove(get(id));	
	}
	
	
	public Computer getLast() {

		JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
		QComputer qComputer = QComputer.computer;

		return query.from(qComputer).orderBy(qComputer.id.desc()).fetchFirst();
	}
	
	
	public int countEntries(String filter) {
		
		JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
		QComputer qComputer = QComputer.computer;
		QCompany qCompany = QCompany.company;
		
		return (int) query
				.from(qComputer)
				.leftJoin(qCompany).on(qComputer.company.id.eq(qCompany.id))
				.where(qComputer.name.contains(filter)
						.or(qCompany.name.contains(filter)))
				.fetchCount();

	}
	

	private OrderSpecifier<?> doOrder(PageDTO pageDTO) {
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		

		String[] order = pageDTO.getOrder().split("-");

		switch (order[0]) {
		case "computer.name":
			if (order[1].equals("ASC")) {
				return computer.name.asc();
			}
			return computer.name.desc();

		case "introduced":
			if (order[1].equals("ASC")) {
				return computer.introduced.asc().nullsLast();
			}
			return computer.introduced.desc().nullsLast();
		case "discontinued":
			if (order[1].equals("ASC")) {
				return computer.discontinued.asc().nullsLast();
			}
			return computer.discontinued.desc().nullsLast();
		case "company_name":
			if (order[1].equals("ASC")) {
				return company.name.asc().nullsLast();
			}
			return company.name.desc().nullsLast();
		default:
			
			return computer.id.asc();
	
		}



	}


}
