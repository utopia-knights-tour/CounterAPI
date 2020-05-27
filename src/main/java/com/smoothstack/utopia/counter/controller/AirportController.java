package com.smoothstack.utopia.counter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smoothstack.utopia.counter.model.Airport;
import com.smoothstack.utopia.counter.service.AirportService;

@RestController
@RequestMapping("/counter/airports")
public class AirportController {
	
	@Autowired
	private AirportService airportService;
	
	@GetMapping
	public ResponseEntity<List<Airport>> readAirports() {
		List<Airport> airports = airportService.readAirports();
		return ResponseEntity.ok().body(airports);
	}
}
