package com.hermes.talaria.domain.apis.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
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
@DynamicInsert
public class Apis implements Serializable {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long apisId;

	@Column
	private Long developerId;

	@Column
	private String name;

	@Column
	private String webServerUrl;

	@Column(columnDefinition = "LONGTEXT")
	private String swaggerContent;

	@Enumerated(EnumType.STRING)
	@ColumnDefault("'PENDING'")
	private ApisStatus status;

	@Column
	private Long quota;

	@Enumerated(EnumType.STRING)
	@ColumnDefault("'JSON'")
	private RawType rawType;

	@Type(type = "string-array")
	@Column(columnDefinition = "text[]")
	private String[] whiteList;

	@Column
	private String routingUrl;

	@Builder
	public Apis(Long apisId, Long developerId, String name, String webServerUrl, String swaggerContent,
		ApisStatus status, Long quota, RawType rawType, String[] whiteList, String routingUrl) {
		this.apisId = apisId;
		this.developerId = developerId;
		this.name = name;
		this.webServerUrl = webServerUrl;
		this.swaggerContent = swaggerContent;
		this.status = status;
		this.quota = quota;
		this.rawType = rawType;
		this.whiteList = whiteList;
		this.routingUrl = routingUrl;
	}

	public void registerOas(ApisDto apisDto) {
		this.swaggerContent = apisDto.getSwaggerContent();
		this.rawType = apisDto.getRawType();
	}

	public void updateWebServerUrl(String webServerUrl) {
		this.webServerUrl = webServerUrl;
	}

	public void updateName(String name) {
		this.name = name;
	}

	public void updateQuota(Long quota) {
		this.quota = quota;
	}

	public void updateStatus(ApisStatus status) {
		this.status = status;
	}

	public void updateWhiteList(String[] whiteList) {
		this.whiteList = whiteList;
	}
}
