package com.hermes.talaria.domain.gateway.dto;

import java.util.List;
import java.util.Map;

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
public class GatewayResponse {
	GatewayInfoResponse info;
	GatewayPoliciesResponse policies;
	List<GatewayKeyResponse> keys;
	Map<String, Object> oas;

	@Builder
	public GatewayResponse(GatewayInfoResponse info, GatewayPoliciesResponse policies, List<GatewayKeyResponse> keys,
		Map<String, Object> oas) {
		this.info = info;
		this.policies = policies;
		this.keys = keys;
		this.oas = oas;
	}
}
