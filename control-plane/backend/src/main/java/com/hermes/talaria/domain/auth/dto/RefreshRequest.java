package com.hermes.talaria.domain.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RefreshRequest {
	String refreshToken;

	@Builder
	public RefreshRequest(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
