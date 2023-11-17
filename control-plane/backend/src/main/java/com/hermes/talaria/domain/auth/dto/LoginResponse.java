package com.hermes.talaria.domain.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hermes.talaria.domain.auth.constant.Role;
import com.hermes.talaria.domain.member.dto.MemberDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginResponse {
	private Long memberId;
	private String email;
	private Role role;
	private Long keyId;
	private String accessToken;
	private String refreshToken;

	@Builder
	public LoginResponse(Long memberId, String email, Role role, Long keyId, String accessToken, String refreshToken) {
		this.memberId = memberId;
		this.email = email;
		this.role = role;
		this.keyId = keyId;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public static LoginResponse of(MemberDto memberDto, String accessToken, String refreshToken) {
		return LoginResponse.builder()
			.memberId(memberDto.getMemberId())
			.email(memberDto.getEmail())
			.role(memberDto.getRole())
			.keyId(memberDto.getKeyId())
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}
