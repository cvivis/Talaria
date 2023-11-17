package com.hermes.talaria.domain.member.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hermes.talaria.domain.auth.constant.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SignupRequest {
	String email;
	String password;
	String keyExpirationDate;
	Role role;

	@Builder
	public SignupRequest(String email, String password, String keyExpirationDate, Role role) {
		this.email = email;
		this.password = password;
		this.keyExpirationDate = keyExpirationDate;
		this.role = role;
	}
}
