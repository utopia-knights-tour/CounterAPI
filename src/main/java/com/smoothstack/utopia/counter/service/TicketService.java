package com.smoothstack.utopia.counter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smoothstack.utopia.counter.exception.InvalidIdException;
import com.smoothstack.utopia.counter.model.PageDetails;
import com.smoothstack.utopia.counter.model.Ticket;
import com.smoothstack.utopia.counter.repository.CustomerRepository;
import com.smoothstack.utopia.counter.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepo;

	@Autowired
	private CustomerRepository customerRepo;

	public PageDetails<Ticket> readTicketsByCustomer(Integer customerId, Integer page, Integer pageSize)
			throws InvalidIdException {
		// Not found.
		if (!customerRepo.existsById(customerId)) {
			throw new InvalidIdException("That ID is invalid.");
		}
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<Ticket> tickets = ticketRepo.findTickets(customerId, pageable);
		return new PageDetails<Ticket>(tickets.getContent(), tickets.getTotalElements());
	}

}
