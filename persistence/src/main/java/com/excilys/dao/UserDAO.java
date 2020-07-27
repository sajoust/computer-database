package com.excilys.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.excilys.model.Page;
import com.excilys.model.QUserModel;
import com.excilys.model.User;
import com.querydsl.jpa.impl.JPAQuery;

public class UserDAO extends DAO<User> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public List<User> getAll(Page currentPage) {
		List<User> userList = new ArrayList<>();
		JPAQuery<User> query = new JPAQuery<>(entityManager);
		QUserModel qUserModel = QUserModel.userModel;

		userList = query.from(qUserModel).fetch();
		return userList;
	}

	@Override
	public User get(String id) {
		JPAQuery<User> query = new JPAQuery<>(entityManager);
		QUserModel qUserModel = QUserModel.userModel;
		return query.from(qUserModel).where(qUserModel.id.eq(Long.valueOf(id))).fetchOne();

	}
	
	public User getByName(String name) {
		JPAQuery<User> query = new JPAQuery<>(entityManager);
		QUserModel qUserModel = QUserModel.userModel;
		return query.from(qUserModel).where(qUserModel.name.eq(name)).fetchOne();

	}
	

}
