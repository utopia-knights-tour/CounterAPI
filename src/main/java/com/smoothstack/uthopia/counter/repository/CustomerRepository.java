package com.smoothstack.uthopia.counter.repository;

import org.springframework.stereotype.Repository;

import com.smoothstack.uthopia.counter.model.Customer;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	List<Customer> findByCustomerNameStartsWith(String name, Pageable pageable);
	
}
