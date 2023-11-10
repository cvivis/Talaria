package com.hermes.talaria.domain.apis.dto;

import com.hermes.talaria.domain.apis.entity.Apis;
import com.hermes.talaria.domain.subscription.constant.Status;
import com.hermes.talaria.domain.subscription.entity.Subscription;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ApisSubDto {
	private Long apisId;
	private String name;
	private String content;
	private String routingUrl;
	private Long quota;
	private Status status;

	@Builder
	public ApisSubDto(Long apisId, String name, String content, String routingUrl, Long quota,
		Status status) {
		this.apisId = apisId;
		this.name = name;
		this.content = content;
		this.routingUrl = routingUrl;
		this.quota = quota;
		this.status = status;
	}

	public static ApisSubDto fromSubscriptionAndApis(Subscription subscription, Apis apis) {
		return ApisSubDto.builder()
			.apisId(apis.getApisId())
			.name(apis.getName())
			.content(subscription.getContent())
			.routingUrl(apis.getRoutingUrl())
			.quota(apis.getQuota())
			.status(subscription.getStatus())
			.build();
	}
}
