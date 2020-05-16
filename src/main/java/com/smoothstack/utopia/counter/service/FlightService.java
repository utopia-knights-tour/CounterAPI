package com.smoothstack.utopia.counter.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smoothstack.utopia.counter.exception.InvalidIdException;
import com.smoothstack.utopia.counter.model.Flight;
import com.smoothstack.utopia.counter.repository.AirportRepository;
import com.smoothstack.utopia.counter.repository.FlightRepository;

@Service
public class FlightService {
	
	@Autowired
	private FlightRepository flightRepo;
	
	@Autowired
	private AirportRepository airportRepo;
	
	public List<Flight> readFlights(String originCode, String destinationCode, String departureDate) throws InvalidIdException {
		if (!airportRepo.existsById(originCode) || !airportRepo.existsById(destinationCode)) {
			throw new InvalidIdException("Invalid Airport Code.");
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(departureDate, formatter);
		return flightRepo.findFlights(originCode, destinationCode, localDate);
	}
	
	public Flight readFlight(Integer flightId) throws InvalidIdException {
		Optional<Flight> flight = flightRepo.findById(flightId);
		return flight.orElseThrow(() -> new InvalidIdException("Invalid Flight ID."));
	}

}
