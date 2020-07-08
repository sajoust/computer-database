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
import com.excilys.formation.CDB.service.CompanyService;
import com.excilys.formation.CDB.service.ComputerService;
import com.excilys.formation.CDB.validation.ValidationComputer;


@WebServlet(name = "AddComputerServlet", urlPatterns = { "/addComputer" })
public class AddComputerServlet extends HttpServlet {
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	private List<DTOCompany> DTOList;

	private static final long serialVersionUID = 2024490107750585147L;

	public void init(ServletConfig config) {
		try {
			super.init(config);
		} catch (ServletException e) {

			e.printStackTrace();
		}
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DTOList = companyService.getAll(1, 1, "la bretagne", "le plancton");
		request.setAttribute("DTOList", DTOList);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/AddComputer.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> errors = new HashMap<String, String>();

		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String company_id = request.getParameter("companyId");

		System.out.println("dto id= " + company_id);

		DTOCompany dtoCompany = new DTOCompany(company_id);
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
