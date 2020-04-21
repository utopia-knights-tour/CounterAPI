package com.smoothstack.utopia.counter.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smoothstack.utopia.counter.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	
	@Query(value = "SELECT t FROM Ticket t WHERE t.customer.customerId = ?1 AND t.flight.departureDate > CURRENT_DATE")
	List<Ticket> findTickets(Integer customerId, Pageable pageable);
}
