package com.excilys.formation.CDB.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.service.ComputerService;

@WebServlet(name = "Dashboard", urlPatterns = { "/home" })
public class Dashboard extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5205053770995883524L;
	//private static final long serialVersionUID = 1L;
	private ComputerService computerService=ComputerService.getInstance();
	//private PageComputer pageComputer = new PageComputer(10);
	private List<DTOComputer> DTOList;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		int pageToDisplay = (request.getParameter("pageToDisplay")!=null)?Integer.parseInt(request.getParameter("pageToDisplay")):1;
		int computerPerPage = (request.getParameter("computerPerPage")!=null)?Integer.parseInt(request.getParameter("computerPerPage")):10;
		int nbPages = (computerService.countEntries()/computerPerPage)+1;
		pageToDisplay=Math.min(pageToDisplay, nbPages);
		String search =(request.getParameter("search")!=null)?request.getParameter("search"):"no_filter";
		DTOList=computerService.getAll(computerPerPage, pageToDisplay,search);
		
		

		request.setAttribute("nbPages", nbPages);
		request.setAttribute("pageToDisplay",pageToDisplay);
		request.setAttribute("computerPerPage", computerPerPage);
		request.setAttribute("DTOList", DTOList);		
		request.setAttribute("entriesCount", computerService.countEntries());
		
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/Dashboard.jsp").forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	 

}
