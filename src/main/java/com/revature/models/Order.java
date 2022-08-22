package com.revature.models;

import java.text.DecimalFormat; 
import java.util.ArrayList;

public class Order {

	int id;
	ArrayList<LineItem> lineItemArray = new ArrayList<>();
	String storeAddress;
	double totalPriceOfItems;
	
	public Order(String storeAddress) {
		this.storeAddress = storeAddress;
		this.totalPriceOfItems = 0;
	}
	
	public Order(int id, String storeAddress) {
		this.id = id;
		this.storeAddress = storeAddress;
		this.totalPriceOfItems = 0;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void addLineItem(LineItem lineItem) {
		lineItemArray.add(lineItem);
		this.totalPriceOfItems += lineItem.getQuantity() * lineItem.getProduct().getPrice();
	}
	
	public void removeLineItem(LineItem lineItem) {
		this.totalPriceOfItems -= lineItem.getQuantity() * lineItem.getProduct().getPrice();
		lineItemArray.remove(lineItem);
	}
	
	public void resetLineItemArray() {
		this.lineItemArray = new ArrayList<LineItem>();
	}
	
	
	public void increaseLineItemQuantity(Product duckie, int amount) {
		for(LineItem item : lineItemArray) {
			if(item.getProduct().getName().equals(duckie.getName())) {
				item.increaseQuanity(amount);
				this.totalPriceOfItems += amount * item.getProduct().getPrice();
			}
		}
	}
	
	public void removeFromLineItemQuantity(Product duckie, int amount) {
		for(LineItem item : lineItemArray) {
			if(item.getProduct() == duckie) {
				item.decreaseQuanity(amount);
				this.totalPriceOfItems -= amount * item.getProduct().getPrice();
			}
			if(item.getQuantity() <= 0) {
				if(lineItemArray.size() == 1) {
					this.lineItemArray = new ArrayList<LineItem>();
				} else {
					removeLineItem(item);
				}		
			}			
		}
	}
	
	public boolean containsDuckie(Product duckie) {
		for(LineItem item : lineItemArray) {
			if(item.getProduct().getName().equals(duckie.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<LineItem> getLineItemArray() {
		return lineItemArray;
	}

	public void setLineItemArray(ArrayList<LineItem> lineItemArray) {
		this.lineItemArray = lineItemArray;
	}

	public double getTotalPriceOfItems() {
		return totalPriceOfItems;
	}

	public void setTotalPriceOfItems(double totalPriceOfItems) {
		this.totalPriceOfItems = totalPriceOfItems;
	}

	public void printOrder() {
		DecimalFormat df = new DecimalFormat("#.00");
		for(LineItem lineItem : lineItemArray) {
			System.out.println(lineItem.getQuantity() + " x " + lineItem.getProduct().getName() + 
					" - $" + df.format(lineItem.getProduct().getPrice() * lineItem.getQuantity()));
		}
		System.out.println("Total: $" + df.format(this.totalPriceOfItems));
	}
	
	public void printOrderWithTax() {
		DecimalFormat df = new DecimalFormat("#.00");
		for(LineItem lineItem : lineItemArray) {
			System.out.println(lineItem.getQuantity() + " x " + lineItem.getProduct().getName() + 
					" - $" + df.format(lineItem.getProduct().getPrice() * lineItem.getQuantity()));
		}
		System.out.println("Sales tax: $" + df.format(this.totalPriceOfItems * 0.07));
		System.out.println("Items Price: $" + df.format(this.totalPriceOfItems));
		System.out.println("Total: $" + df.format(this.totalPriceOfItems + (this.totalPriceOfItems * 0.07)));
	}
	
	public String getOrderForToString() {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<lineItemArray.size();i++) {
			sb.append(lineItemArray.get(i).quantity + " x " + lineItemArray.get(i).product.name);
			if(i<lineItemArray.size()-1) {
				sb.append("\n");
			}
			sb.toString();
		}
		return sb.toString();
	}

	public String getStoreAddress() {
		return storeAddress;
	}

	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", lineItemArray=" + lineItemArray + ", storeAddress=" + storeAddress
				+ ", totalPriceOfItems=" + totalPriceOfItems + "]";
	}

	
	
	
	
}
