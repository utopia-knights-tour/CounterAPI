package com.smoothstack.utopia.counter.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smoothstack.utopia.counter.model.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer>{
		
		@Query(value = "SELECT f FROM Flight f WHERE f.sourceAirport.airportCode = ?1 AND "
				+ "f.destinationAirport.airportCode = ?2 AND f.departureDate = ?3 AND f.seatsAvailable > 0")
		List<Flight> findFlights(String originCode, String destinationCode, LocalDate departureDate);
}
