package com.smoothstack.uthopia.counter.controller;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.smoothstack.uthopia.counter.model.Agency;
import com.smoothstack.uthopia.counter.model.Customer;
import com.smoothstack.uthopia.counter.model.Flight;
import com.smoothstack.uthopia.counter.model.Ticket;
import com.smoothstack.uthopia.counter.service.TicketService;

public class TicketControllerTest {
	
	@InjectMocks
	private TicketController ticketController;
	
	@Mock
	private TicketService ticketService;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testReadTicketsSuccess() {
		Ticket testTicket = new Ticket();
		testTicket.setTicketId(14);
		testTicket.setCanceled(false);
		testTicket.setFlight(new Flight());
		Agency agency = new Agency();
		agency.setAgencyId(213);
		agency.setAgencyName("Tall Tale Travels");
		testTicket.setAgency(agency);
		Customer customer = new Customer();
		customer.setCustomerId(10);
		testTicket.setCustomer(customer);
		List<Ticket> tickets = Collections.singletonList(testTicket);
		when(ticketService.readTicketsByCustomer(eq(customer.getCustomerId()), eq(0), eq(10))).thenReturn(tickets);
		assertEquals(ticketController.readTickets(customer.getCustomerId(), 0, 10), 
				new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK));
	}
	
	@Test
	public void testReadTicketsFailure() {
		when(ticketService.readTicketsByCustomer(eq(10), eq(0), eq(10))).thenReturn(null);
		assertEquals(ticketController.readTickets(10, 0, 10), 
				new ResponseEntity<List<Ticket>>(HttpStatus.NOT_FOUND));
	}
	
	@Test
	public void testAddTicketSuccess() {
		Ticket testTicket = new Ticket();
		Flight flight = new Flight();
		flight.setFlightId(10);
		Customer customer = new Customer();
		customer.setCustomerId(10);
		testTicket.setFlight(flight);
		testTicket.setCustomer(customer);
		when(ticketService.saveTicket(eq(testTicket))).thenReturn(testTicket);
		assertEquals(ticketController.addTicket(testTicket), 
				new ResponseEntity<Void>(HttpStatus.CREATED));
	}
	
	@Test
	public void testAddTicketFailure() {
		Ticket testTicket = new Ticket();
		Flight flight = new Flight();
		Customer customer = new Customer();
		customer.setCustomerId(10);
		testTicket.setFlight(flight);
		testTicket.setCustomer(customer);
		when(ticketService.saveTicket(eq(testTicket))).thenReturn(null);
		assertEquals(ticketController.addTicket(testTicket), 
				new ResponseEntity<Void>(HttpStatus.BAD_REQUEST));
	}
	
	@Test
	public void testDeleteTicketSuccess() {
		when(ticketService.returnTicket(eq(10))).thenReturn(true);
		assertEquals(ticketController.deleteTicket(10), 
				new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
	}
	
	@Test
	public void testDeleteTicketFailure() {
		when(ticketService.returnTicket(eq(10))).thenReturn(false);
		assertEquals(ticketController.deleteTicket(10), 
				new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}

}
