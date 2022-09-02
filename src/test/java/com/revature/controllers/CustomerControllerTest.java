package com.revature.controllers;


import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.dao.CustomerDao;
import com.revature.models.Customer;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

	@Mock
	private CustomerDao customerDao;
	
	private ArrayList<Customer> mockCustomers;
		
	@BeforeAll
	public void setup() {
		MockitoAnnotations.openMocks(this);
		this.customerDao = new CustomerDao();
		this.mockCustomers = new ArrayList<>();
		this.mockCustomers.add(new Customer("Brandon", "Learned", "blearned", "password", "email@email.com"));
		this.mockCustomers.add(new Customer("Alex", "Learned", "subwoofer", "password", "allypup@email.com"));
		this.mockCustomers.add(new Customer("Iroh", "Learned", "meowmix", "password", "meowmeow@email.com"));
	}
	
	@Test
	public void testFindAllCustomers() {
		Mockito.when(this.customerDao.getAllInstance()).thenReturn(this.mockCustomers);
		ArrayList<Customer> customers = this.customerDao.getAllInstance();
		Assertions.assertEquals(3, customers.size());
	}
	
	@Test
	public void testFindCustomerById() {
		int id = 1;
		Mockito.when(this.customerDao.getById(id)).thenReturn(this.mockCustomers.get(id-1));
		Customer customer = this.customerDao.getById(id);
		Assertions.assertEquals("blearned", customer.getUsername());
	}
	
	@Test
	public void testFindCustomerByUsername() {
		String username = "subwoofer";
		Customer returnCustomer = null;
		for(Customer customer : mockCustomers) {
			if(customer.getUsername().equals(username)) {
				returnCustomer = customer;
			}
		}
		Mockito.when(this.customerDao.getByAttribute(username)).thenReturn(returnCustomer);
		Customer customer = this.customerDao.getByAttribute(username);
		Assertions.assertEquals("subwoofer", customer.getUsername());
	}

	@Test
	public void testFindCustomerByUsernameFName() {
		String username = "subwoofer";
		Customer returnCustomer = null;
		for(Customer customer : mockCustomers) {
			if(customer.getUsername().equals(username)) {
				returnCustomer = customer;
			}
		}
		Mockito.when(this.customerDao.getByAttribute(username)).thenReturn(returnCustomer);
		Customer customer = this.customerDao.getByAttribute(username);
		Assertions.assertEquals("Alex", customer.getfName());
	}
	
	@Test
	public void testFindCustomerByUsernameLName() {
		String username = "subwoofer";
		Customer returnCustomer = null;
		for(Customer customer : mockCustomers) {
			if(customer.getUsername().equals(username)) {
				returnCustomer = customer;
			}
		}
		Mockito.when(this.customerDao.getByAttribute(username)).thenReturn(returnCustomer);
		Customer customer = this.customerDao.getByAttribute(username);
		Assertions.assertEquals("Learned", customer.getlName());
	}
}
