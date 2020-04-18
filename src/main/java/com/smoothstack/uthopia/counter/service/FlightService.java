package com.smoothstack.uthopia.counter.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smoothstack.uthopia.counter.exception.InvalidIdException;
import com.smoothstack.uthopia.counter.model.Flight;
import com.smoothstack.uthopia.counter.repository.AirportRepository;
import com.smoothstack.uthopia.counter.repository.FlightRepository;

@Service
public class FlightService {
	
	@Autowired
	private FlightRepository flightRepo;
	
	@Autowired
	private AirportRepository airportRepo;
	
	public List<Flight> readFlights(String originCode, String destinationCode, String departureDate) throws InvalidIdException {
		if (!airportRepo.existsById(originCode) || !airportRepo.existsById(destinationCode)) {
			throw new InvalidIdException("That ID is invalid.");
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(departureDate, formatter);
		return flightRepo.findFlights(originCode, destinationCode, localDate);
	}

}
