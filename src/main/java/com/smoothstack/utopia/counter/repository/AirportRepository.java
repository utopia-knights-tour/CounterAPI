package com.smoothstack.utopia.counter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smoothstack.utopia.counter.model.Airport;

@Repository
public interface AirportRepository extends JpaRepository<Airport, String> {
	
	List<Airport> findByOrderByAirportCode();
	
}
