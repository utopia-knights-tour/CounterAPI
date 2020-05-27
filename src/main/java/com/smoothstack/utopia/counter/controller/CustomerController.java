package com.smoothstack.utopia.counter.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smoothstack.utopia.counter.exception.InvalidIdException;
import com.smoothstack.utopia.counter.model.Customer;
import com.smoothstack.utopia.counter.model.PageDetails;
import com.smoothstack.utopia.counter.service.CustomerService;

@RestController
@RequestMapping("/counter/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@PostMapping
	public ResponseEntity<Void> addCustomer(@Valid @RequestBody Customer customer) {
		Customer savedCustomer = customerService.addCustomer(customer);
		// location header
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCustomer.getCustomerId())
                .toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping
	public ResponseEntity<PageDetails<Customer>> readCustomers(@RequestParam(required = false) String customerName,
			@RequestParam(required = false) String customerAddress, @RequestParam(required = false) String customerPhone,
			@RequestParam Integer page, @RequestParam Integer pagesize) {
		PageDetails<Customer> customersPage = customerService.readCustomers(customerName, customerAddress, customerPhone,
				page-1, pagesize);
		return ResponseEntity.ok().body(customersPage);
	}
	
	@GetMapping("{customerId}")
	public ResponseEntity<Customer> readCustomer(@PathVariable Integer customerId) throws InvalidIdException {
		Customer customer = customerService.readCustomer(customerId);
		return ResponseEntity.ok().body(customer);
	}

	@PutMapping("{customerId}")
	public ResponseEntity<Void> updateCustomer(@Valid @RequestBody Customer customer, @PathVariable Integer customerId)
			throws InvalidIdException {
		customer.setCustomerId(customerId);
		customerService.updateCustomer(customer);
		return ResponseEntity.noContent().build();
	}
	

}
