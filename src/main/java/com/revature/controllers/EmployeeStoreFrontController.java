package com.revature.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dao.StoreFrontDao;
import com.revature.helpermethods.CSS;
import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.LineItem;
import com.revature.models.Order;
import com.revature.models.StoreFront;

public class EmployeeStoreFrontController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//FIELDS
		final String URI = req.getRequestURI().replace("/Learned_Brandon_P1/", "");
		HttpSession session =  req.getSession();
		PrintWriter writer = resp.getWriter();
		Employee employee = (Employee) session.getAttribute("employee");
		StoreFrontDao storeFrontDao = new StoreFrontDao();
		ArrayList<StoreFront> storeFronts = storeFrontDao.getAllInstance();
		DecimalFormat df = new DecimalFormat("#.00");
		
		//HEADER
		writer.println("<html>");
		if(session.getAttribute("storechoice") != null) {
			writer.println(CSS.getCSS(false));	
		} else {
			writer.println(CSS.getCSS(true));	
		}
		
		//BODY
		writer.println("<body><div class=\"user\">"
				+ "<h2>"
				+ " Logged In As: </br>"
				+ "<img src=\"https://cdn-icons-png.flaticon.com/512/8165/8165854.png\" height=30px width=30px/>"
				+ " " + employee.getUsername().toUpperCase() + " " 
				+ "<img src=\"https://cdn-icons-png.flaticon.com/512/8165/8165854.png\" height=30px width=30px/>"
				+ "</h2></div>");
		
		//NAV BAR
		writer.write("<ul class=\"topnav\">\r\n"
				+ "<li><a href=\"/Learned_Brandon_P1/home\">Log Out</a></li>");
		if(session.getAttribute("storechoice") != null) {
			writer.write("<li><a href=\"/Learned_Brandon_P1/empclearstore\">Return to Store Selection</a></li>");
		} 
		if(session.getAttribute("storechoice") != null && session.getAttribute("previousorders") == null) {
			writer.write("<li><a href=\"/Learned_Brandon_P1/viewstoreorders\">View Previous Orders</a></li>");
		}
		
		
		
//			else {
//			writer.write("<li><a href=\"/Learned_Brandon_P1/empclearstore\">Return to Store Selection</a></li>");
//		}
		
			
		writer.write("</ul>");

		//CHANGE TO VIEW PREVIOUS ORDERS IF STOREFRONT SESSION IS NULL
//		if(session.getAttribute("storechoice") == null && session.getAttribute("previousorders") == null) {
//			writer.write("<form method = \"get\" action = \"/Learned_Brandon_P1/vieworders\">\r\n"
//					+ "			<input type = \"submit\" value = \"View Previous Orders\"/>\r\n"
//					+ "		</form>");
//		} else {
//			writer.print("<form method = \"get\" action = \"clearstore\">"
//					+ "<input type = \"submit\" value =\"Back To Store Selection\">"
//					+ "</form>");
//		}
		
		//BODY OF PAGE CODE
		writer.println("<img src=\"https://i.pinimg.com/236x/a5/05/91/a5059198c216ea8493fb7e19e0d6a26f.jpg\"\r\n</br>");
		if(session.getAttribute("previousorders") != null) {
			
			writer.write("<table border=\"2\"><th>" + ((StoreFront)session.getAttribute("storechoice")).getName() + ""
					+ " Order History</h2></th></table>");
		}
		//STORE SELECT
		if(session.getAttribute("storechoice") == null && session.getAttribute("previousorders") == null) {
			writer.print("<table border=\"2\">"
					+ "<tr><th><b>Select a Store</b></th><tr>"
					+ "<tr>"
					+ "<td><form class=\"fixform\" method = \"get\" action = \"/Learned_Brandon_P1/empsetstore\">"
					+ "            <select  style=\"width: 540px;\" name=\"stores\">");
			for(StoreFront storeFront : storeFronts) {
				writer.write("<option value= " + storeFront.getId() + ">" + storeFront.getName() + "</option>\\r\\n");
			}
			writer.write("            </select>\r\n"
					+ "			<input type = \"submit\"  style=\"width: 540px;\" value = \"Submit Selection\"/>\r\n"
					+ "        </form></td>");
			//VIEW ORDERS
		} 
		else if(session.getAttribute("storechoice") != null && session.getAttribute("previousorders") != null){
			ArrayList<Order> previousOrders = (ArrayList<Order>) session.getAttribute("previousorders");
			for(Order order : previousOrders) {
				double total = 0;
				writer.write("<table border=\"2\">" //pg138
						+ "<tr>"
						+ "<th colspan=\"3\">Order Id: " + order.getId() + "</th>"
						+ "</tr>"
						+ "<tr>"
						+ "<th width=\"200\">Product</th>"
						+ "<th>Quantity</th>"
						+ "<th width=\"130\">Price</th>"
						+ "</tr>");				
				for(LineItem item : order.getLineItemArray()) {
					total += item.getQuantity() * item.getProduct().getPrice();
					writer.write(""
							+ "<tr>"
							+ "<td>" + item.getProduct().getName() + "</td>"
							+ "<td>" + item.getQuantity() + "</td>"
							+ "<td>$" + df.format(item.getProduct().getPrice()*item.getQuantity()) + "</td>"
							+ "</tr>");
				}
				writer.write(""
						+ "<tr>"
						+ "<td>Total:</td>"
						+ "<td></td>"
						+ "<td>$" + df.format(total) + "</td>"
						+ "</tr>");
				writer.write("</table>");
			}
			//SHOW STOREFRONT MENU
		} else {
			StoreFront currentStore = (StoreFront) session.getAttribute("storechoice");
			writer.print(""
					+ "<form method=\"post\" action = \"/Learned_Brandon_P1/updatequantity\">"
					+ "<table border=\"2\">"
					+ "<tr>\r\n"
					+ "    <th colspan=\"3\"><b>Set New Inventory Quantities for " + currentStore.getName() + " Menu</b></th>\r\n"
					+ "  </tr>"
					+ "<tr>\r\n"
					+ "    <th width=\"330\">Product</th>\r\n"
					+ "    <th width=\"90\">Price</th>\r\n"
					+ "    <th width=\"90\">Stock</th>\r\n"
					+ "  </tr>");
			
			for(LineItem lineItem : currentStore.getLineItems()) {
				writer.write("<tr>"
						+ "<td>" + lineItem.getProduct().getName() + "</td>"
						+ "<td>$" + lineItem.getProduct().getPrice() + "</td>"
						+ "<td>" 
						+ "<input type =  \"number\" name=" + lineItem.getProduct().getId() + " size=50 style=\"width: 130px;\" min=0 max=1000 value=" + lineItem.getQuantity() + ">\r\n"
						+ "</td>"
						+ "</tr>");
				
			}
			writer.write("</table>"
					
					+ "<input type = \"submit\" style=\"width: 555px;\" value = \"Submit Stock Changes\">"
					+ "</form>");
			
			//CART CODE
			if(session.getAttribute("updateditems") != null) {
				currentStore = (StoreFront) session.getAttribute("storechoice");
				ArrayList<LineItem> updateItems = (ArrayList<LineItem>) session.getAttribute("updateditems");
				writer.write("<table border=\"2\">"
						+ "<tr>"
						+ "<th colspan=5>Items Updated</th>"
						+ "</tr>"
						+ "<tr>"
						+ "<th width=\"155\">Product</th>"
						+ "<th width=\"100\">Original Quantity</th>"
						+ "<th width=\"100\">Quantity added</th>"
						+ "<th width=\"100\">Quantity removed</th>"
						+ "<th width=\"100\">New Quantity</th>"
						+ "</tr>");	
				for(int i=0; i<updateItems.size(); i++) {
					if(updateItems.get(i).getQuantity() != 0) {
						writer.write("<tr><td>" + updateItems.get(i).getProduct().getName() + "</td>");
						writer.write("<td>" + (currentStore.getLineItems().get(i).getQuantity() - updateItems.get(i).getQuantity()) + "</td>"); //the number added or removed
						if(updateItems.get(i).getQuantity() > 0) {
							writer.write("<td>" + updateItems.get(i).getQuantity() + "</td>"); //the number added or removed
							writer.write("<td>" + 0 + "</td>");
						} else if (updateItems.get(i).getQuantity() < 0){
							writer.write("<td>" + 0 + "</td>"); //the number added or removed
							writer.write("<td>" + updateItems.get(i).getQuantity()/-1 + "</td>");
						} 
						writer.write("<td>" + currentStore.getLineItems().get(i).getQuantity() + "</td></tr>");

					}
				}
				
				
				
			}
//			Order order = (Order) session.getAttribute("currentorder");
//			if(order.getLineItemArray().size() > 0) {
//				double total = 0;
//				writer.write("<table border=\"2\">"
//						+ "<tr>"
//						+ "<th colspan=4>Your Cart</th>"
//						+ "</tr>"
//						+ "<tr>"
//						+ "<th width=\"157\">Product</th>"
//						+ "<th width=\"98\">Quantity</th>"
//						+ "<th width=\"127\">Price</th>"
//						+ "<th width=\"128\">Sum</th>"
//						+ "</tr>");				
//				for(LineItem item : order.getLineItemArray()) {
//					total+=item.getQuantity()*item.getProduct().getPrice();
//					writer.write("<tr>"
//							+ "<td>" + item.getProduct().getName() + "</td>"
//							+ "<td>" + item.getQuantity() + "</td>"
//							+ "<td>$" + item.getProduct().getPrice() + "</td>"
//							+ "<td>$" + df.format(item.getQuantity()*item.getProduct().getPrice()) + "</td>"
//							+ "</tr>");
//				}
				writer.write(""
//						+ "<td colspan=\"2\">Total</td>"
//						+ "<td colspan=\"2\">$" + df.format(total)+ "</td>"
						+ "</table>");
//				writer.write(""
//						+ "<form method=\"post\" action = \"/Learned_Brandon_P1/finalizeorder\">"
//						+ "<input type = \"submit\" style=\"width: 555px;\" value = \"Finalize Order\">"
//						+ "</form>");
//				//Ability to remove from cart
//			}
			
		}
		
		
		writer.println("</body></html>");
	}
	
}
