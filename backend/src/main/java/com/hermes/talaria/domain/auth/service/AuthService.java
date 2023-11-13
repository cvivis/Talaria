package com.hermes.talaria.domain.auth.service;

import org.springframework.stereotype.Service;

import com.hermes.talaria.domain.auth.repository.RefreshTokenRepository;
import com.hermes.talaria.domain.member.dto.MemberDto;
import com.hermes.talaria.domain.member.service.MemberService;
import com.hermes.talaria.global.error.ErrorCode;
import com.hermes.talaria.global.error.exception.AuthenticationException;
import com.hermes.talaria.global.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberService memberService;
	private final JwtUtil jwtUtil;
	private final RefreshTokenRepository refreshTokenRepository;

	public MemberDto login(String email, String password) {
		MemberDto memberDto = memberService.getMemberByEmail(email);
		if (!memberDto.getPassword().equals(password)) {
			throw new AuthenticationException(ErrorCode.NOT_EXIST_MEMBER);
		}

		return memberDto;
	}

	public String getAccessToken(String accessToken, String refreshToken) {

		if (accessToken == null) {    // 액세스 토큰이 없음
			throw new AuthenticationException(ErrorCode.EMPTY_AUTHORIZATION);
		}

		if (!accessToken.startsWith("Bearer ")) {// 액세스 토큰이 Bearer 타입이 아님
			throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
		}

		accessToken = accessToken.split(" ")[1];

		if (!jwtUtil.isExpired(accessToken)) {    // 액세스 토큰이 만료되지 않음
			throw new AuthenticationException(ErrorCode.NOT_EXPIRED_ACCESS_TOKEN);
		}

		Long memberId = Long.valueOf(jwtUtil.getExpiredTokenClaims(accessToken).getSubject());

		String refreshTokenFromRedis = refreshTokenRepository.findByMemberId(memberId);

		if (!refreshToken.equals(refreshTokenFromRedis)) {
			throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
		}

		Long memberIdFromToken = jwtUtil.getMemberId(refreshToken);

		if (memberId != memberIdFromToken) {
			throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
		}

		MemberDto memberDto = memberService.getMemberByMemberId(memberId);

		return jwtUtil.createAccessToken(memberDto);
	}

	public String createAccessToken(MemberDto memberDto) {
		return jwtUtil.createAccessToken(memberDto);
	}

	public String createRefreshToken(MemberDto memberDto) {
		String refreshToken = jwtUtil.createRefreshToken(memberDto);
		refreshTokenRepository.save(memberDto.getMemberId(), refreshToken);
		return refreshToken;
	}

	public void deleteRefreshToken(Long memberId) {
		refreshTokenRepository.delete(memberId);
	}
}
