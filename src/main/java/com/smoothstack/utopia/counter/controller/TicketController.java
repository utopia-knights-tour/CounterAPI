package com.smoothstack.utopia.counter.controller;

import java.util.List;

import javax.transaction.Transactional;
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

import com.smoothstack.utopia.counter.exception.InvalidIdException;
import com.smoothstack.utopia.counter.exception.MissingIdException;
import com.smoothstack.utopia.counter.exception.NoSeatsAvailableException;
import com.smoothstack.utopia.counter.model.Ticket;
import com.smoothstack.utopia.counter.service.TicketService;

@RestController
@RequestMapping(path = "/counter")
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	
	@GetMapping(path = "/tickets")
	public ResponseEntity<List<Ticket>> readTickets(@RequestParam Integer customerId, @RequestParam Integer page,
			@RequestParam Integer pageSize) throws InvalidIdException {
		List<Ticket> tickets = ticketService.readTicketsByCustomer(customerId, page, pageSize);
		return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
	}
	
	@Transactional(rollbackOn = { MissingIdException.class, NoSeatsAvailableException.class })
	@PostMapping(path = "/tickets")
	public ResponseEntity<Void> addTicket(@Valid @RequestBody Ticket ticket)
			throws MissingIdException, NoSeatsAvailableException {
		ticketService.saveTicket(ticket);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@Transactional(rollbackOn= { InvalidIdException.class })
	@DeleteMapping(path = "/tickets")
	public ResponseEntity<Void> deleteTicket(@RequestParam Integer ticketId) throws InvalidIdException {
		ticketService.returnTicket(ticketId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
