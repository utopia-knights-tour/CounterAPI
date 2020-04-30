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
	
	public void saveCustomer(Customer customer) {
		customerRepo.save(customer);
	}
	
	public PageDetails<Customer> readCustomers(String customerName, String customerAddress, 
			String customerPhone, Integer page, Integer pagesize) {
		Pageable pageable = PageRequest.of(page, pagesize);
		Page<Customer> customersPage = customerRepo.findCustomers(pageable, customerName != null? customerName: "", 
				customerAddress != null? customerAddress: "", customerPhone != null? customerPhone: "");
		return new PageDetails<Customer>(customersPage.getContent(), customersPage.getTotalElements());
	}
	
	public Customer readCustomer(Integer customerId) throws InvalidIdException {
		Optional<Customer> customer = customerRepo.findById(customerId);
		if (!customer.isPresent()) {
			throw new InvalidIdException("That ID is invalid.");
		}
		return customer.get();
	}
	
	public void updateCustomer(Customer customer) throws InvalidIdException {
		if (!customerRepo.existsById(customer.getCustomerId())) {
			throw new InvalidIdException("That ID is invalid.");
		}
		customerRepo.save(customer);
	}

}
