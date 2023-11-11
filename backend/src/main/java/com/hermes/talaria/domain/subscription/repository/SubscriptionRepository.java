package com.hermes.talaria.domain.subscription.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hermes.talaria.domain.subscription.constant.SubscriptionStatus;
import com.hermes.talaria.domain.subscription.entity.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	List<Subscription> findByMemberIdAndStatus(Long memberId, SubscriptionStatus status);

	List<Subscription> findByStatus(SubscriptionStatus status);

	List<Subscription> findByApisIdAndStatus(Long apisId, SubscriptionStatus status);
}
