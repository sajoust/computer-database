package com.excilys.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

		List<Company> companyList;
		JPAQuery<Company> query = new JPAQuery<>(entityManager);
		QCompany qCompany = QCompany.company;

		companyList = query.from(qCompany)

				.fetch();

		return companyList;

	}

	@Override
	public Company get(String id) {


		JPAQuery<Company> query = new JPAQuery<>(entityManager);
		QCompany qCompany = QCompany.company;
		return query.from(qCompany).where(qCompany.id.eq(Long.valueOf(id))).fetchFirst();
	}
	@Transactional
	public void delete(String id) {
		
		entityManager.remove(get(id));

	}

}
