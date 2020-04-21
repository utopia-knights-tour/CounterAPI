package com.smoothstack.utopia.counter.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.smoothstack.utopia.counter.service.CustomerService;

@RestController
@RequestMapping(path = "/counter")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping(path = "/customers")
	public ResponseEntity<List<Customer>> readCustomers(@RequestParam(required = false) String customerName,
			@RequestParam Integer page, @RequestParam Integer pageSize) {
		List<Customer> customers = customerService.readCustomers(customerName, page, pageSize);
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}

	@PostMapping(path = "/customers")
	public ResponseEntity<Void> addCustomer(@Valid @RequestBody Customer customer) {
		customerService.saveCustomer(customer);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@PutMapping(path = "/customers/{customerId}")
	public ResponseEntity<Void> updateCustomer(@Valid @RequestBody Customer customer, @PathVariable Integer customerId)
			throws InvalidIdException {
		customer.setCustomerId(customerId);
		customerService.updateCustomer(customer);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
