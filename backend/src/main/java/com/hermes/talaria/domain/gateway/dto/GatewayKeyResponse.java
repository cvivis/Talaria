package com.hermes.talaria.domain.gateway.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GatewayKeyResponse {
	String key;
	String id;

	@Builder
	public GatewayKeyResponse(String key, String id) {
		this.key = key;
		this.id = id;
	}
}
