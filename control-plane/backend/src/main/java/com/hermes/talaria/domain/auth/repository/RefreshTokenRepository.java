package com.hermes.talaria.domain.auth.repository;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.hermes.talaria.global.error.ErrorCode;
import com.hermes.talaria.global.error.exception.AuthenticationException;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {
	private final RedisTemplate redisTemplate;

	public void save(Long memberId, String refreshToken) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(String.valueOf(memberId), refreshToken);
		redisTemplate.expire(String.valueOf(memberId), 7, TimeUnit.DAYS);
	}

	public void delete(Long memberId) {
		redisTemplate.delete(Long.toString(memberId));
	}

	public String findByMemberId(Long memberId) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		String refreshToken = valueOperations.get(String.valueOf(memberId));

		if (Objects.isNull(refreshToken)) {
			throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
		}
		return refreshToken;
	}
}
