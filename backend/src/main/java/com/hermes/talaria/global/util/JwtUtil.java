package com.hermes.talaria.global.util;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hermes.talaria.domain.member.dto.MemberDto;
import com.hermes.talaria.global.error.ErrorCode;
import com.hermes.talaria.global.error.exception.AuthenticationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Configuration
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secretKey;
	@Value("${jwt.access-expiry-time}")
	private long accessExpiryTime;
	@Value("${jwt.refresh-expiry-time}")
	private long refreshExpiryTime;

	public String createAccessToken(MemberDto memberDto) {
		// Claim = Jwt Token에 들어갈 정보
		// Claim에 loginId를 넣어 줌으로써 나중에 loginId를 꺼낼 수 있음

		Claims claims = Jwts.claims();
		claims.put("role", memberDto.getRole());

		String key = Base64.getEncoder().encodeToString(secretKey.getBytes());
		// Base64.getEncoder().

		String accessToken = Jwts.builder()
			.setClaims(claims)
			.setSubject(Long.toString(memberDto.getMemberId()))
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + accessExpiryTime))
			.signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
			.compact();

		UsernamePasswordAuthenticationToken authentication = getAuthentication(memberDto, accessToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return accessToken;
	}

	public String createRefreshToken(MemberDto memberDto) {
		// Claim = Jwt Token에 들어갈 정보
		// Claim에 loginId를 넣어 줌으로써 나중에 loginId를 꺼낼 수 있음
		// Claims claims = Jwts.claims();
		// claims.put("role", memberDto.getRole());

		return Jwts.builder()
			.setSubject(Long.toString(memberDto.getMemberId()))
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + refreshExpiryTime))
			.signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
			.compact();
	}

	// Claims에서 loginId 꺼내기
	public Long getMemberId(String token) {
		return Long.parseLong(extractClaims(token).getSubject());
	}

	public boolean isExpired(String token) {
		try {
			Claims claims = Jwts.parser()
				.setSigningKey(secretKey.getBytes())
				.parseClaimsJws(token)
				.getBody();

			Date expiredDate = claims.getExpiration();

			return expiredDate.before(new Date());
		} catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
			throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
		} catch (ExpiredJwtException ignored) {
			return true;
		}

	}

	private Claims extractClaims(String token) {
		try {
			return Jwts.parser()
				.setSigningKey(secretKey.getBytes())
				.parseClaimsJws(token)
				.getBody();
		} catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
			throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
		} catch (ExpiredJwtException e) {
			throw new AuthenticationException(ErrorCode.EXPIRED_ACCESS_TOKEN);
		}
	}

	public UsernamePasswordAuthenticationToken getAuthentication(MemberDto memberDto, String accessToken) {

		return new UsernamePasswordAuthenticationToken(
			memberDto.getMemberId(), accessToken, List.of(new SimpleGrantedAuthority(memberDto.getRole().name())));
	}

	public Claims getExpiredTokenClaims(String token) {
		try {
			return Jwts.parser()
				.setSigningKey(secretKey.getBytes())
				.parseClaimsJws(token)
				.getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}
