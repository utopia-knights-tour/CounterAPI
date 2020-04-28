package com.smoothstack.utopia.counter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smoothstack.utopia.counter.exception.InvalidIdException;
import com.smoothstack.utopia.counter.model.Ticket;
import com.smoothstack.utopia.counter.repository.CustomerRepository;
import com.smoothstack.utopia.counter.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepo;

	@Autowired
	private CustomerRepository customerRepo;

	public List<Ticket> readTicketsByCustomer(Integer customerId, Integer page, Integer pageSize)
			throws InvalidIdException {
		// Not found.
		if (!customerRepo.existsById(customerId)) {
			throw new InvalidIdException("That ID is invalid.");
		}
		Pageable pageable = PageRequest.of(page, pageSize);
		return ticketRepo.findTickets(customerId, pageable);
	}

}
