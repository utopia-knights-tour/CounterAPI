package com.smoothstack.utopia.counter.service;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.smoothstack.utopia.counter.exception.InvalidIdException;
import com.smoothstack.utopia.counter.model.Airport;
import com.smoothstack.utopia.counter.model.Flight;
import com.smoothstack.utopia.counter.repository.AirportRepository;
import com.smoothstack.utopia.counter.repository.FlightRepository;
import com.smoothstack.utopia.counter.service.FlightService;

public class FlightServiceTest {

	@InjectMocks
	private FlightService flightService;

	@Mock
	private FlightRepository flightRepo;

	@Mock
	private AirportRepository airportRepo;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testReadFlightsSuccess() throws InvalidIdException {
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
		when(airportRepo.existsById(source.getAirportCode())).thenReturn(true);
		when(airportRepo.existsById(destination.getAirportCode())).thenReturn(true);
		when(flightRepo.findFlights(anyString(), anyString(), any())).thenReturn(flights);
		assertEquals(flightService.readFlights(source.getAirportCode(), destination.getAirportCode(), "2020-04-15"),
				flights);
	}

	@Test
	public void testReadFlightsFailure() {
		when(airportRepo.existsById("ABC")).thenReturn(true);
		when(airportRepo.existsById("DEF")).thenReturn(false);
		InvalidIdException ex = assertThrows(InvalidIdException.class, () -> flightService.readFlights("ABC","DEF", "2020-04-15"));
		assertEquals(ex.getMessage(), "Invalid Airport Code.");
		assertThrows(InvalidIdException.class, () -> flightService.readFlights("DEF","ABC", "2020-04-15"));
	}
	
	@Test
	public void testReadFlightSuccess() throws InvalidIdException {
		Flight testFlight = new Flight();
		testFlight.setFlightId(10);
		Optional<Flight> flight = Optional.of(testFlight);
		when(flightRepo.findById(10)).thenReturn(flight);
		assertEquals(flightService.readFlight(10), testFlight);
	}
	
	@Test
	public void testReadFlightFailure() {
		when(flightRepo.findById(10)).thenReturn(Optional.empty());
		assertThrows(InvalidIdException.class, () -> flightService.readFlight(10));
	}

}
