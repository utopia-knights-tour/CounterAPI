package com.smoothstack.uthopia.counter.controller;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyInt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.smoothstack.uthopia.counter.model.Customer;
import com.smoothstack.uthopia.counter.service.CustomerService;

public class CustomerControllerTest {

	@InjectMocks
	private CustomerController customerController;
	
	@Mock
	private CustomerService customerService;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testReadCustomers() {
		Customer testCustomer = new Customer();
		testCustomer.setCustomerName("John Doe");
		testCustomer.setCustomerPhone("(323) 312-6364");
		testCustomer.setCustomerAddress("383 Honey Creek Ave. Mentor, OH 44060");
		testCustomer.setCustomerId(12);
		List<Customer> customers = Collections.singletonList(testCustomer);
		when(customerService.readCustomers(eq(testCustomer.getCustomerName()), anyInt(), anyInt()))
			.thenReturn(customers);
		assertEquals(customerController.readCustomers(testCustomer.getCustomerName(), 0, 10),
				new ResponseEntity<List<Customer>>(customers, HttpStatus.OK));
	}
	
	@Test
	public void testAddCustomer() {
		Customer testCustomer = new Customer();
		testCustomer.setCustomerName("John Doe");
		testCustomer.setCustomerPhone("(323) 312-6364");
		testCustomer.setCustomerAddress("383 Honey Creek Ave. Mentor, OH 44060");
		assertEquals(customerController.addCustomer(testCustomer), new ResponseEntity<Void>(HttpStatus.CREATED));
	}
	
	@Test
	public void TestUpdateCustomerSuccess() {
		Customer testCustomer = new Customer();
		testCustomer.setCustomerName("John Doe");
		testCustomer.setCustomerPhone("(323) 312-6364");
		testCustomer.setCustomerAddress("383 Honey Creek Ave. Mentor, OH 44060");
		testCustomer.setCustomerId(10);
		when(customerService.updateCustomer(eq(testCustomer))).thenReturn(true);
		assertEquals(customerController.updateCustomer(testCustomer, testCustomer.getCustomerId()),
				new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
	}
	
	@Test
	public void TestUpdateCustomerFailure() {
		Customer testCustomer = new Customer();
		testCustomer.setCustomerName("John Doe");
		testCustomer.setCustomerPhone("(323) 312-6364");
		testCustomer.setCustomerAddress("383 Honey Creek Ave. Mentor, OH 44060");
		testCustomer.setCustomerId(10);
		when(customerService.updateCustomer(eq(testCustomer))).thenReturn(false);
		assertEquals(customerController.updateCustomer(testCustomer, testCustomer.getCustomerId()),
				new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}
}
