package com.revature.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dao.EmployeeDao;
import com.revature.helpermethods.CSS;
import com.revature.models.Customer;
import com.revature.models.Employee;

public class EmployeeController extends HttpServlet{

	EmployeeDao employeeDao = new EmployeeDao();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		final String URI = req.getRequestURI().replace("/Learned_Brandon_P1/", "");
		HttpSession session =  req.getSession();
		PrintWriter writer = resp.getWriter();
		
		switch (URI) {
		case "employeelogin": //use for login maybe
			Employee loginEmp = employeeDao.getByAttribute(req.getParameter("eusername"));
			if(loginEmp != null && req.getParameter("epassword").equals(loginEmp.getPassword())) {
				session.setAttribute("employee", loginEmp);
				resp.sendRedirect("/Learned_Brandon_P1/empstoreselect"); //this direct needs to be fixed

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
	
}
