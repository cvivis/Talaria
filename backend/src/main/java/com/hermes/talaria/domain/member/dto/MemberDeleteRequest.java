package com.hermes.talaria.domain.member.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberDeleteRequest {
	List<Long> memberIds;

	@Builder
	public MemberDeleteRequest(List<Long> memberIds) {
		this.memberIds = memberIds;
	}
}
