package com.hermes.talaria.domain.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RefreshResponse {
	String accessToken;

	@Builder
	public RefreshResponse(String accessToken) {
		this.accessToken = accessToken;
	}

	public static RefreshResponse fromRefreshToken(String accessToken) {
		return RefreshResponse.builder()
			.accessToken(accessToken)
			.build();
	}
}
