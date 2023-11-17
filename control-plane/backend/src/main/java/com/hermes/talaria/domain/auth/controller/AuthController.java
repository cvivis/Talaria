package com.hermes.talaria.domain.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hermes.talaria.domain.auth.dto.LoginRequest;
import com.hermes.talaria.domain.auth.dto.LoginResponse;
import com.hermes.talaria.domain.auth.dto.RefreshRequest;
import com.hermes.talaria.domain.auth.dto.RefreshResponse;
import com.hermes.talaria.domain.auth.service.AuthService;
import com.hermes.talaria.domain.member.dto.MemberDto;
import com.hermes.talaria.global.memberinfo.MemberInfo;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

		MemberDto memberDto = authService.login(request.getEmail(), request.getPassword());
		String accessToken = authService.createAccessToken(memberDto);
		String refreshToken = authService.createRefreshToken(memberDto);

		LoginResponse response = LoginResponse.of(memberDto, accessToken, refreshToken);

		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/refresh")
	public ResponseEntity<RefreshResponse> getAccessToken(HttpServletRequest request,
		@RequestBody RefreshRequest requestBody) {

		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);

		RefreshResponse response = RefreshResponse.fromRefreshToken(
			authService.getAccessToken(accessToken, requestBody.getRefreshToken()));

		return ResponseEntity.ok(response);
	}

	@GetMapping("/logout")
	public ResponseEntity<Void> logout(@MemberInfo Long memberId) {
		authService.deleteRefreshToken(memberId);
		return ResponseEntity.ok().build();
	}
}
