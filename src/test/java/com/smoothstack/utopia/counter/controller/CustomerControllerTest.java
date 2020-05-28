package com.smoothstack.utopia.counter.controller;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import com.smoothstack.utopia.counter.controller.CustomerController;
import com.smoothstack.utopia.counter.exception.InvalidIdException;
import com.smoothstack.utopia.counter.model.Customer;
import com.smoothstack.utopia.counter.model.PageDetails;
import com.smoothstack.utopia.counter.service.CustomerService;

public class CustomerControllerTest {

	@InjectMocks
	private CustomerController customerController;
	
	@Mock
	private CustomerService customerService;
	
	private MockHttpServletRequest request;
	
	@BeforeEach
	public void setup() {
		this.request = new MockHttpServletRequest();
		this.request.setScheme("http");
		this.request.setServerName("localhost");
		this.request.setServerPort(-1);
		this.request.setRequestURI("/customers");
		this.request.setContextPath("/customers");
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testReadCustomers() {
		String name = "John Doe";
		String phone = "(323) 312-6364";
		String address = "383 Honey Creek Ave. Mentor, OH 44060";
		Customer customer = new Customer();
		customer.setCustomerName(name);
		customer.setCustomerPhone(phone);
		customer.setCustomerAddress(address);
		customer.setCustomerId(12);
		List<Customer> customers = Collections.singletonList(customer);
		PageDetails<Customer> customersPage = new PageDetails<Customer>(customers, (long) 1);
		when(customerService.readCustomers(name, address, phone, 0, 10)).thenReturn(customersPage);
		assertEquals(customerController.readCustomers(name, address, phone, 1, 10), 
				ResponseEntity.ok().body(customersPage));
	}
	
	@Test 
	public void testReadCustomer() throws InvalidIdException {
		Customer customer = new Customer();
		customer.setCustomerId(12);
		when(customerService.readCustomer(12)).thenReturn(customer);
		assertEquals(customerController.readCustomer(12),
				ResponseEntity.ok().body(customer));
	}
	
	@Test
	public void TestUpdateCustomer() throws InvalidIdException {
		Customer customer = new Customer();
		customer.setCustomerName("John Doe");
		customer.setCustomerPhone("(323) 312-6364");
		customer.setCustomerAddress("383 Honey Creek Ave. Mentor, OH 44060");
		customer.setCustomerId(10);
		assertEquals(customerController.updateCustomer(customer, 10),
				ResponseEntity.noContent().build());
	}
	
	@Test 
	public void TestAddCustomer() {
		Customer customer = new Customer();
		customer.setCustomerName("John Doe");
		customer.setCustomerPhone("(323) 312-6364");
		customer.setCustomerAddress("383 Honey Creek Ave. Mentor, OH 44060");
		customer.setCustomerId(20);
		when(customerService.addCustomer(customer)).thenReturn(customer);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(this.request));
		assertEquals(customerController.addCustomer(customer).getStatusCode(), HttpStatus.CREATED);
	}
	
}
