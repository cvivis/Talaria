package com.hermes.talaria.global.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hermes.talaria.domain.member.dto.MemberDto;
import com.hermes.talaria.domain.member.service.MemberService;
import com.hermes.talaria.global.error.ErrorCode;
import com.hermes.talaria.global.error.exception.AuthenticationException;
import com.hermes.talaria.global.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final MemberService memberService;

	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (authorization == null) {
			filterChain.doFilter(request, response);
			throw new AuthenticationException(ErrorCode.EMPTY_AUTHORIZATION);
		}

		if (!authorization.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			// 헤더가 없거나 잘못됨
			throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
		}

		String token = authorization.split(" ")[1];

		if (jwtUtil.isExpired(token)) {
			filterChain.doFilter(request, response);
			throw new AuthenticationException(ErrorCode.EXPIRED_ACCESS_TOKEN);
			// 토큰이 만료됨
		}

		Long memberId = jwtUtil.getMemberId(token);

		MemberDto memberDto = memberService.getMemberByMemberId(memberId);

		// loginUser 정보로 UsernamePasswordAuthenticationToken 발급
		UsernamePasswordAuthenticationToken authenticationToken = jwtUtil.getAuthentication(memberDto, token);

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		filterChain.doFilter(request, response);
	}
}
