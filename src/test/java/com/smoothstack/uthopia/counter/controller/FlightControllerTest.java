package com.smoothstack.uthopia.counter.controller;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.smoothstack.uthopia.counter.model.Airport;
import com.smoothstack.uthopia.counter.model.Flight;
import com.smoothstack.uthopia.counter.service.FlightService;

public class FlightControllerTest {
	
	@InjectMocks
	private FlightController flightController;
	
	@Mock
	private FlightService flightService;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testReadFlights() {
		Flight flight = new Flight();
		Airport source = new Airport();
		source.setAirportCode("LAX");
		flight.setSourceAirport(source);
		Airport destination = new Airport();
		destination.setAirportCode("JFK");
		flight.setDestinationAirport(destination);
		flight.setCost(100.0);
		flight.setSeatsAvailable(50);
		flight.setArrivalDate(LocalDate.now());
		flight.setDepartureDate(LocalDate.now());
		flight.setArrivalTime(LocalTime.now());
		flight.setDepartureTime(LocalTime.now());
		List<Flight> flights = Collections.singletonList(flight);
		when(flightService.readFlights(eq(source.getAirportCode()), eq(destination.getAirportCode()),
				eq(flight.getDepartureDate().toString()))).thenReturn(flights);
		assertEquals(flightController.readFlights(source.getAirportCode(), destination.getAirportCode(), 
				flight.getDepartureDate().toString()), new ResponseEntity<List<Flight>>(flights, HttpStatus.OK));
	}

}
