package com.excilys.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.excilys.model.Company;
import com.excilys.model.Page;
import com.excilys.model.QCompany;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class CompanyDAO extends DAO<Company> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Company> getAll(Page currentPage) {

		List<Company> companyList = new ArrayList<>();
		JPAQuery<Company> query = new JPAQuery<Company>(entityManager);
		QCompany qCompany = QCompany.company;

		companyList = query.from(qCompany)

				.fetch();

		return companyList;

	}

	@Override
	public Company get(String id) {


		JPAQuery<Company> query = new JPAQuery<Company>(entityManager);
		QCompany qCompany = QCompany.company;
		return query.from(qCompany).where(qCompany.id.eq(Long.valueOf(id))).fetchFirst();
	}
	@Transactional
	public void delete(String id) throws SQLException, PersistenceException {
		
		entityManager.remove(get(id));

	}

}
