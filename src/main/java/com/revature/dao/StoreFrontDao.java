package com.revature.dao;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.models.LineItem;
import com.revature.models.Order;
import com.revature.models.Product;
import com.revature.models.StoreFront;
import com.revature.utils.ConnectionUtil;

public class StoreFrontDao implements Dao<StoreFront> {

	@Override
	public void addInstance(StoreFront instance) {
		// TODO Auto-generated method stub
	}

	@Override
	public ArrayList<StoreFront> getAllInstance() {
		ArrayList<StoreFront> storeFronts = new ArrayList<>();
		try(Connection connection = ConnectionUtil.getConnection()){
			String query = "select * from storefronts";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				storeFronts.add(new StoreFront(rs.getInt("storefront_id"), rs.getString("storefront_name"), rs.getString("address")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return storeFronts;
	}

	@Override
	public StoreFront getByAttribute(String attribute) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoreFront getById(int id) {
		try(Connection connection = ConnectionUtil.getConnection()){
			String query = "select * from storefronts where storefront_id = ?";
			PreparedStatement pstmt = connection.prepareStatement(query); //conevert to prepared statement
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return new StoreFront(rs.getInt("storefront_id"), rs.getString("storefront_name"), rs.getString("address"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return null;
	}
	
	public void initializeAllProducts(StoreFront storeFront){

		ArrayList<LineItem> lineItems = new ArrayList<>(); //create line item, add it to order
		
		try(Connection connection = ConnectionUtil.getConnection()){
			String query = "select * from products\r\n"
					+ "natural inner join storefront_items where storefront_id = ?;";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, storeFront.getId());
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Product product = new Product(rs.getInt("product_id"), 
						rs.getString("product_name"),
						rs.getDouble("price"),
						rs.getString("description"),
						rs.getString("quality"));
				LineItem lineItem = new LineItem(product, rs.getInt("quantity"));
				lineItems.add(lineItem);
			}	

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Something went wrong");
		}

		storeFront.setLineItems(lineItems);

	}
	
	public ArrayList<Order> initializeAllOrders(){
		return null;
	}

}
