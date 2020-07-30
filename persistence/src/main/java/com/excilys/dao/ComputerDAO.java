package com.excilys.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.model.QCompany;
import com.excilys.model.QComputer;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class ComputerDAO implements DAO<Computer> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public List<Computer> getAll(Page currentPage) {

		JPAQuery<Computer> query = new JPAQuery<>(entityManager);
		QComputer qComputer = QComputer.computer;
		QCompany qCompany = QCompany.company;

		return query.from(qComputer).leftJoin(qCompany).on(qComputer.company.id.eq(qCompany.id))
				.where(qComputer.name.contains(currentPage.getSearch())
						.or(qCompany.name.contains(currentPage.getSearch())))
				.orderBy(doOrder(currentPage)).limit(currentPage.getComputerPerPage())
				.offset((currentPage.getPageToDisplay() - 1) * (long)currentPage.getComputerPerPage()).fetch();

	}

	@Transactional
	public List<Computer> getAll() {

		JPAQuery<Computer> query = new JPAQuery<>(entityManager);
		QComputer qComputer = QComputer.computer;
		QCompany qCompany = QCompany.company;

		return query.from(qComputer).leftJoin(qCompany).on(qComputer.company.id.eq(qCompany.id)).fetch();
	}

	@Override
	public Computer get(String id) {
		JPAQuery<Computer> query = new JPAQuery<>(entityManager);
		QComputer qComputer = QComputer.computer;

		return query.from(qComputer).where(qComputer.id.eq(Long.valueOf(id))).fetchOne();
	}

	@Transactional
	public void add(Computer computerToAdd) {
		entityManager.persist(computerToAdd);
	}

	@Transactional
	public void edit(String id, Computer computerToEdit) {
		entityManager.merge(computerToEdit);
	}

	@Transactional
	public void deleteComputer(String id) {
		entityManager.remove(get(id));
	}

	public Computer getLast() {

		JPAQuery<Computer> query = new JPAQuery<>(entityManager);
		QComputer qComputer = QComputer.computer;

		return query.from(qComputer).orderBy(qComputer.id.desc()).fetchFirst();
	}

	public int countEntries(String filter) {

		JPAQuery<Computer> query = new JPAQuery<>(entityManager);
		QComputer qComputer = QComputer.computer;
		QCompany qCompany = QCompany.company;

		return (int) query.from(qComputer).leftJoin(qCompany).on(qComputer.company.id.eq(qCompany.id))
				.where(qComputer.name.contains(filter).or(qCompany.name.contains(filter))).fetchCount();

	}

	private OrderSpecifier<?> doOrder(Page currentPage) {
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;

		String[] order = currentPage.getOrder().split("-");

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
