package com.smoothstack.utopia.counter.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smoothstack.utopia.counter.exception.InvalidIdException;
import com.smoothstack.utopia.counter.model.Customer;
import com.smoothstack.utopia.counter.model.PageDetails;
import com.smoothstack.utopia.counter.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepo;
	
	public Customer addCustomer(Customer customer) {
		return customerRepo.save(customer);
	}
	
	public PageDetails<Customer> readCustomers(String customerName, String customerAddress, 
			String customerPhone, Integer page, Integer pagesize) {
		Pageable pageable = PageRequest.of(page, pagesize);
		customerName = Optional.ofNullable(customerName).orElse("");
		customerAddress = Optional.ofNullable(customerAddress).orElse("");
		customerPhone = Optional.ofNullable(customerPhone).orElse("");
		Page<Customer> customersPage = customerRepo.findCustomers(pageable, customerName, customerAddress, customerPhone);
		return new PageDetails<Customer>(customersPage.getContent(), customersPage.getTotalElements());
	}
	
	public Customer readCustomer(Integer customerId) throws InvalidIdException {
		return customerRepo.findById(customerId)
				.orElseThrow(() -> new InvalidIdException("Invalid Customer ID."));
	}
	
	public void updateCustomer(Customer customer) throws InvalidIdException {
		if (!customerRepo.existsById(customer.getCustomerId())) {
			throw new InvalidIdException("Invalid Customer ID.");
		}
		customerRepo.save(customer);
	}

}
