package com.excilys.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.excilys.model.CustomUserDetails;
import com.excilys.model.User;
import com.excilys.service.UserService;

@Controller
@SessionAttributes({ "currentUser" })
public class LoginController {

	private static Logger log = LoggerFactory.getLogger(LoginController.class);

	@SuppressWarnings("unused")
	private UserService userService;
	@SuppressWarnings("unused")
	private PasswordEncoder passwordEncoder;

	public LoginController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/loginFailed", method = RequestMethod.GET)
	public String loginError(Model model) {
		log.info("Login attempt failed");
		model.addAttribute("error", "true");
		return "login";

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(SessionStatus session) {

		SecurityContextHolder.getContext().setAuthentication(null);
		session.setComplete();
		return "redirect:/login";
		
	}

	@RequestMapping(value = "/postLogin", method = RequestMethod.POST)
	public String postLogin(Model model, HttpSession session) {
		log.info("postLogin()");
		UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
				.getContext().getAuthentication();
		validatePrinciple(authentication.getPrincipal());
		User loggedInUser = (User) authentication.getPrincipal();
		model.addAttribute("currentUser", loggedInUser.getUsername());
		session.setAttribute("userId", loggedInUser.getId());
		return "redirect:/home";

	}

	private void validatePrinciple(Object principal) {

		if (!(principal instanceof CustomUserDetails)) {

			throw new IllegalArgumentException("Principal can not be null!");

		}

	}

}
