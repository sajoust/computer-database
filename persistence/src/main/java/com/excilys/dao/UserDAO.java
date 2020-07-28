package com.excilys.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.excilys.model.Page;
import com.excilys.model.QUser;
import com.excilys.model.User;
import com.querydsl.jpa.impl.JPAQuery;




@Repository
public class UserDAO extends DAO<User> {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public List<User> getAll(Page currentPage) {
		List<User> userList = new ArrayList<>();
		JPAQuery<User> query = new JPAQuery<>(entityManager);
		QUser qUser = QUser.user;

		userList = query.from(qUser).fetch();
		return userList;
	}

	@Override
	public User get(String id) {
		JPAQuery<User> query = new JPAQuery<>(entityManager);
		QUser qUser = QUser.user;
		return query.from(qUser).where(qUser.id.eq(Integer.valueOf(id))).fetchOne();

	}
	
	public User getByName(String name) {
		JPAQuery<User> query = new JPAQuery<>(entityManager);
		QUser qUser = QUser.user;
		return query.from(qUser).where(qUser.username.eq(name)).fetchOne();

	}
	

}
