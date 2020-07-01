package com.excilys.formation.CDB.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@WebServlet(name = "EditComputerServlet", urlPatterns = { "/editComputer" })
public class EditComputerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8760262791830598329L;
	private ComputerService computerService=ComputerService.getInstance();
	private CompanyService companyService = CompanyService.getInstance();
	private List<DTOCompany> dtoCompanyList;
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idToEdit = request.getParameter("computerToEdit");
		dtoCompanyList=companyService.getAll(1	, 1, "la bretagne");
		DTOComputer dtoComputer=computerService.get(idToEdit);
		
		
		
		request.setAttribute("computerToEditName", dtoComputer.getName());
		request.setAttribute("introducedComputerToEdit", dtoComputer.getIntroduced());
		request.setAttribute("discontinuedComputerToEdit", dtoComputer.getDiscontinued());
		request.setAttribute("dtoCompanyList", dtoCompanyList);
		request.setAttribute("companyIDComputerToEdit", dtoComputer.getDtoCompany().getId());
		request.setAttribute("companNameComputerToEdit", dtoComputer.getDtoCompany().getName());
		request.setAttribute("computerToEdit", idToEdit);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/EditComputer.jsp").forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String company_id = request.getParameter("companyId");
		
		
		DTOCompany dtoCompany = new DTOCompany(company_id);
		DTOComputer dtoComputer = new DTOComputer(name, introduced, discontinued, dtoCompany);
		
		
		Map<String, String> errors = new HashMap<String, String>();
		
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
	    	String idToEdit = request.getParameter("computerToEdit");
			computerService.edit(idToEdit, dtoComputer);
		}
		
		doGet(request, response);
	}


}
