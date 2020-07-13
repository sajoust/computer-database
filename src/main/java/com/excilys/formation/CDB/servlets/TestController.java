package com.excilys.formation.CDB.servlets;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {
	@RequestMapping(path = "/hello/{name}", method = RequestMethod.GET)

	public String greet(@PathVariable String name, ModelMap model) {
		String hello = " Hello !!!" + name + " How are You?";
		model.addAttribute("hello", hello); 
		System.out.println(hello);
		return "hello"; // ici le nom

	}
}
