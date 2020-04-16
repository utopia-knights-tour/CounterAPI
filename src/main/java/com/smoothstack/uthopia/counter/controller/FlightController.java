package com.smoothstack.uthopia.counter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smoothstack.uthopia.counter.model.Flight;
import com.smoothstack.uthopia.counter.service.FlightService;

@RestController
@RequestMapping(path = "/counter")
public class FlightController {
	
	@Autowired
	private FlightService flightService;
	
	@GetMapping(path = "/flights")
	public ResponseEntity<List<Flight>> readFlights(@RequestParam String originCode, @RequestParam String destinationCode,
			@RequestParam String departureDate) {
		List<Flight> flights = flightService.readFlights(originCode, destinationCode, departureDate);
		return new ResponseEntity<List<Flight>>(flights, HttpStatus.OK);
	}

}
