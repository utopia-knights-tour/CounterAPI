package com.smoothstack.uthopia.counter.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import com.smoothstack.uthopia.counter.exception.InvalidIdException;
import com.smoothstack.uthopia.counter.model.Customer;
import com.smoothstack.uthopia.counter.repository.CustomerRepository;

public class CustomerServiceTest {

	@InjectMocks
	private CustomerService customerService;

	@Mock
	private CustomerRepository customerRepo;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSaveCustomer() {
		Customer testCustomer = new Customer();
		customerService.saveCustomer(testCustomer);
		verify(customerRepo, times(1)).save(testCustomer);
	}

	@Test
	public void testReadCustomers() {
		Customer testCustomer = new Customer();
		testCustomer.setCustomerName("John Doe");
		testCustomer.setCustomerPhone("(323) 312-6364");
		testCustomer.setCustomerAddress("383 Honey Creek Ave. Mentor, OH 44060");
		testCustomer.setCustomerId(12);
		List<Customer> customers = Collections.singletonList(testCustomer);
		when(customerRepo.findByCustomerNameStartsWith(eq("John"), any())).thenReturn(customers);
		assertEquals(customerService.readCustomers("John", 0, 10), customers);
	}

	@Test
	public void testUpdateCustomerSuccess() {
		Customer testCustomer = new Customer();
		testCustomer.setCustomerId(10);
		when(customerRepo.existsById(10)).thenReturn(true);
		customerService.saveCustomer(testCustomer);
		verify(customerRepo, times(1)).save(testCustomer);
	}

	@Test
	public void testUpdateCustomerFailure() {
		Customer testCustomer = new Customer();
		testCustomer.setCustomerId(10);
		when(customerRepo.existsById(10)).thenReturn(false);
		InvalidIdException ex = assertThrows(InvalidIdException.class,
				() -> customerService.updateCustomer(testCustomer));
		assertEquals(ex.getMessage(), "That ID is invalid.");
	}

}
