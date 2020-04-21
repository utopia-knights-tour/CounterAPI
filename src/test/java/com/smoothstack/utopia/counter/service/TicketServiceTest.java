package com.smoothstack.utopia.counter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.smoothstack.utopia.counter.exception.InvalidIdException;
import com.smoothstack.utopia.counter.exception.MissingIdException;
import com.smoothstack.utopia.counter.exception.NoSeatsAvailableException;
import com.smoothstack.utopia.counter.model.Agency;
import com.smoothstack.utopia.counter.model.Customer;
import com.smoothstack.utopia.counter.model.Flight;
import com.smoothstack.utopia.counter.model.Ticket;
import com.smoothstack.utopia.counter.repository.CustomerRepository;
import com.smoothstack.utopia.counter.repository.FlightRepository;
import com.smoothstack.utopia.counter.repository.TicketRepository;
import com.smoothstack.utopia.counter.service.TicketService;

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
	public void testSaveTicketSuccess() throws MissingIdException, InvalidIdException, NoSeatsAvailableException {
		Ticket testTicket = new Ticket();
		Flight flight = new Flight();
		flight.setFlightId(10);
		flight.setSeatsAvailable(50);
		Customer customer = new Customer();
		customer.setCustomerId(10);
		testTicket.setFlight(flight);
		testTicket.setCustomer(customer);
		when(customerRepo.existsById(eq(customer.getCustomerId()))).thenReturn(true);
		when(flightRepo.existsById(eq(flight.getFlightId()))).thenReturn(true);
		when(flightRepo.getOne(10)).thenReturn(flight);
		ticketService.saveTicket(testTicket);
		verify(ticketRepo, times(1)).save(testTicket);
		assertEquals(flight.getSeatsAvailable(), 49);
	}
	
	@Test
	public void testSaveTicketMissingID() {
		Ticket testTicket = new Ticket();
		Flight flight = new Flight();
		flight.setFlightId(10);
		Customer customer = new Customer();
		Agency agency = new Agency();
		agency.setAgencyName("Test Agency");
		agency.setAgencyAddress("Test Address");
		agency.setAgencyPhone("Test Phone");
		testTicket.setFlight(flight);
		testTicket.setCustomer(customer);
		MissingIdException ex = assertThrows(MissingIdException.class, () -> ticketService.saveTicket(testTicket));
		assertEquals(ex.getMessage(), "Missing ID.");
	}
	
	@Test
	public void testSaveTicketInvalidID() {
		Ticket testTicket = new Ticket();
		Flight flight = new Flight();
		flight.setFlightId(10);
		Customer customer = new Customer();
		customer.setCustomerId(10);
		testTicket.setFlight(flight);
		testTicket.setCustomer(customer);
		when(customerRepo.existsById(eq(customer.getCustomerId()))).thenReturn(false);
		InvalidIdException ex = assertThrows(InvalidIdException.class, () -> ticketService.saveTicket(testTicket));
		assertEquals(ex.getMessage(), "That ID is invalid.");
	}
	
	@Test
	public void testSaveTicketNoSeatsAvailable() {
		Ticket testTicket = new Ticket();
		Flight flight = new Flight();
		flight.setFlightId(10);
		flight.setSeatsAvailable(0);
		Customer customer = new Customer();
		customer.setCustomerId(10);
		testTicket.setFlight(flight);
		testTicket.setCustomer(customer);
		when(customerRepo.existsById(eq(customer.getCustomerId()))).thenReturn(true);
		when(flightRepo.existsById(eq(flight.getFlightId()))).thenReturn(true);
		when(flightRepo.getOne(10)).thenReturn(flight);
		NoSeatsAvailableException ex = assertThrows(NoSeatsAvailableException.class, () -> ticketService.saveTicket(testTicket));
		assertEquals(ex.getMessage(), "No more seats available for this flight.");
	}
	
	@Test
	public void testReadTicketsByCustomerSuccess() throws InvalidIdException {
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
		InvalidIdException ex = assertThrows(InvalidIdException.class, () -> ticketService.readTicketsByCustomer(10, 0, 10));
		assertEquals(ex.getMessage(), "That ID is invalid.");
	}
	
	@Test
	public void returnTicketSuccess() throws InvalidIdException {
		Ticket ticket = new Ticket();
		ticket.setTicketId(10);
		Flight flight = new Flight();
		flight.setSeatsAvailable(50);
		ticket.setFlight(flight);
		when(ticketRepo.existsById(10)).thenReturn(true);
		when(ticketRepo.getOne(10)).thenReturn(ticket);
		ticketService.returnTicket(10);
		assertEquals(ticket.getCanceled(), true);
		assertEquals(flight.getSeatsAvailable(), 51);
	}
	
	@Test
	public void returnTicketInvalidID() {
		when(ticketRepo.existsById(10)).thenReturn(false);
		InvalidIdException ex = assertThrows(InvalidIdException.class, () -> ticketService.returnTicket(10));
		assertEquals(ex.getMessage(), "That ID is invalid.");
	}

}
