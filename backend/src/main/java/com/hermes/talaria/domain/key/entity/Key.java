package com.hermes.talaria.domain.key.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
public class Key implements Serializable {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long keyId;

	@Column(length = 32, unique = true)
	private String keyValue;

	@Column
	@CreatedDate
	private LocalDate createdDate;

	@Column
	private LocalDate expirationDate;

	@Builder
	public Key(Long keyId, String keyValue, LocalDate createdDate, LocalDate expirationDate) {
		this.keyId = keyId;
		this.keyValue = keyValue;
		this.createdDate = createdDate;
		this.expirationDate = expirationDate;
	}

	public static Key of(String keyValue, LocalDate expirationDate) {
		return Key.builder()
			.keyValue(keyValue)
			.expirationDate(expirationDate)
			.build();
	}
}
