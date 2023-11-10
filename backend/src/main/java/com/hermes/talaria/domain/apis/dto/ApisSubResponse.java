package com.hermes.talaria.domain.apis.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hermes.talaria.domain.subscription.constant.Status;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public class ApisSubResponse {
	private Long apisId;
	private String name;
	private String content;
	private String routingUrl;
	private Long quota;
	private Status status;

	@Builder
	public ApisSubResponse(Long apisId, String name, String content, String routingUrl, Long quota,
		Status status) {
		this.apisId = apisId;
		this.name = name;
		this.content = content;
		this.routingUrl = routingUrl;
		this.quota = quota;
		this.status = status;
	}
}
