package com.smoothstack.utopia.counter.controller;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.smoothstack.utopia.counter.controller.TicketController;
import com.smoothstack.utopia.counter.exception.InvalidIdException;
import com.smoothstack.utopia.counter.model.Agency;
import com.smoothstack.utopia.counter.model.Customer;
import com.smoothstack.utopia.counter.model.Flight;
import com.smoothstack.utopia.counter.model.PageDetails;
import com.smoothstack.utopia.counter.model.Ticket;
import com.smoothstack.utopia.counter.service.TicketService;

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
	public void testReadTickets() throws InvalidIdException {
		Ticket ticket = new Ticket();
		ticket.setTicketId(14);
		ticket.setCanceled(false);
		ticket.setFlight(new Flight());
		ticket.setPaymentId("Test Payment ID");
		Agency agency = new Agency();
		agency.setAgencyId(213);
		agency.setAgencyName("Test Name");
		agency.setAgencyAddress("Test Address");
		agency.setAgencyPhone("Test Phone");
		ticket.setAgency(agency);
		Customer customer = new Customer();
		customer.setCustomerId(10);
		ticket.setCustomer(customer);
		List<Ticket> tickets = Collections.singletonList(ticket);
		PageDetails<Ticket> ticketsPage = new PageDetails<Ticket>(tickets, (long) 1);
		when(ticketService.readTicketsByCustomer(customer.getCustomerId(), 0, 10)).thenReturn(ticketsPage);
		assertEquals(ticketController.readTickets(customer.getCustomerId(), 1, 10), 
				ResponseEntity.ok().body(ticketsPage));
	}
	
}
