package com.smoothstack.utopia.counter.controller;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
		PageDetails<Customer> customersPage = new PageDetails<Customer>(customers, (long) 1);
		when(customerService.readCustomers(any(), any(), any(), anyInt(), anyInt())).thenReturn(customersPage);
		assertEquals(customerController.readCustomers(testCustomer.getCustomerName(), testCustomer.getCustomerAddress(),
				testCustomer.getCustomerPhone(), 1, 10), 
				new ResponseEntity<PageDetails<Customer>>(customersPage, HttpStatus.OK));
	}
	
	@Test void testReadCustomer() throws InvalidIdException {
		Customer testCustomer = new Customer();
		testCustomer.setCustomerId(12);
		when(customerService.readCustomer(12)).thenReturn(testCustomer);
		assertEquals(customerController.readCustomer(12),
				new ResponseEntity<Customer>(testCustomer, HttpStatus.OK));
	}
	
	@Test
	public void TestUpdateCustomer() throws InvalidIdException {
		Customer testCustomer = new Customer();
		testCustomer.setCustomerName("John Doe");
		testCustomer.setCustomerPhone("(323) 312-6364");
		testCustomer.setCustomerAddress("383 Honey Creek Ave. Mentor, OH 44060");
		testCustomer.setCustomerId(10);
		assertEquals(customerController.updateCustomer(testCustomer, testCustomer.getCustomerId()),
				new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
	}
	
	@Test 
	public void TestAddCustomer() {
		Customer customer = new Customer();
		assertEquals(customerController.addCustomer(customer), new ResponseEntity<Void>(HttpStatus.CREATED));
	}
	
}
