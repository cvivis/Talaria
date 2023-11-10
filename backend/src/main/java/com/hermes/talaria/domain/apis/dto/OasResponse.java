package com.hermes.talaria.domain.apis.dto;

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
public class OasResponse {
	String swaggerContent;

	@Builder
	public OasResponse(String swaggerContent) {
		this.swaggerContent = swaggerContent;
	}
}
