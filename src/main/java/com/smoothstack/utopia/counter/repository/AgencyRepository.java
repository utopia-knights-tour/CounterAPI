package com.smoothstack.utopia.counter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smoothstack.utopia.counter.model.Agency;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Integer> {

}
