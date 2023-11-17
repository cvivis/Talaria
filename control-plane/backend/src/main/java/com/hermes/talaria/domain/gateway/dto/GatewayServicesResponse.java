package com.hermes.talaria.domain.gateway.dto;

import java.util.List;

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
public class GatewayServicesResponse {
	List<GatewayResponse> services;

	@Builder
	public GatewayServicesResponse(List<GatewayResponse> services) {
		this.services = services;
	}
}
