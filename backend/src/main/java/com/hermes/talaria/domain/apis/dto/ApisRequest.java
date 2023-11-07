package com.hermes.talaria.domain.apis.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApisRequest {
	String name;
	String url;

	@Builder

	public ApisRequest(String name, String url) {
		this.name = name;
		this.url = url;
	}
}
