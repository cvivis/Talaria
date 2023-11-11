package com.hermes.talaria.domain.key.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KeyDto {
	Long keyId;
	String key;
	LocalDate createdDate;
	LocalDate expirationDate;

	@Builder
	public KeyDto(Long keyId, String key, LocalDate createdDate, LocalDate expirationDate) {
		this.keyId = keyId;
		this.key = key;
		this.createdDate = createdDate;
		this.expirationDate = expirationDate;
	}
}
