package com.revature.models;

import java.util.ArrayList;

public class StoreFront {

	int id;
	String name;
	String address;
	ArrayList<LineItem> lineItems = new ArrayList<>();
	ArrayList<Order> orderList = new ArrayList<>();
	
	public ArrayList<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(ArrayList<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public StoreFront(String name, String address) {
		this.name = name;
		this.address = address;
	}
	
	public StoreFront(int id, String name, String address) {
		this.name = name;
		this.address = address;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void addOrder(Order order) {
		orderList.add(order);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ArrayList<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(ArrayList<Order> orderList) {
		this.orderList = orderList;
	}

	@Override
	public String toString() {
		return "StoreFront [name=" + name + ", address=" + address + ", orderList="
				+ orderList + "]";
	}
	
	
	
	
}
