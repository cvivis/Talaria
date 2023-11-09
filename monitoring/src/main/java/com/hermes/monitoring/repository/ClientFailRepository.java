package com.hermes.monitoring.repository;

import com.hermes.monitoring.entity.ClientFail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientFailRepository extends JpaRepository<ClientFail,Long> {
}
