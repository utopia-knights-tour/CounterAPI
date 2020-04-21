package com.smoothstack.utopia.counter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smoothstack.utopia.counter.model.Airport;
import com.smoothstack.utopia.counter.repository.AirportRepository;

@Service
public class AirportService {
	
	@Autowired
	private AirportRepository airportRepo;
	
	public List<Airport> readAirports() {
		return airportRepo.findAll();
	}

}
