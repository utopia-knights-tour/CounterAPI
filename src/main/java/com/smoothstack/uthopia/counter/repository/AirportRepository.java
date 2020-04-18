package com.smoothstack.uthopia.counter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smoothstack.uthopia.counter.model.Airport;

@Repository
public interface AirportRepository extends JpaRepository<Airport, String> {
	
}
