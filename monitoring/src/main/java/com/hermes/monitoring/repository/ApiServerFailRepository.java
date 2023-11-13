package com.hermes.monitoring.repository;

import com.hermes.monitoring.entity.ServerFail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ApiServerFailRepository extends JpaRepository<ServerFail, Integer> {

}
