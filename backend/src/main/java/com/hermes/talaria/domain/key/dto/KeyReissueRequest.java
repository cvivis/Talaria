package com.hermes.talaria.domain.key.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public class KeyReissueRequest {
	Long keyId;

	@Builder
	public KeyReissueRequest(Long keyId) {
		this.keyId = keyId;
	}
}
