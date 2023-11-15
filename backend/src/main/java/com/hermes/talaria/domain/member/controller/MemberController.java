package com.hermes.talaria.domain.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hermes.talaria.domain.member.dto.MemberDeleteRequest;
import com.hermes.talaria.domain.member.dto.MemberDto;
import com.hermes.talaria.domain.member.dto.MemberResponse;
import com.hermes.talaria.domain.member.dto.SignupRequest;
import com.hermes.talaria.domain.member.service.MemberService;
import com.hermes.talaria.global.util.ModelMapperUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/admin/signup")
	public ResponseEntity<MemberResponse> signup(@RequestBody SignupRequest request) {
		MemberDto memberDto = ModelMapperUtil.getModelMapper().map(request, MemberDto.class);
		MemberResponse response = memberService.signup(memberDto, request.getKeyExpirationDate());

		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/admin")
	public ResponseEntity<Void> deleteMember(@RequestBody MemberDeleteRequest request) {
		memberService.deleteMember(request.getMemberIds());

		return ResponseEntity.ok().build();
	}

	@GetMapping("/admin")
	public ResponseEntity<List<MemberResponse>> getAllMember() {
		// memberService.deleteMember(memberId);
		List<MemberResponse> responses = memberService.getAllMember();

		return ResponseEntity.ok().body(responses);
	}

}
