package at.technikumwien.customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/customerservlet")
@SuppressWarnings("serial")
public class CustomerServlet extends HttpServlet {
	@Inject
	private CustomerService customerService;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		StringBuilder html = new StringBuilder(
			"<!DOCTYPE html>" +
			"<html>" +
			"<head>" +
			"<meta charset='UTF-8'>" +
			"<title>Customers</title>" +
			"</head>" +
			"<body>" +
			"<h1>Customers</h1>"
		);
		
		List<Customer> customersList = customerService.getAllCustomers();
		
		for (Customer customer : customersList) {
			html.append(
				"<h2>" + customer.getFirstname() + customer.getLastname() + "</h2>" +
				"<p> Birthdate: " + customer.getBirthDate().toString() + "</p>" +
				"<p> Active " + customer.getActiveState().toString() + "</p>"
			);
		}
		
		html.append(
			"</body>" +
			"</html>"
		);
		
		out.println(html.toString());
	}
}