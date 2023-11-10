package com.hermes.talaria.domain.subscription.repository;

import java.util.List;

import com.hermes.talaria.domain.subscription.constant.Status;
import com.hermes.talaria.domain.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	List<Subscription> findByMemberIdAndStatus(Long memberId, Status status);
}
