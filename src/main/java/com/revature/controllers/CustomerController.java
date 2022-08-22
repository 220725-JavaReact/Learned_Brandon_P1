package com.revature.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dao.CustomerDao;
import com.revature.dao.Dao;
import com.revature.models.Customer;

public class CustomerController extends HttpServlet{

	/*
	 * This is responsible for handling all get requests
	 */
	private static Dao<Customer> customerDao = new CustomerDao();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		final String URI = req.getRequestURI().replace("/Learned_Brandon_P1/", "");
		
		switch (URI) {
		case "customer": //use for login maybe
			resp.getWriter().println("One Customer");
			break;
		case "allcustomers":
			ArrayList<Customer> customers = customerDao.getAllInstance();
			resp.getWriter().write("<html><body><h1>All Customers</h1>");
			for(Customer customer : customers) {
				resp.getWriter().println(customer.getUsername());
			}
			
			resp.getWriter().write("</body></html>");;
		default:
			
			break;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//unpacking form elements sent via request
		String firstname = req.getParameter("firstname");
		firstname = firstname.substring(0, 1).toUpperCase() + firstname.substring(1).toLowerCase();
		String lastname = req.getParameter("lastname");
		lastname = lastname.substring(0, 1).toUpperCase() + lastname.substring(1).toLowerCase();
		String username = req.getParameter("cusername");
		String password = req.getParameter("cpassword");
		String email = req.getParameter("email");
		
		if(customerDao.getByAttribute(username) != null) {
			resp.getWriter().write("<html><body>");
			resp.getWriter().write("<h2>Customer: " + username + " already exists!</h2>");
			resp.getWriter().write("<form method = \"get\" action = \"/Learned_Brandon_P1/home\">\r\n"
					+ "			<input type =  \"submit\" value = \"Back To Menu\"/>\r\n"
					+ "		</form>");

			resp.getWriter().write("</body></html>");

		} else {
			Customer customer = new Customer(firstname, lastname, username.toLowerCase(), password, email);
			customerDao.addInstance(customer);
			resp.getWriter().write("<html><body>");
			resp.getWriter().write("<h2>Customer: " + customer.getUsername() + " successfully created!</h2>");
			resp.getWriter().write("<form method = \"get\" action = \"/Learned_Brandon_P1/home\">\r\n"
					+ "			<input type =  \"submit\" value = \"Back To Menu\"/>\r\n"
					+ "		</form>");

			resp.getWriter().write("</body></html>");
		}	
	}
}
