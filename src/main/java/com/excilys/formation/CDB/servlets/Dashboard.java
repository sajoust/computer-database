package com.excilys.formation.CDB.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.CDB.DTO.DTOCompany;
import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.service.CompanyService;
import com.excilys.formation.CDB.service.ComputerService;

@WebServlet(name = "Dashboard", urlPatterns = { "/home" })
public class Dashboard extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5205053770995883524L;

	private ComputerService computerService=ComputerService.getInstance();
	private CompanyService companyService = CompanyService.getInstance();

	private List<DTOComputer> computerDTOList;
	private List<DTOCompany> CompanyDTOList;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		int pageToDisplay = (request.getParameter("pageToDisplay")!=null)?Integer.parseInt(request.getParameter("pageToDisplay")):1;
		
		
		String search =(request.getParameter("search")!=null && !request.getParameter("search").equals(""))?request.getParameter("search"):"";
		String order = (request.getParameter("order")!=null && !request.getParameter("order").equals(""))?request.getParameter("order"):"";
		int computerPerPage = (request.getParameter("computerPerPage")!=null)?Integer.parseInt(request.getParameter("computerPerPage")):10;
		
		System.out.println("ORDER DASHBOARD GET   "+order);
		computerDTOList=computerService.getAll(computerPerPage, pageToDisplay, search, order);
		int nbEntries=computerService.countEntries(search);
		int nbPages = (nbEntries/computerPerPage)+1;
		pageToDisplay=Math.min(pageToDisplay, nbPages);
		
		
		
		
		//CompanyDTOList=companyService.getAll();
		//int nbEntries = computerDTOList.size();
		
		
		request.setAttribute("search", search);
		request.setAttribute("nbPages", nbPages);
		request.setAttribute("pageToDisplay",pageToDisplay);
		request.setAttribute("computerPerPage", computerPerPage); 
		request.setAttribute("DTOList", computerDTOList);	
		request.setAttribute("entriesCount", nbEntries);
		request.setAttribute("order", order);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/Dashboard.jsp").forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String computersToDelete = request.getParameter("selection");
		String[] results = computersToDelete.split(",");
		for (String str : results) {
			computerService.delete(str);
		}

		//System.out.println("COMPUTERS TO DELETE DASHBOARD POST     "+computersToDelete);
		
		
		doGet(request, response);
	}
	
	
	 

}
