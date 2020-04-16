package com.smoothstack.uthopia.counter.service;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.smoothstack.uthopia.counter.model.Airport;
import com.smoothstack.uthopia.counter.model.Flight;
import com.smoothstack.uthopia.counter.repository.FlightRepository;

public class FlightServiceTest {
	
	@InjectMocks
	private FlightService flightService;
	
	@Mock
	private FlightRepository flightRepo;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testReadFlights() {
		Flight flight = new Flight();
		flight.setSourceAirport(new Airport());
		flight.setDestinationAirport(new Airport());
		flight.setCost(100.0);
		flight.setSeatsAvailable(50);
		flight.setArrivalDate(LocalDate.now());
		flight.setDepartureDate(LocalDate.now());
		flight.setArrivalTime(LocalTime.now());
		flight.setDepartureTime(LocalTime.now());
		List<Flight> flights = Collections.singletonList(flight);
		when(flightRepo.findFlights(anyString(), anyString(), any())).thenReturn(flights);
		assertEquals(flightService.readFlights("", "", "2020-04-15"), flights);
	}

}
