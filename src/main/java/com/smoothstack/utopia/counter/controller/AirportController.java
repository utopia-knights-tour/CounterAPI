package com.smoothstack.utopia.counter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smoothstack.utopia.counter.model.Airport;
import com.smoothstack.utopia.counter.service.AirportService;

@RestController
@CrossOrigin(origins = "http://utopia-airlines.s3-website-us-east-1.amazonaws.com")
@RequestMapping(path = "/counter")
public class AirportController {
	
	@Autowired
	private AirportService airportService;
	
	@GetMapping(path = "/airports")
	public ResponseEntity<List<Airport>> readAirports() {
		List<Airport> airports = airportService.readAirports();
		return new ResponseEntity<List<Airport>>(airports, HttpStatus.OK);
	}
}
