package com.excilys.formation.CDB.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet( name="Servlet", urlPatterns = {"/first"} )
public class Servlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String fruit = request.getParameter("fruit");
		
		String message = "j'aime les fruits en sirop é@Ê$"+((fruit != null)?fruit:"");
		request.setAttribute("sirop", message);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/First.jsp" ).forward( request, response );
	}
}
