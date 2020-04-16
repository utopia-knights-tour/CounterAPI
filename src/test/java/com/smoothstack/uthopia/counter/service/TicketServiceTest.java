package com.smoothstack.uthopia.counter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.smoothstack.uthopia.counter.model.Customer;
import com.smoothstack.uthopia.counter.model.Flight;
import com.smoothstack.uthopia.counter.model.Ticket;
import com.smoothstack.uthopia.counter.repository.CustomerRepository;
import com.smoothstack.uthopia.counter.repository.FlightRepository;
import com.smoothstack.uthopia.counter.repository.TicketRepository;

public class TicketServiceTest {
	
	@InjectMocks
	private TicketService ticketService;
	
	@Mock
	private TicketRepository ticketRepo;
	
	@Mock
	private CustomerRepository customerRepo;
	
	@Mock
	private FlightRepository flightRepo;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testSaveTicketSuccess() {
		Ticket testTicket = new Ticket();
		Flight flight = new Flight();
		flight.setFlightId(10);
		Customer customer = new Customer();
		customer.setCustomerId(10);
		testTicket.setFlight(flight);
		testTicket.setCustomer(customer);
		when(customerRepo.existsById(eq(customer.getCustomerId()))).thenReturn(true);
		when(flightRepo.existsById(eq(flight.getFlightId()))).thenReturn(true);
		ticketService.saveTicket(testTicket);
		verify(ticketRepo, times(1)).save(testTicket);
	}
	
	@Test
	public void testSaveTicketFailure() {
		Ticket testTicket = new Ticket();
		Flight flight = new Flight();
		flight.setFlightId(10);
		Customer customer = new Customer();
		testTicket.setFlight(flight);
		testTicket.setCustomer(customer);
		ticketService.saveTicket(testTicket);
		assertEquals(ticketService.saveTicket(testTicket), null);
	}
	
	@Test
	public void testReadTicketsByCustomerSuccess() {
		Ticket testTicket = new Ticket();
		testTicket.setTicketId(14);
		testTicket.setCanceled(false);
		Customer customer = new Customer();
		customer.setCustomerId(10);
		testTicket.setCustomer(customer);
		List<Ticket> tickets = Collections.singletonList(testTicket);
		when(customerRepo.existsById(customer.getCustomerId())).thenReturn(true);
		when(ticketRepo.findTickets(eq(10), any())).thenReturn(tickets);
		assertEquals(ticketService.readTicketsByCustomer(customer.getCustomerId(), 0, 10), tickets);
	}
	
	@Test
	public void testReadTicketsByCustomerFailure() {
		when(customerRepo.existsById(10)).thenReturn(false);
		assertEquals(ticketService.readTicketsByCustomer(10, 0, 10), null);
	}
	
	@Test
	public void returnTicketSuccess() {
		when(ticketRepo.existsById(10)).thenReturn(true);
		assertEquals(ticketService.returnTicket(10), true);
	}
	
	@Test
	public void returnTicketFailure() {
		when(ticketRepo.existsById(10)).thenReturn(false);
		assertEquals(ticketService.returnTicket(10), false);
	}

}
