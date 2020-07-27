package com.excilys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.dao.UserDAO;
import com.excilys.model.Page;
import com.excilys.model.User;


@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		User user = userDAO.getByName(username);
		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.build();

	}
	
	public List<User> getAll(Page currentPage){
		return userDAO.getAll(currentPage);
	}
	public User get(String id) {
		return userDAO.get(id);
	}
	public User getByName(String username) {
		return userDAO.getByName(username);
	}
	

}
