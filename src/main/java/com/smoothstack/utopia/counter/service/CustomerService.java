package com.smoothstack.utopia.counter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smoothstack.utopia.counter.exception.InvalidIdException;
import com.smoothstack.utopia.counter.model.Customer;
import com.smoothstack.utopia.counter.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepo;
	
	public void saveCustomer(Customer customer) {
		customerRepo.save(customer);
	}
	
	public List<Customer> readCustomers(String customerName, Integer page, Integer pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		return customerRepo.findByCustomerNameStartsWith(customerName != null? customerName: "", pageable);
	}
	
	public void updateCustomer(Customer customer) throws InvalidIdException {
		if (!customerRepo.existsById(customer.getCustomerId())) {
			throw new InvalidIdException("That ID is invalid.");
		}
		customerRepo.save(customer);
	}

}
