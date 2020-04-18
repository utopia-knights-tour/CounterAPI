package com.smoothstack.uthopia.counter.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smoothstack.uthopia.counter.exception.InvalidIdException;
import com.smoothstack.uthopia.counter.exception.MissingIdException;
import com.smoothstack.uthopia.counter.exception.NoSeatsAvailableException;
import com.smoothstack.uthopia.counter.model.Flight;
import com.smoothstack.uthopia.counter.model.Ticket;
import com.smoothstack.uthopia.counter.repository.AgencyRepository;
import com.smoothstack.uthopia.counter.repository.CustomerRepository;
import com.smoothstack.uthopia.counter.repository.FlightRepository;
import com.smoothstack.uthopia.counter.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private FlightRepository flightRepo;

	@Autowired
	private AgencyRepository agencyRepo;

	@Transactional
	public void saveTicket(Ticket ticket) throws MissingIdException, InvalidIdException, NoSeatsAvailableException {
		// Request body validation guarantees Customer and Flight are not null.
		Integer customerId = ticket.getCustomer().getCustomerId();
		Integer flightId = ticket.getFlight().getFlightId();
		// Bad Request.
		if (customerId == null || flightId == null || 
				ticket.getAgency() != null && ticket.getAgency().getAgencyId() == null) {
			throw new MissingIdException("Missing ID.");
		}
		// Not found.
		if (!customerRepo.existsById(customerId) || !flightRepo.existsById(flightId)
				|| (ticket.getAgency() != null && ticket.getAgency().getAgencyId() == null
				&& !agencyRepo.existsById(ticket.getAgency().getAgencyId()))) {
			throw new InvalidIdException("That ID is invalid.");
		}
		ticket.setCanceled(false);
		ticketRepo.save(ticket);
		Flight flight = flightRepo.getOne(flightId);
		Integer numSeats = flight.getSeatsAvailable(); 
		// No more tickets.
		if (numSeats <= 0) {
			throw new NoSeatsAvailableException("No more tickets available for this flight.");
		}
		flight.setSeatsAvailable(numSeats - 1);
	}

	public List<Ticket> readTicketsByCustomer(Integer customerId, Integer page, Integer pageSize)
			throws InvalidIdException {
		// Not found.
		if (!customerRepo.existsById(customerId)) {
			throw new InvalidIdException("That ID is invalid.");
		} 
		Pageable pageable = PageRequest.of(page, pageSize);
		return ticketRepo.findTickets(customerId, pageable);
	}

	@Transactional
	public void returnTicket(Integer ticketId) throws InvalidIdException {
		// Not found.
		if (!ticketRepo.existsById(ticketId)) {
			throw new InvalidIdException("That ID is invalid.");
		}
		Ticket ticket = ticketRepo.getOne(ticketId);
		ticket.setCanceled(true);
		Flight flight = ticket.getFlight();
		flight.setSeatsAvailable(flight.getSeatsAvailable() + 1);
	}
}
