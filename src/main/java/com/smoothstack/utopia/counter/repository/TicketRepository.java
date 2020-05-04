package com.smoothstack.utopia.counter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smoothstack.utopia.counter.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	
	@Query(value = "SELECT t FROM Ticket t WHERE t.customer.customerId = ?1 AND t.flight.departureDate > CURRENT_DATE")
	Page<Ticket> findTickets(Integer customerId, Pageable pageable);
}
