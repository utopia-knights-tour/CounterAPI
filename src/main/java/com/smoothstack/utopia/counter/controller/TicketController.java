package com.smoothstack.utopia.counter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smoothstack.utopia.counter.exception.InvalidIdException;
import com.smoothstack.utopia.counter.model.Ticket;
import com.smoothstack.utopia.counter.service.TicketService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/counter")
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	
	@GetMapping(path = "/tickets")
	public ResponseEntity<List<Ticket>> readTickets(@RequestParam Integer customerId, @RequestParam Integer page,
			@RequestParam Integer pagesize) throws InvalidIdException {
		List<Ticket> tickets = ticketService.readTicketsByCustomer(customerId, page-1, pagesize);
		return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
	}
	
}
