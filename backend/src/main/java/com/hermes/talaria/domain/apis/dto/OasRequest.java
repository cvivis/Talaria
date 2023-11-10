package com.hermes.talaria.domain.apis.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hermes.talaria.domain.apis.constant.RawType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public class OasRequest {
	String swaggerContent;
	RawType rawType;

	@Builder
	public OasRequest(String swaggerContent, RawType rawType) {
		this.swaggerContent = swaggerContent;
		this.rawType = rawType;
	}
}
