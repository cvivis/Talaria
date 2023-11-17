package com.hermes.talaria.domain.member.dto;

import java.time.LocalDateTime;

import com.hermes.talaria.domain.auth.constant.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDto {
	private Long memberId;
	private String email;
	private String password;
	private Role role;
	private Long keyId;
	private LocalDateTime createdTime;

	@Builder
	public MemberDto(Long memberId, String email, String password, Role role, Long keyId, LocalDateTime createdTime) {
		this.memberId = memberId;
		this.email = email;
		this.password = password;
		this.role = role;
		this.keyId = keyId;
		this.createdTime = createdTime;
	}
}
