package com.hermes.talaria.domain.apis.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hermes.talaria.domain.apis.constant.ApisStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public class ApisResponse {
	private Long apisId;
	private String name;
	private String webServerUrl;
	private ApisStatus status;

	@Builder
	public ApisResponse(Long apisId, String name, String webServerUrl,
		ApisStatus status) {
		this.apisId = apisId;
		this.name = name;
		this.webServerUrl = webServerUrl;
		this.status = status;
	}
}
