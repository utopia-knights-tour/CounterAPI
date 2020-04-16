package com.smoothstack.uthopia.counter.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smoothstack.uthopia.counter.model.Flight;
import com.smoothstack.uthopia.counter.repository.FlightRepository;

@Service
public class FlightService {
	
	@Autowired
	private FlightRepository flightRepo;
	
	public List<Flight> readFlights(String originCode, String destinationCode, String departureDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(departureDate, formatter);
		return flightRepo.findFlights(originCode, destinationCode, localDate);
	}

}
