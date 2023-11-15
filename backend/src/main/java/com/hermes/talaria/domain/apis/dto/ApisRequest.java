package com.hermes.talaria.domain.apis.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hermes.talaria.domain.apis.constant.ApisStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public class ApisRequest {
	String name;
	String webServerUrl;
	ApisStatus status;

	@Builder
	public ApisRequest(String name, String webServerUrl, ApisStatus status) {
		this.name = name;
		this.webServerUrl = webServerUrl;
		this.status = status;
	}
}
