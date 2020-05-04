package com.smoothstack.utopia.counter.repository;

import org.springframework.stereotype.Repository;

import com.smoothstack.utopia.counter.model.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	@Query("SELECT c FROM Customer c WHERE c.customerName LIKE :customerName% AND "
			+ "c.customerAddress LIKE :customerAddress% AND c.customerPhone LIKE :customerPhone%")
	Page<Customer> findCustomers(Pageable pageable, String customerName, 
			String customerAddress, String customerPhone);
	
}
