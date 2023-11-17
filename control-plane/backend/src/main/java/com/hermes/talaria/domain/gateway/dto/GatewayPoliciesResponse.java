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
public class GatewayPoliciesResponse {
	Long quota;
	List<GatewayIPResponse> whitelist;

	@Builder
	public GatewayPoliciesResponse(Long quota, List<GatewayIPResponse> whitelist) {
		this.quota = quota;
		this.whitelist = whitelist;
	}
}
