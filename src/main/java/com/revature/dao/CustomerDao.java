package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.models.Customer;
import com.revature.utils.ConnectionUtil;

public class CustomerDao implements Dao<Customer> {

	@Override
	public void addInstance(Customer instance) {
		try(Connection connection = ConnectionUtil.getConnection()){
			String query = "insert into customers (first_name, last_name, username, pw, email) values (?, ?, ?, ?, ?)";
			PreparedStatement pstmt = connection.prepareStatement(query); //conevert to prepared statement
			pstmt.setString(1, instance.getfName());
			pstmt.setString(2, instance.getlName());
			pstmt.setString(3, instance.getUsername());
			pstmt.setString(4, instance.getPassword());
			pstmt.setString(5, instance.getEmail());
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Something went wrong");
			//e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Customer> getAllInstance() {
		ArrayList<Customer> customers = new ArrayList<>();
		try(Connection connection = ConnectionUtil.getConnection()){
			String query = "select * from customers";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				customers.add(new Customer(rs.getInt("customer_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("username"), rs.getString("pw"), rs.getString("email")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return customers;
	}

	@Override
	public Customer getByAttribute(String attribute) {
		try(Connection connection = ConnectionUtil.getConnection()){
			String query = "select * from customers where username = ?";
			PreparedStatement pstmt = connection.prepareStatement(query); //conevert to prepared statement
			pstmt.setString(1, attribute);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return new Customer(rs.getInt("customer_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("username"), rs.getString("pw"), rs.getString("email"));
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return null;
	}

	@Override
	public Customer getById(int id) {
		try(Connection connection = ConnectionUtil.getConnection()){
			String query = "select * from customers where customer_id = ?";
			PreparedStatement pstmt = connection.prepareStatement(query); //conevert to prepared statement
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return new Customer(rs.getInt("customer_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("username"), rs.getString("pw"), rs.getString("email"));
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return null;
	}

}
