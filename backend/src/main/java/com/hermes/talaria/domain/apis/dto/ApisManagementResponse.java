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
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApisManagementResponse {
	Long apisId;
	String developerEmail; // developer email
	String name; //apis entity
	Long quota;    //apis entity
	ApisStatus status;    //apis entity
	String[] whiteList; // apis entity

	@Builder
	public ApisManagementResponse(Long apisId, String developerEmail, String name, Long quota, ApisStatus status,
		String[] whiteList) {
		this.apisId = apisId;
		this.developerEmail = developerEmail;
		this.name = name;
		this.quota = quota;
		this.status = status;
		this.whiteList = whiteList;
	}
}
