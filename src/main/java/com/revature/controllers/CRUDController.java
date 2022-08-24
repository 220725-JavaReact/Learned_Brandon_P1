package com.revature.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.HelperMethods.CSS;
import com.revature.dao.OrderDao;
import com.revature.dao.StoreFrontDao;
import com.revature.models.Customer;
import com.revature.models.LineItem;
import com.revature.models.Order;
import com.revature.models.Product;
import com.revature.models.StoreFront;

public class CRUDController extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		final String URI = req.getRequestURI().replace("/Learned_Brandon_P1/", "");
		HttpSession session =  req.getSession();
		PrintWriter writer = resp.getWriter();
		StoreFrontDao storeFrontDao = new StoreFrontDao();
		OrderDao orderDao = new OrderDao();
		DecimalFormat df = new DecimalFormat("#.00");

		
		switch (URI) {
		case "finalizeorder":
			Order order = (Order) session.getAttribute("currentorder");
			StoreFront currentStore = (StoreFront) session.getAttribute("storechoice");
			Customer customer = (Customer) session.getAttribute("customer");
			orderDao.upDateOrdersAndStoreItems(customer, order, currentStore);
			
			writer.write("<html>" + CSS.getCSS(true) + "<body>");
			
			
//			writer.print("<p>Order:<p><ul>");
//			for(LineItem item : order.getLineItemArray()) {
//				writer.write("<li>" + item.getProduct().getName() + " x " + item.getQuantity() + " - $" + (item.getProduct().getPrice() * item.getQuantity()) + "</li>");
//			}
//			writer.print("</ul><p>Successfully Submitted!</p>");
//
//			writer.print("<form class=\"fixform\" method = \"get\" action = \"clearstore\">"
//					+ "<input type = \"submit\" value =\"Continue\">"
//					+ "</form>");
			
			double total = 0;
			writer.write("<table border=\"2\">"
					+ "<tr>"
					+ "<th colspan=4>Your Cart</th>"
					+ "</tr>"
					+ "<tr>"
					+ "<th width=\"157\">Product</th>"
					+ "<th width=\"98\">Quantity</th>"
					+ "<th width=\"127\">Price</th>"
					+ "<th width=\"128\">Sum</th>"
					+ "</tr>");				
			for(LineItem item : order.getLineItemArray()) {
				total+=item.getQuantity()*item.getProduct().getPrice();
				writer.write("<tr>"
						+ "<td>" + item.getProduct().getName() + "</td>"
						+ "<td>" + item.getQuantity() + "</td>"
						+ "<td>$" + item.getProduct().getPrice() + "</td>"
						+ "<td>$" + df.format(item.getQuantity()*item.getProduct().getPrice()) + "</td>"
						+ "</tr>");
			}
			writer.write(""
					+ "<td colspan=\"2\">Total</td>"
					+ "<td colspan=\"2\">$" + df.format(total)+ "</td>"
					+ "</table>");
			writer.write(""
					+ "<form method=\"get\" action = \"/Learned_Brandon_P1/clearstore\">"
					+ "<input type = \"submit\" style=\"width: 555px;\" value = \"Continue\">"
					+ "</form>");
			
			writer.write("</body></html>");
			
			
			break;
		default:
			break;
		}

	}
	
	
}
