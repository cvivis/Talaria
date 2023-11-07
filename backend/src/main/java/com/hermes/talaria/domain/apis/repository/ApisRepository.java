package com.hermes.talaria.domain.apis.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hermes.talaria.domain.apis.entity.Apis;

@Repository
public interface ApisRepository extends JpaRepository<Apis, Long> {

	List<Apis> findApisByMemberId(Long memberId);

	Optional<Apis> findApisByApisId(Long apisId);

	void deleteApisByApisId(Long apisId);
}
