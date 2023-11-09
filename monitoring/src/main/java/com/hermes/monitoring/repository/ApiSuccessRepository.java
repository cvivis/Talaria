package com.hermes.monitoring.repository;

import com.hermes.monitoring.entity.Success;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiSuccessRepository extends JpaRepository<Success,Integer> {
}
