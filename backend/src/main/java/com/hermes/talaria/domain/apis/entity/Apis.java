package com.hermes.talaria.domain.apis.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hermes.talaria.domain.apis.constant.ApisStatus;
import com.hermes.talaria.domain.apis.constant.RawType;
import com.hermes.talaria.domain.apis.dto.ApisDto;

import io.hypersistence.utils.hibernate.type.array.StringArrayType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@ToString
@TypeDef(name = "string-array", typeClass = StringArrayType.class)
public class Apis implements Serializable {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long apisId;

	@Column
	private Long memberId;

	@Column
	private String name;

	@Column
	private String webServerUrl;

	@Column
	private String urlSuffix;

	@Column
	private String swaggerContent;

	@Column
	private ApisStatus status;

	@Column
	private Long quota;

	@Column
	private RawType rawType;

	@Type(type = "string-array")
	// @Column(columnDefinition = "text[]")
	private String[] ips;

	@Builder
	public Apis(Long apisId, Long memberId, String name, String webServerUrl, String urlSuffix,
		String swaggerContent, ApisStatus status, Long quota, RawType rawType, String[] ips) {
		this.apisId = apisId;
		this.memberId = memberId;
		this.name = name;
		this.webServerUrl = webServerUrl;
		this.urlSuffix = urlSuffix;
		this.swaggerContent = swaggerContent;
		this.status = status;
		this.quota = quota;
		this.rawType = rawType;
		this.ips = ips;
	}

	public void update(ApisDto apisDto) {
		this.name = apisDto.getName();
		this.webServerUrl = apisDto.getWebServerUrl();
		this.urlSuffix = apisDto.getUrlSuffix();
	}

	public void registerOas(ApisDto apisDto) {
		this.swaggerContent = apisDto.getSwaggerContent();
		this.rawType = apisDto.getRawType();
	}
}
