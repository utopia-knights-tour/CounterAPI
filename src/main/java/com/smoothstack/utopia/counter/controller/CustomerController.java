package com.smoothstack.utopia.counter.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smoothstack.utopia.counter.exception.InvalidIdException;
import com.smoothstack.utopia.counter.model.Customer;
import com.smoothstack.utopia.counter.model.PageDetails;
import com.smoothstack.utopia.counter.service.CustomerService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/counter")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@PostMapping(path = "/customers")
	public ResponseEntity<Void> addCustomer(@Valid @RequestBody Customer customer) {
		customerService.saveCustomer(customer);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@GetMapping(path = "/customers")
	public ResponseEntity<PageDetails<Customer>> readCustomers(@RequestParam(required = false) String customerName,
			@RequestParam(required = false) String customerAddress, @RequestParam(required = false) String customerPhone,
			@RequestParam Integer page, @RequestParam Integer pagesize) {
		PageDetails<Customer> customers = customerService.readCustomers(customerName, customerAddress, customerPhone,
				page-1, pagesize);
		return new ResponseEntity<PageDetails<Customer>>(customers, HttpStatus.OK);
	}
	
	@GetMapping(path = "/customers/{customerId}")
	public ResponseEntity<Customer> readCustomer(@PathVariable Integer customerId) throws InvalidIdException {
		Customer customer = customerService.readCustomer(customerId);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@PutMapping(path = "/customers/{customerId}")
	public ResponseEntity<Void> updateCustomer(@Valid @RequestBody Customer customer, @PathVariable Integer customerId)
			throws InvalidIdException {
		customer.setCustomerId(customerId);
		customerService.updateCustomer(customer);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	

}
