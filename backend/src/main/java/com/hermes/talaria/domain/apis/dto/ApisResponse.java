package com.hermes.talaria.domain.apis.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApisResponse {
	private Long apisId;

	@Builder
	public ApisResponse(Long apisId) {
		this.apisId = apisId;
	}

	public static ApisResponse ofApisId(Long apisId) {
		return ApisResponse.builder()
			.apisId(apisId)
			.build();
	}
}
