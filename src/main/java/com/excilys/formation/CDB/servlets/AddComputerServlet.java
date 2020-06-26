package com.excilys.formation.CDB.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.CDB.service.ComputerService;


@WebServlet(name = "AddComputerServlet", urlPatterns = { "/addComputer" })
public class AddComputerServlet extends HttpServlet {

	private ComputerService computerService=ComputerService.getInstance();
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2024490107750585147L;

	/**
	 * 
	 */
	//private static final long serialVersionUID = 2L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//System.out.println(request.getAttribute("computerName"));
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/AddComputer.jsp").forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("computerName");
		String introduced = (request.getParameter("introduced"))==""?(null):(request.getParameter("introduced"));
		String discontinued = (request.getParameter("discontinued"))==""?(null):(request.getParameter("discontinued"));
		String companyId = request.getParameter("companyId");		
		computerService.add(new String[] {name,introduced,discontinued,companyId});
		
		
		
		
		
		doGet(request, response);
	}
	
	
}
