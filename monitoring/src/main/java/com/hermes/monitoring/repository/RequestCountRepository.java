package com.hermes.monitoring.repository;

import com.hermes.monitoring.entity.ClientFail;
import com.hermes.monitoring.entity.RequestCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestCountRepository extends JpaRepository<RequestCount,Long> {
}
