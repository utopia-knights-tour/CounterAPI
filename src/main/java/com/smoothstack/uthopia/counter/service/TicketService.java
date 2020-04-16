package com.smoothstack.uthopia.counter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

	public Ticket saveTicket(Ticket ticket) {
		Integer customerId = ticket.getCustomer().getCustomerId();
		Integer flightId = ticket.getFlight().getFlightId();
		if (customerId == null || !customerRepo.existsById(customerId) || flightId == null
				|| !flightRepo.existsById(flightId)
				|| (ticket.getAgency() != null && (ticket.getAgency().getAgencyId() == null
				|| !agencyRepo.existsById(ticket.getAgency().getAgencyId())))) {
			return null;
		}
		ticket.setCanceled(false);
		return ticketRepo.save(ticket);
	}

	public List<Ticket> readTicketsByCustomer(Integer customerId, Integer page, Integer pageSize) {
		if (customerRepo.existsById(customerId)) {
			Pageable pageable = PageRequest.of(page, pageSize);
			return ticketRepo.findTickets(customerId, pageable);
		} else {
			return null;
		}
	}

	public Boolean returnTicket(Integer ticketId) {
		if (ticketRepo.existsById(ticketId)) {
			ticketRepo.deleteById(ticketId);
			return true;
		}
		return false;
	}
}
