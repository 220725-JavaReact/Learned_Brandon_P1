package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.models.Customer;
import com.revature.models.LineItem;
import com.revature.models.Order;
import com.revature.models.Product;
import com.revature.models.StoreFront;
import com.revature.utils.ConnectionUtil;

public class OrderDao implements Dao<Order>{

	@Override
	public void addInstance(Order instance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Order> getAllInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order getByAttribute(String attribute) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void upDateOrdersAndStoreItems(Customer customer, Order order, StoreFront storeFront) {
		
		StringBuilder query = new StringBuilder();
		query.append("begin;\r\n");
		query.append(" insert into orders(customer_id, storefront_id) values (" 
		+ customer.getId() + ", " + storeFront.getId() + ");\r\n");
		for(LineItem lineItem : order.getLineItemArray()) {
			query.append(
					" insert into order_items(order_id, product_id, quantity) values "
					+ "((select max(order_id) from orders), " + lineItem.getProduct().getId() 
					+ ", " + lineItem.getQuantity() + ");\r\n");
			query.append(
					" update storefront_items set quantity = quantity-" + lineItem.getQuantity() 
					+ " where storefront_id = " + storeFront.getId() + " and product_id =" + lineItem.getProduct().getId() + ";\r\n"
					
					);
		}
		query.append("commit;");
		
		try(Connection connection = ConnectionUtil.getConnection()){
			PreparedStatement pstmt = connection.prepareStatement(query.toString()); //conevert to prepared statement
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Something went wrong");
			//e.printStackTrace();
		}
		
	}

	public ArrayList<Order> getAllById(int id) {
		ArrayList<Order> orders = new ArrayList<>(); //create line item, add it to order
		
		try(Connection connection = ConnectionUtil.getConnection()){
			String query = "select * from orders \r\n"
					+ "natural inner join order_items \r\n"
					+ "natural inner join products \r\n"
					+ "natural inner join storefronts\r\n"
					+ "where customer_id = ?\r\n"
					+ "order by order_id;";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			boolean doNext = true;
	
			while(rs.next()) {
				for(Order order : orders) {
					if(order.getId() == rs.getInt("order_id")) {
						Product product = new Product(rs.getInt("product_id"), 
								rs.getString("product_name"),
								rs.getDouble("price"), 
								rs.getString("description"), 
								rs.getString("quality"));
						LineItem lineItem = new LineItem(product, rs.getInt("quantity"));
						order.addLineItem(lineItem);
						doNext = false;
					} 
				}
				if(doNext == true) {
					Order order = new Order(rs.getInt("order_id"), rs.getString("storefront_name"));
					Product product = new Product(rs.getInt("product_id"), 
							rs.getString("product_name"),
							rs.getDouble("price"), 
							rs.getString("description"), 
							rs.getString("quality"));
					LineItem lineItem = new LineItem(product, rs.getInt("quantity"));
					order.addLineItem(lineItem);
					orders.add(order);
				} else {
					doNext = true;
				}
				
			}	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Something went wrong");
			return null;
		}
		return orders;
	}
}
