package com.smoothstack.utopia.counter.controller;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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
import org.springframework.http.ResponseEntity;

import com.smoothstack.utopia.counter.controller.FlightController;
import com.smoothstack.utopia.counter.exception.InvalidIdException;
import com.smoothstack.utopia.counter.model.Airport;
import com.smoothstack.utopia.counter.model.Flight;
import com.smoothstack.utopia.counter.service.FlightService;

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
	public void testReadFlights() throws InvalidIdException {
		Flight flight = new Flight();
		Airport source = new Airport();
		source.setAirportCode("LAX");
		flight.setSourceAirport(source);
		Airport destination = new Airport();
		destination.setAirportCode("JFK");
		flight.setDestinationAirport(destination);
		flight.setCost(new BigDecimal("100.0"));
		flight.setSeatsAvailable(50);
		flight.setArrivalDate(LocalDate.now());
		flight.setDepartureDate(LocalDate.now());
		flight.setArrivalTime(LocalTime.now());
		flight.setDepartureTime(LocalTime.now());
		List<Flight> flights = Collections.singletonList(flight);
		when(flightService.readFlights(eq(source.getAirportCode()), eq(destination.getAirportCode()),
				eq(flight.getDepartureDate().toString()))).thenReturn(flights);
		assertEquals(flightController.readFlights(source.getAirportCode(), destination.getAirportCode(), 
				flight.getDepartureDate().toString()), ResponseEntity.ok().body(flights));
	}

	@Test
	public void testReadFlight() throws InvalidIdException {
		Flight flight = new Flight();
		flight.setFlightId(10);
		when(flightService.readFlight(10)).thenReturn(flight);
		assertEquals(flightController.readFlight(10), 
				ResponseEntity.ok().body(flight));
	}
}
