package com.hermes.talaria.domain.member.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hermes.talaria.domain.auth.constant.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public class MemberResponse {
	Long memberId;
	String email;
	String password;
	Role role;
	String key;
	String keyCreatedDate;
	String keyExpirationDate;

	@Builder
	public MemberResponse(Long memberId, String email, String password, Role role, String key, String keyCreatedDate,
		String keyExpirationDate) {
		this.memberId = memberId;
		this.email = email;
		this.password = password;
		this.role = role;
		this.key = key;
		this.keyCreatedDate = keyCreatedDate;
		this.keyExpirationDate = keyExpirationDate;
	}
}
