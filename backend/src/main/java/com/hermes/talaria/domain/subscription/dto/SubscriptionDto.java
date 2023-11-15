package com.hermes.talaria.domain.subscription.dto;

import java.time.LocalDateTime;

import com.hermes.talaria.domain.subscription.constant.SubscriptionStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SubscriptionDto {
	private Long subscriptionId;
	private Long memberId;
	private Long apisId;
	private Long keyId;
	private String content;
	private LocalDateTime subscriptionTime;
	private SubscriptionStatus status;
	private String address;

	@Builder
	public SubscriptionDto(Long subscriptionId, Long memberId, Long apisId, Long keyId, String content,
		LocalDateTime subscriptionTime, SubscriptionStatus status, String address) {
		this.subscriptionId = subscriptionId;
		this.memberId = memberId;
		this.apisId = apisId;
		this.keyId = keyId;
		this.content = content;
		this.subscriptionTime = subscriptionTime;
		this.status = status;
		this.address = address;
	}
}
