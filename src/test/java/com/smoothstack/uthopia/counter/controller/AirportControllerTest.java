package com.smoothstack.uthopia.counter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.smoothstack.uthopia.counter.model.Airport;
import com.smoothstack.uthopia.counter.service.AirportService;

public class AirportControllerTest {
	
	@InjectMocks
	private AirportController airportController;
	
	@Mock
	private AirportService airportService;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testReadAirports() {
		Airport airport = new Airport();
		airport.setAirportCode("LAX");
		airport.setAirportLocation("Los Angeles, California");
		airport.setAirportName("Los Angeles International Airport");
		List<Airport> airports = Collections.singletonList(airport);
		when(airportService.readAirports()).thenReturn(airports);
		assertEquals(airportController.readAirports(), new ResponseEntity<List<Airport>>(airports, HttpStatus.OK));
	}
}
