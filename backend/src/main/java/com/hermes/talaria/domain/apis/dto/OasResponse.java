package com.hermes.talaria.domain.apis.dto;

import java.util.Map;

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
	Map<String, Object> swaggerContent;

	@Builder
	public OasResponse(Map<String, Object> swaggerContent) {
		this.swaggerContent = swaggerContent;
	}
}
