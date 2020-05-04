package com.smoothstack.utopia.counter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smoothstack.utopia.counter.model.Agency;

public interface AgencyRepository extends JpaRepository<Agency, Integer> {

}
