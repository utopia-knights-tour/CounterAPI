package com.smoothstack.utopia.counter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smoothstack.utopia.counter.exception.InvalidIdException;
import com.smoothstack.utopia.counter.exception.MissingIdException;
import com.smoothstack.utopia.counter.exception.NoSeatsAvailableException;
import com.smoothstack.utopia.counter.model.Flight;
import com.smoothstack.utopia.counter.model.Ticket;
import com.smoothstack.utopia.counter.repository.CustomerRepository;
import com.smoothstack.utopia.counter.repository.FlightRepository;
import com.smoothstack.utopia.counter.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private FlightRepository flightRepo;

	public void saveTicket(Ticket ticket) throws MissingIdException, NoSeatsAvailableException {
		// Request body validation guarantees Customer and Flight are not null.
		Integer customerId = ticket.getCustomer().getCustomerId();
		Integer flightId = ticket.getFlight().getFlightId();
		// Bad Request.
		if (customerId == null || flightId == null
				|| ticket.getAgency() != null && ticket.getAgency().getAgencyId() == null) {
			throw new MissingIdException("Missing ID.");
		}
		ticket.setCanceled(false);
		ticketRepo.save(ticket);
		Flight flight = flightRepo.getOne(flightId);
		Integer numSeats = flight.getSeatsAvailable();
		// No more seats.
		if (numSeats <= 0) {
			throw new NoSeatsAvailableException("No more seats available for this flight.");
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

	public void returnTicket(Integer ticketId) throws InvalidIdException {
		Optional<Ticket> ticket = ticketRepo.findById(ticketId);
		// Not found.
		if (!ticket.isPresent()) {
			throw new InvalidIdException("That ID is invalid.");
		}
		ticket.get().setCanceled(true);
		Flight flight = ticket.get().getFlight();
		flight.setSeatsAvailable(flight.getSeatsAvailable() + 1);
	}
}