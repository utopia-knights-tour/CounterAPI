package com.smoothstack.uthopia.counter.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smoothstack.uthopia.counter.model.Ticket;
import com.smoothstack.uthopia.counter.service.TicketService;

@RestController
@RequestMapping(path = "/counter")
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	
	@GetMapping(path = "/tickets")
	public ResponseEntity<List<Ticket>> readTickets(@RequestParam Integer customerId, @RequestParam Integer page,
			@RequestParam Integer pageSize) {
		List<Ticket> tickets = ticketService.readTicketsByCustomer(customerId, page, pageSize);
		if (tickets != null) {
			return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
		}
		return new ResponseEntity<List<Ticket>>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(path = "/tickets")
	public ResponseEntity<Void> addTicket(@Valid @RequestBody Ticket ticket) {
		Ticket savedTicket = ticketService.saveTicket(ticket);
		if (savedTicket == null) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@DeleteMapping(path = "/tickets")
	public ResponseEntity<Void> deleteTicket(@RequestParam Integer ticketId) {
		Boolean success = ticketService.returnTicket(ticketId);
		if (success) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

}
