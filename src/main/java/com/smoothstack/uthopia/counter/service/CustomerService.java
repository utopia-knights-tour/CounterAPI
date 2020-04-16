package com.smoothstack.uthopia.counter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smoothstack.uthopia.counter.model.Customer;
import com.smoothstack.uthopia.counter.repository.CustomerRepository;

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
	
	public Boolean updateCustomer(Customer customer) {
		if (customerRepo.existsById(customer.getCustomerId())) {
			customerRepo.save(customer);
			return true;
		}
		return false;
	}

}
