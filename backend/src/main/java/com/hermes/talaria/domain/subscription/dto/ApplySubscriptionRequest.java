package com.hermes.talaria.domain.subscription.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApplySubscriptionRequest {
	Long subscriptionId;
	Long memberId;
	Long apisId;
	Long keyId;
	String content;
	String address;

	@Builder
	public ApplySubscriptionRequest(Long subscriptionId, Long memberId, Long apisId, Long keyId, String content,
		String address) {
		this.subscriptionId = subscriptionId;
		this.memberId = memberId;
		this.apisId = apisId;
		this.keyId = keyId;
		this.content = content;
		this.address = address;
	}

}
