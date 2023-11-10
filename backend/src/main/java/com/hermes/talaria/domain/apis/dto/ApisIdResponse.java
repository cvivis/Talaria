package com.hermes.talaria.domain.apis.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public class ApisIdResponse {
	private Long apisId;

	@Builder
	public ApisIdResponse(Long apisId) {
		this.apisId = apisId;
	}

	public static ApisIdResponse ofApisId(Long apisId) {
		return ApisIdResponse.builder()
			.apisId(apisId)
			.build();
	}
}
