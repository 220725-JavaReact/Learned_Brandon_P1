package com.revature.models;

public class Product {
	
	int id;
	String name;
	double price;
	String description;
	String quality;
	
	public Product(String name, double price, String description, String quality) {
		this.name = name;
		this.price = price;
		this.description = description;
		this.quality = quality;
	}
	
	public Product(int id, String name, double price, String description, String quality) {
		this.name = name;
		this.price = price;
		this.description = description;
		this.quality = quality;
		this.id = id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description
				+ ", quality=" + quality + "]";
	}


	
	
}
