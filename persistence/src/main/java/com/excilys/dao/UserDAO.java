package com.excilys.dao;

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
public class UserDAO implements DAO<User> {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public List<User> getAll(Page currentPage) {

		JPAQuery<User> query = new JPAQuery<>(entityManager);
		QUser qUser = QUser.user;
		return query.from(qUser).fetch();
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
