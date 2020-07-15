package com.excilys.formation.CDB.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.DTO.PageDTO;
import com.excilys.formation.CDB.model.Page;
import com.excilys.formation.CDB.service.ComputerService;

@WebServlet(name = "Dashboard", urlPatterns = { "/home_old" })
public class Dashboard extends HttpServlet {

	private static Logger logger = LoggerFactory.getLogger(ComputerService.class);

	private static final long serialVersionUID = 5205053770995883524L;

	@Autowired
	private ComputerService computerService;
	private List<DTOComputer> computerDTOList;

	public void init(ServletConfig config) {
		try {
			super.init(config);
		} catch (ServletException e) {

			e.printStackTrace();
		}
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		PageDTO page = paginate(request);
		computerDTOList = computerService.getAll(page);
		int nbEntries = computerService.countEntries(page.getSearch());
		int nbPages = (nbEntries / page.getComputerPerPage()) + 1;
		page.setPageToDisplay(Math.min(page.getPageToDisplay(), nbPages));
		

		
		request.setAttribute("search", page.getSearch());
		request.setAttribute("nbPages", nbPages);
		request.setAttribute("pageToDisplay", page.getPageToDisplay());
		request.setAttribute("computerPerPage", page.getComputerPerPage());
		request.setAttribute("DTOList", computerDTOList);
		request.setAttribute("entriesCount", nbEntries);
		request.setAttribute("order", page.getOrder());

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/Dashboard.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String computersToDelete = request.getParameter("selection");
		List<String> results = Arrays.asList(computersToDelete.split(","));
		
		results.stream().forEach(str -> computerService.delete(str));		

		doGet(request, response);
	}

	
	public PageDTO paginate(HttpServletRequest request) {

		int pageToDisplay = (request.getParameter("pageToDisplay") != null)
				? Integer.parseInt(request.getParameter("pageToDisplay"))
				: 1;

		String search = (request.getParameter("search") != null && !request.getParameter("search").equals(""))
				? request.getParameter("search")
				: "";
		String order = (request.getParameter("order") != null && !request.getParameter("order").equals(""))
				? request.getParameter("order")
				: "";
		int computerPerPage = (request.getParameter("computerPerPage") != null)
				? Integer.parseInt(request.getParameter("computerPerPage"))
				: 10;

		PageDTO page = new PageDTO(search,order,pageToDisplay,computerPerPage);
		return page;
	}

}
