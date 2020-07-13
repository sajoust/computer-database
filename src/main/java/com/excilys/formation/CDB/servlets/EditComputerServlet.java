
package com.excilys.formation.CDB.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formation.CDB.DTO.DTOCompany;
import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.exceptions.ComputerDateException;
import com.excilys.formation.CDB.exceptions.ComputerNameException;
import com.excilys.formation.CDB.model.Page;
import com.excilys.formation.CDB.service.CompanyService;
import com.excilys.formation.CDB.service.ComputerService;
import com.excilys.formation.CDB.validation.ValidationComputer;

@WebServlet(name = "EditComputerServlet", urlPatterns = { "/editComputer" })
public class EditComputerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8760262791830598329L;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	private List<DTOCompany> dtoCompanyList;
	private String idToEdit = "";

	public void init(ServletConfig config) {
		try {
			super.init(config);
		} catch (ServletException e) {

			e.printStackTrace();
		}
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		idToEdit = request.getParameter("computerToEdit");

		request.setAttribute("computerToEdit", idToEdit);
		dtoCompanyList = companyService.getAll(new Page());
		DTOComputer dtoComputer = computerService.get(idToEdit);

		request.setAttribute("computerToEditName", dtoComputer.getName());
		request.setAttribute("introducedComputerToEdit", dtoComputer.getIntroduced());
		request.setAttribute("discontinuedComputerToEdit", dtoComputer.getDiscontinued());
		request.setAttribute("dtoCompanyList", dtoCompanyList);
		request.setAttribute("companyIDComputerToEdit", dtoComputer.getDtoCompany().getId());
		request.setAttribute("companyNameComputerToEdit", dtoComputer.getDtoCompany().getName());

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/EditComputer.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String company_id = request.getParameter("companyId");
		String companyName = (String) request.getAttribute("companyNameComputerToEdit");

		DTOCompany dtoCompany = new DTOCompany(company_id, companyName);
		DTOComputer dtoComputer = new DTOComputer(idToEdit, name, introduced, discontinued, dtoCompany);

		Map<String, String> errors = new HashMap<String, String>();

		errors = isValid(dtoComputer, errors);

		request.setAttribute("errors", errors);

		if (errors.isEmpty()) {

			computerService.edit(idToEdit, dtoComputer);
			response.sendRedirect(request.getContextPath() + "/home");
		} else {

			doGet(request, response);
			// response.sendRedirect("editComputer?computerToEdit="+idToEdit);
		}

	}

	private Map<String, String> isValid(DTOComputer dtoComputer, Map<String, String> errors) {
		try {
			ValidationComputer.nameValidation(dtoComputer);
			ValidationComputer.dateValidation(dtoComputer);
		} catch (ComputerNameException exceptionName) {
			errors.put("computerName", exceptionName.getMessage());
		} catch (ComputerDateException exceptionDate) {
			errors.put("discontinued", exceptionDate.getMessage());
		}

		return errors;
	}

}