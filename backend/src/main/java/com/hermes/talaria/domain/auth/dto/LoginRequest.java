package com.hermes.talaria.domain.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginRequest {
	String email;
	String password;

	@Builder
	public LoginRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
