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
public class KeyReissueResponse {
	String key;
	String keyCreatedDate;
	String keyExpirationDate;

	@Builder
	public KeyReissueResponse(String key, String keyCreatedDate, String keyExpirationDate) {
		this.key = key;
		this.keyCreatedDate = keyCreatedDate;
		this.keyExpirationDate = keyExpirationDate;
	}
}
