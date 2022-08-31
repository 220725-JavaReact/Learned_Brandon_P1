package com.revature.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dao.OrderDao;
import com.revature.dao.StoreFrontDao;
import com.revature.models.Customer;
import com.revature.models.LineItem;
import com.revature.models.Order;
import com.revature.models.Product;
import com.revature.models.StoreFront;

public class RedirectController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		final String URI = req.getRequestURI().replace("/Learned_Brandon_P1/", "");
		HttpSession session =  req.getSession();
		PrintWriter writer = resp.getWriter();
		StoreFrontDao storeFrontDao = new StoreFrontDao();
		OrderDao orderDao = new OrderDao();
		
		switch (URI) {
		case "home":
			session.invalidate();
			resp.sendRedirect("/Learned_Brandon_P1/");
			break;
		case "setstore":
			int storeid = Integer.parseInt(req.getParameter("stores"));
			StoreFront returnStore = storeFrontDao.getById(storeid);
			storeFrontDao.initializeAllProducts(returnStore);
			session.setAttribute("storechoice", returnStore);
			session.setAttribute("currentorder", new Order(returnStore.getAddress()));
			resp.sendRedirect("/Learned_Brandon_P1/storeselect");
			break;
		case "empsetstore":
			int empstoreid = Integer.parseInt(req.getParameter("stores"));
			StoreFront empreturnStore = storeFrontDao.getById(empstoreid);
			storeFrontDao.initializeAllProducts(empreturnStore);
			session.setAttribute("storechoice", empreturnStore);
			
			resp.sendRedirect("/Learned_Brandon_P1/empstoreselect");
			break;
		case "clearstore":
			session.setAttribute("currentorder", null);
			session.setAttribute("storechoice", null);
			session.setAttribute("previousorders", null);
			resp.sendRedirect("/Learned_Brandon_P1/storeselect");
			break;
		case "empclearstore":
			session.setAttribute("storechoice", null);
			session.setAttribute("previousorders", null);
			session.setAttribute("updateditems", null);
			resp.sendRedirect("/Learned_Brandon_P1/empstoreselect");
			break;
		case "vieworders":
			Customer customer = (Customer) session.getAttribute("customer");
			ArrayList<Order> previousOrders = orderDao.getAllById(customer.getId());
			session.setAttribute("previousorders", previousOrders);
			resp.sendRedirect("/Learned_Brandon_P1/storeselect");
			break;
		case "viewstoreorders":
			ArrayList<Order> orderList = orderDao.getAllByStorefrontId((StoreFront) session.getAttribute("storechoice"));
			session.setAttribute("previousorders", orderList);
			resp.sendRedirect("/Learned_Brandon_P1/empstoreselect");
			break;
		default:	
			break;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		final String URI = req.getRequestURI().replace("/Learned_Brandon_P1/", "");
		HttpSession session =  req.getSession();
		PrintWriter writer = resp.getWriter();
		StoreFrontDao storeFrontDao = new StoreFrontDao();
		
		switch (URI) {
		case "addtoorder":
			Order order = (Order) session.getAttribute("currentorder");
			StoreFront storeFront = (StoreFront) session.getAttribute("storechoice");
			for(LineItem item : storeFront.getLineItems()) {
				String intValue = req.getParameter(String.valueOf(item.getProduct().getId()));
				if(!intValue.equals("0")) {
					int quantityToAdd = Integer.parseInt(intValue);
					Product productToAdd = item.getProduct();
					boolean hasItem = false;
					for(LineItem custItem : order.getLineItemArray()) {
						if(custItem.getProduct().getId() == item.getProduct().getId()) {
							hasItem=true;
							custItem.increaseQuanity(quantityToAdd);
							item.decreaseQuanity(quantityToAdd);
						}
					}
					
					if(!hasItem) {
						order.addLineItem(new LineItem(productToAdd, quantityToAdd));
						item.decreaseQuanity(quantityToAdd);
					}					
				}		
				session.setAttribute("currentorder", order);
				session.setAttribute("storechoice", storeFront);

			}	
			break;
		case "updatequantity":
			ArrayList<LineItem> updateItems = new ArrayList<>();
			StoreFront empStoreFront = (StoreFront) session.getAttribute("storechoice");
			System.out.println(empStoreFront.getName());
			for(LineItem item : empStoreFront.getLineItems()) {
				String intValue = req.getParameter(String.valueOf(item.getProduct().getId()));
				System.out.println(intValue);
				int quantityToSet = Integer.parseInt(intValue);
				updateItems.add(new LineItem(item.getProduct(), quantityToSet - item.getQuantity()));
				item.setQuantity(quantityToSet);
				System.out.println(quantityToSet);
			}	
			storeFrontDao.updateAllLineItems(empStoreFront);
			session.setAttribute("updateditems", updateItems);
			session.setAttribute("storechoice", empStoreFront);
			break;
		default:	
			break;
		}
		
		if(URI.equals("addtoorder")) {
			resp.sendRedirect("/Learned_Brandon_P1/storeselect");
		} else {
//			writer.write("<p>done</p>");
//			wrtier.write(<)
			resp.sendRedirect("/Learned_Brandon_P1/empstoreselect");
		}
		

//		

	}
}
