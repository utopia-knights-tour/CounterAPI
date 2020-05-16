package com.smoothstack.utopia.counter.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.smoothstack.utopia.counter.exception.InvalidIdException;
import com.smoothstack.utopia.counter.model.Customer;
import com.smoothstack.utopia.counter.repository.CustomerRepository;
import com.smoothstack.utopia.counter.service.CustomerService;

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
	public void testReadCustomers() {
		Customer testCustomer = new Customer();
		testCustomer.setCustomerName("John Doe");
		testCustomer.setCustomerPhone("(323) 312-6364");
		testCustomer.setCustomerAddress("383 Honey Creek Ave. Mentor, OH 44060");
		testCustomer.setCustomerId(12);
		List<Customer> customers = Collections.singletonList(testCustomer);
		Page<Customer> customersPage = new PageImpl<Customer>(customers, PageRequest.of(0, 10), 1);
		when(customerRepo.findCustomers(any(), eq("John"), eq("383"), eq("(323)"))).thenReturn(customersPage);
		assertNotNull(customerService.readCustomers("John", "383", "(323)", 0, 10));
	}
	
	@Test
	public void testReadCustomerSuccess() throws InvalidIdException {
		Customer testCustomer = new Customer();
		testCustomer.setCustomerId(10);
		Optional<Customer> customer = Optional.of(testCustomer);
		when(customerRepo.findById(testCustomer.getCustomerId())).thenReturn(customer);
		assertEquals(customerService.readCustomer(10), customer.get());
	}
	
	@Test
	public void testReadCustomerFailure() {
		when(customerRepo.findById(10)).thenReturn(Optional.empty());
		assertThrows(InvalidIdException.class, () -> customerService.readCustomer(10));
	}

	@Test
	public void testAddCustomer() {
		Customer testCustomer = new Customer();
		testCustomer.setCustomerId(10);
		when(customerRepo.existsById(10)).thenReturn(true);
		customerService.addCustomer(testCustomer);
		verify(customerRepo, times(1)).save(testCustomer);
	}

	@Test
	public void testUpdateCustomerSuccess() throws InvalidIdException {
		Customer testCustomer = new Customer();
		testCustomer.setCustomerId(10);
		when(customerRepo.existsById(10)).thenReturn(true);
		customerService.updateCustomer(testCustomer);
		verify(customerRepo, times(1)).save(testCustomer);
	}
	
	@Test
	public void testUpdateCustomerFailure() {
		Customer testCustomer = new Customer();
		testCustomer.setCustomerId(10);
		when(customerRepo.existsById(10)).thenReturn(false);
		InvalidIdException ex = assertThrows(InvalidIdException.class,
				() -> customerService.updateCustomer(testCustomer));
		assertEquals(ex.getMessage(), "Invalid Customer ID.");
	}

}
