package com.hermes.talaria.domain.apis.dto;

import com.hermes.talaria.domain.apis.constant.ApisStatus;
import com.hermes.talaria.domain.apis.constant.RawType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ApisDto {
	private Long apisId;
	private Long developerId;
	private String developerEmail;
	private String name;
	private String webServerUrl;
	private String swaggerContent;
	private ApisStatus status;
	private Long quota;
	private RawType rawType;
	private String[] whiteList;
	private String routingUrl;
	private String monitoringUrl;

	@Builder
	public ApisDto(Long apisId, Long developerId, String developerEmail, String name, String webServerUrl,
		String swaggerContent,
		ApisStatus status, Long quota, RawType rawType, String[] whiteList, String routingUrl, String monitoringUrl) {
		this.apisId = apisId;
		this.developerId = developerId;
		this.developerEmail = developerEmail;
		this.name = name;
		this.webServerUrl = webServerUrl;
		this.swaggerContent = swaggerContent;
		this.status = status;
		this.quota = quota;
		this.rawType = rawType;
		this.whiteList = whiteList;
		this.routingUrl = routingUrl;
		this.monitoringUrl = monitoringUrl;
	}
}
