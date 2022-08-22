package com.revature.models;

import java.util.ArrayList;

public class Customer {

	int id;
	String fName;
	String lName;
	String username;
	String password;
	String email;
	ArrayList<Order> orderList = new ArrayList<>();
	
	public Customer(int id, String fName, String lName, String username, String password, String email) {
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public Customer(String fName, String lName, String username, String password, String email) {
		this.fName = fName;
		this.lName = lName;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public void addOrder(Order order) {
		orderList.add(order);
	}
	
	public void printOrder() {
		for(Order order : orderList) {
			System.out.println(order.toString());
		}
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(ArrayList<Order> orderList) {
		this.orderList = new ArrayList<>();
		for(Order order : orderList) {
			this.orderList.add(order);
		}
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", fName=" + fName + ", lName=" + lName + ", username=" + username + ", password="
				+ password + ", email=" + email + ", orderList=" + orderList + "]";
	}
	
	
	
	
	
	
	
}
