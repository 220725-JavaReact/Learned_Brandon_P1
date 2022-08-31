package com.revature.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dao.CustomerDao;
import com.revature.dao.Dao;
import com.revature.helpermethods.CSS;
import com.revature.models.Customer;

public class CustomerController extends HttpServlet{

	/*
	 * This is responsible for handling all get requests
	 */
	private static Dao<Customer> customerDao = new CustomerDao();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		final String URI = req.getRequestURI().replace("/Learned_Brandon_P1/", "");
		HttpSession session =  req.getSession();
		PrintWriter writer = resp.getWriter();
		
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
			
			resp.getWriter().write("</body></html>");
		case "customerlogin": //use for login maybe
			Customer loginCust = customerDao.getByAttribute(req.getParameter("cusername"));
			if(loginCust != null && req.getParameter("cpassword").equals(loginCust.getPassword())) {
				session.setAttribute("customer", loginCust);
				resp.sendRedirect("/Learned_Brandon_P1/storeselect");
			} else {
				writer.write("<html>" + CSS.getCSS(false) + ""
						+ "<style> img {border-radius: 50%; padding: 15px;}</style><body>");
				
				writer.write("<ul class=\"topnav\">\r\n"
						+ "<li><a href=\"/Learned_Brandon_P1/home\">Back to Menu</a></li>");
				writer.write("</ul>");
				resp.getWriter().write("<h2>Incorrect Username or Password</h2>");
				//button to go back to home page			
				writer.write("<img src=\"https://media.nationalgeographic.org/assets/photos/223/386/10ce445a-e6e8-481f-9e39-f88ae80966a6.jpg\" width=525px>");
				writer.write("<p>");
				for(int i = 0; i<20; i++) {
					writer.write("*quack quack quack quack quack quack quack quack quack quack quack "
						+ "quack quack ");
				}
				writer.write("</p>");
			}	
			break;
		default:
			
			break;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//unpacking form elements sent via request
		PrintWriter writer = resp.getWriter();
		HttpSession session = req.getSession();
		String firstname = req.getParameter("firstname");
		firstname = firstname.substring(0, 1).toUpperCase() + firstname.substring(1).toLowerCase();
		String lastname = req.getParameter("lastname");
		lastname = lastname.substring(0, 1).toUpperCase() + lastname.substring(1).toLowerCase();
		String username = req.getParameter("cusername");
		String password = req.getParameter("cpassword");
		String email = req.getParameter("email");
		
		if(customerDao.getByAttribute(username) != null) {
			writer.write("<html>" + CSS.getCSS(false) + ""
					+ "<style> img {border-radius: 50%; padding: 15px;}</style><body>");
			
			writer.write("<ul class=\"topnav\">\r\n"
					+ "<li><a href=\"/Learned_Brandon_P1/home\">Back to Menu</a></li>");
			writer.write("</ul>");
			resp.getWriter().write("<h2>User: " + username.toUpperCase() + " </br> is already swimming in the duckie pool!"
					+ "</br>Please try another username!</h2>");
			//button to go back to home page			
			writer.write("<img src=\"https://media.nationalgeographic.org/assets/photos/223/386/10ce445a-e6e8-481f-9e39-f88ae80966a6.jpg\" width=525px>");
			writer.write("<p>");
			for(int i = 0; i<17; i++) {
				writer.write("*quack quack quack quack quack quack quack quack quack quack quack "
					+ "quack quack ");
			}
			writer.write("</p>");

			
			
			resp.getWriter().write("</body></html>");

		} else {
			Customer customer = new Customer(firstname, lastname, username.toLowerCase(), password, email);
			customerDao.addInstance(customer);
			writer.write("<html>" + CSS.getCSS(false) + ""
					+ "<style> img {border-radius: 50%; padding: 15px;}</style><body>");
			writer.write("<ul class=\"topnav\">\r\n"
					+ "<li><a href=\"/Learned_Brandon_P1/home\">Back to Menu</a></li>");
			writer.write("</ul>");
			resp.getWriter().write("<h2>User: " + username.toUpperCase() + "</br>is now swimming in the duckie pool!</br>Happy Quackin'!</h2>");
			//button to go back to home page			
			writer.write("<img src=\"https://media.nationalgeographic.org/assets/photos/223/386/10ce445a-e6e8-481f-9e39-f88ae80966a6.jpg\" width=525px>");
			writer.write("<p>");
			for(int i = 0; i<17; i++) {
				writer.write("*quack quack quack quack quack quack quack quack quack quack quack "
					+ "quack quack ");
			}
			writer.write("</p>");
			resp.getWriter().write("</body></html>");
		}	
	}
}
