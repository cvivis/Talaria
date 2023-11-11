package com.hermes.talaria.domain.key.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hermes.talaria.domain.key.entity.Key;

@Repository
public interface KeyRepository extends JpaRepository<Key, Long> {
	Optional<Key> findByKeyValue(String keyValue);

	Optional<Key> findByKeyId(Long keyId);
}

