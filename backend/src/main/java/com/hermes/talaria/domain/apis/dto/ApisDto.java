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
	private Long memberId;
	private String name;
	private String webServerUrl;
	private String swaggerContent;
	private ApisStatus status;
	private Long quota;
	private RawType rawType;
	private String[] ips;

	@Builder
	public ApisDto(Long apisId, Long memberId, String name, String webServerUrl,
		String swaggerContent, ApisStatus status, Long quota, RawType rawType, String[] ips) {
		this.apisId = apisId;
		this.memberId = memberId;
		this.name = name;
		this.webServerUrl = webServerUrl;
		this.swaggerContent = swaggerContent;
		this.status = status;
		this.quota = quota;
		this.rawType = rawType;
		this.ips = ips;
	}
}
