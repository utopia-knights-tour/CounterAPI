package com.smoothstack.utopia.counter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.smoothstack.utopia.counter.exception.InvalidIdException;
import com.smoothstack.utopia.counter.model.Customer;
import com.smoothstack.utopia.counter.model.Ticket;
import com.smoothstack.utopia.counter.repository.CustomerRepository;
import com.smoothstack.utopia.counter.repository.TicketRepository;
import com.smoothstack.utopia.counter.service.TicketService;

public class TicketServiceTest {
	
	@InjectMocks
	private TicketService ticketService;
	
	@Mock
	private TicketRepository ticketRepo;
	
	@Mock
	private CustomerRepository customerRepo;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
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
		Page<Ticket> ticketsPage = new PageImpl<Ticket>(tickets, PageRequest.of(0, 10), 1);
		when(customerRepo.existsById(customer.getCustomerId())).thenReturn(true);
		when(ticketRepo.findTickets(eq(10), any())).thenReturn(ticketsPage);
		assertNotNull(ticketService.readTicketsByCustomer(customer.getCustomerId(), 0, 10));
	}
	
	@Test
	public void testReadTicketsByCustomerFailure() {
		when(customerRepo.existsById(10)).thenReturn(false);
		InvalidIdException ex = assertThrows(InvalidIdException.class, () -> ticketService.readTicketsByCustomer(10, 0, 10));
		assertEquals(ex.getMessage(), "That ID is invalid.");
	}

}
