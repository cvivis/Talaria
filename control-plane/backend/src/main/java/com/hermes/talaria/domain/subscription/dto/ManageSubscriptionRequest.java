package com.hermes.talaria.domain.subscription.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hermes.talaria.domain.subscription.constant.SubscriptionStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public class ManageSubscriptionRequest {
	Long subscriptionId;
	SubscriptionStatus status;

	@Builder
	public ManageSubscriptionRequest(Long subscriptionId, SubscriptionStatus status) {
		this.subscriptionId = subscriptionId;
		this.status = status;
	}
}
