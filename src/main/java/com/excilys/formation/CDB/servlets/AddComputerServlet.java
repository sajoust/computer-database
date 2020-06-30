package com.excilys.formation.CDB.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.CDB.DTO.DTOCompany;
import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.service.CompanyService;
import com.excilys.formation.CDB.service.ComputerService;
import com.excilys.formation.CDB.validation.ValidationComputer;


@WebServlet(name = "AddComputerServlet", urlPatterns = { "/addComputer" })
public class AddComputerServlet extends HttpServlet {

	private ComputerService computerService=ComputerService.getInstance();
	private CompanyService companyService = CompanyService.getInstance();
	private List<DTOCompany> DTOList;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2024490107750585147L;

	/**
	 * 
	 */
	// private static final long serialVersionUID = 2L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// System.out.println(request.getAttribute("computerName"));
		DTOList=companyService.getAll(1	, 1, "la bretagne");
		request.setAttribute("DTOList", DTOList);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/AddComputer.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> errors = new HashMap<String, String>();
		//Map<String, String> dtoMap = DTOList.stream().collect(Collectors.toMap(DTOCompany::getId, animal -> animal));
		
		
		
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String company_id = request.getParameter("companyId");
		
		System.out.println("dto id= "+ request.getParameter("companyId"));
		
		DTOCompany dtoCompany = new DTOCompany(request.getParameter("DTO.id"),request.getParameter("DTO.name"));
		DTOComputer dtoComputer = new DTOComputer(name, introduced, discontinued, dtoCompany);
	
	    try {
			ValidationComputer.nameValidation(dtoComputer);
		} catch (Exception e) {
			errors.put("computerName", e.getMessage());
		}
	    try {
			ValidationComputer.dateValidation(dtoComputer);
		} catch (Exception e) {
			errors.put("discontinued", e.getMessage());
		}
	    
	    request.setAttribute("errors", errors);
	    
	    if (errors.isEmpty()) {
			computerService.add(dtoComputer);
		}
		
		
		
		
		
		doGet(request, response);
	}

}
