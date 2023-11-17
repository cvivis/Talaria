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
public class ApisManagementRequest {
	Long apisId;
	Long quota;
	String[] whiteList;
	ApisStatus status;

	@Builder
	public ApisManagementRequest(Long apisId, Long quota, String[] whiteList, ApisStatus status) {
		this.apisId = apisId;
		this.quota = quota;
		this.whiteList = whiteList;
		this.status = status;
	}
}
