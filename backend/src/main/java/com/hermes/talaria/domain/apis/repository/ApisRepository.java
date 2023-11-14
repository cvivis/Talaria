package com.hermes.talaria.domain.apis.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hermes.talaria.domain.apis.constant.ApisStatus;
import com.hermes.talaria.domain.apis.entity.Apis;

@Repository
public interface ApisRepository extends JpaRepository<Apis, Long> {

	List<Apis> findApisByDeveloperId(Long memberId);

	Optional<Apis> findApisByApisId(Long apisId);

	void deleteApisByApisId(Long apisId);

	List<Apis> findApisByStatus(ApisStatus status);

	Optional<Apis> findApisByName(String apisName);
}
