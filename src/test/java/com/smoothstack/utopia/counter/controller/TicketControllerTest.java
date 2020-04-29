package com.smoothstack.utopia.counter.controller;

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

import com.smoothstack.utopia.counter.controller.TicketController;
import com.smoothstack.utopia.counter.exception.InvalidIdException;
import com.smoothstack.utopia.counter.model.Agency;
import com.smoothstack.utopia.counter.model.Customer;
import com.smoothstack.utopia.counter.model.Flight;
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
		Ticket testTicket = new Ticket();
		testTicket.setTicketId(14);
		testTicket.setCanceled(false);
		testTicket.setFlight(new Flight());
		testTicket.setPaymentId("dqkw131ed");
		Agency agency = new Agency();
		agency.setAgencyId(213);
		agency.setAgencyName("Test Name");
		agency.setAgencyAddress("Test Address");
		agency.setAgencyPhone("Test Phone");
		testTicket.setAgency(agency);
		Customer customer = new Customer();
		customer.setCustomerId(10);
		testTicket.setCustomer(customer);
		List<Ticket> tickets = Collections.singletonList(testTicket);
		when(ticketService.readTicketsByCustomer(eq(customer.getCustomerId()), eq(0), eq(10))).thenReturn(tickets);
		assertEquals(ticketController.readTickets(customer.getCustomerId(), 1, 10), 
				new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK));
	}
	
}
