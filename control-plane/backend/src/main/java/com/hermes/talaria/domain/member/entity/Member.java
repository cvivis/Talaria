package com.hermes.talaria.domain.member.entity;

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

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hermes.talaria.domain.auth.constant.Role;

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
public class Member implements Serializable {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	@Column(length = 30, unique = true)
	private String email;

	@Column(length = 20, nullable = false)
	private String password;

	@Column(length = 20, nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(unique = true, nullable = true)
	private Long keyId;

	@Column
	@CreatedDate
	private LocalDateTime createdTime;

	@Column (nullable = true)
	private LocalDateTime deletedTime;

	@Builder
	public Member(Long memberId, String email, String password, Role role, Long keyId, LocalDateTime createdTime, LocalDateTime deletedTime) {
		this.memberId = memberId;
		this.email = email;
		this.password = password;
		this.role = role;
		this.keyId = keyId;
		this.createdTime = createdTime;
		this.deletedTime = deletedTime;
	}

	public void updateDeletedTime(LocalDateTime deletedTime) {
		this.deletedTime = deletedTime;
	}

	public void updateKeyId(Long keyId) {
		this.keyId = keyId;
	}
}
