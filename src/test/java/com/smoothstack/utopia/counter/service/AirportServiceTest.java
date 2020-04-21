package com.smoothstack.utopia.counter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.smoothstack.utopia.counter.model.Airport;
import com.smoothstack.utopia.counter.repository.AirportRepository;
import com.smoothstack.utopia.counter.service.AirportService;

public class AirportServiceTest {
	
	@InjectMocks
	private AirportService airportService;
	
	@Mock
	private AirportRepository airportRepo;
	
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
		when(airportRepo.findAll()).thenReturn(airports);
		assertEquals(airportService.readAirports(), airports);
	}

}
