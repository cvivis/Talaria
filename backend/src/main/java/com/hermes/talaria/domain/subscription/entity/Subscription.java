package com.hermes.talaria.domain.subscription.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hermes.talaria.domain.subscription.constant.SubscriptionStatus;
import com.hermes.talaria.domain.subscription.dto.SubscriptionDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
@ToString
public class Subscription implements Serializable {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subscriptionId;

	@Column
	@NotNull
	private Long memberId;

	@Column
	@NotNull
	private Long apisId;

	@Column
	private Long keyId;

	@Column
	private String content;

	@Column
	@CreatedDate
	private LocalDateTime subscriptionTime;

	@Enumerated(EnumType.STRING)
	private SubscriptionStatus status;

	@Column
	private String address;

	@Builder
	public Subscription(Long subscriptionId, Long memberId, Long apisId, Long keyId, String content,
		LocalDateTime subscriptionTime,
		SubscriptionStatus status, String address) {
		this.subscriptionId = subscriptionId;
		this.memberId = memberId;
		this.apisId = apisId;
		this.keyId = keyId;
		this.content = content;
		this.subscriptionTime = subscriptionTime;
		this.status = status;
		this.address = address;
	}

	public void update(SubscriptionDto subscriptionDto) {
		this.address = subscriptionDto.getAddress();
		this.content = subscriptionDto.getContent();
		this.status = SubscriptionStatus.PENDING;
		this.subscriptionTime = LocalDateTime.now();
	}

	public void updateStatus(SubscriptionStatus status) {
		this.status = status;
	}
}
