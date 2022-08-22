package com.revature.models;

public class LineItem {

	int id;
	Product product;
	int quantity;
	
	public LineItem(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	public LineItem(int id, Product product, int quantity) {
		this.id = id;
		this.product = product;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void increaseQuanity(int amount) {
		this.quantity += amount;
	}
	
	public void decreaseQuanity(int amount) {
		this.quantity -= amount;
	}

	@Override
	public String toString() {
		return "LineItem [id=" + id + ", product=" + product + ", quantity=" + quantity + "]";
	}

	
	
	
}
