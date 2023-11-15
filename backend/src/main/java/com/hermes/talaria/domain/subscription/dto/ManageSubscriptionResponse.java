package com.hermes.talaria.domain.subscription.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hermes.talaria.domain.apis.entity.Apis;
import com.hermes.talaria.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public class ManageSubscriptionResponse {
	Long subscriptionId;
	String developerName;
	String apiName;
	String userEmail;
	String address;
	String content;

	@Builder
	public ManageSubscriptionResponse(Long subscriptionId, String developerName, String apiName, String userEmail,
		String address, String content) {
		this.subscriptionId = subscriptionId;
		this.developerName = developerName;
		this.apiName = apiName;
		this.userEmail = userEmail;
		this.address = address;
		this.content = content;
	}

	public static ManageSubscriptionResponse of(SubscriptionDto subscriptionDto, Apis apis, Member developer,
		Member user) {
		return ManageSubscriptionResponse.builder()
			.subscriptionId(subscriptionDto.getSubscriptionId())
			.developerName(developer.getEmail())
			.apiName(apis.getName())
			.userEmail(user.getEmail())
			.address(subscriptionDto.getAddress())
			.content(subscriptionDto.getContent())
			.build();
	}
}
