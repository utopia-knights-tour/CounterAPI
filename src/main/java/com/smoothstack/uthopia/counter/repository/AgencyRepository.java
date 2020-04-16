package com.smoothstack.uthopia.counter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smoothstack.uthopia.counter.model.Agency;

public interface AgencyRepository extends JpaRepository<Agency, Integer> {

}
