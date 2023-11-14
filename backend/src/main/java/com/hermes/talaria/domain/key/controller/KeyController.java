package com.hermes.talaria.domain.key.controller;

import java.time.format.DateTimeFormatter;

import com.hermes.talaria.domain.key.entity.Key;
import com.hermes.talaria.global.memberinfo.MemberInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hermes.talaria.domain.key.dto.KeyDto;
import com.hermes.talaria.domain.key.dto.KeyReissueRequest;
import com.hermes.talaria.domain.key.dto.KeyReissueResponse;
import com.hermes.talaria.domain.key.service.KeyService;
import com.hermes.talaria.global.util.ModelMapperUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/keys")
@RequiredArgsConstructor
public class KeyController {
	private final KeyService keyService;

	@PostMapping("/admin")
	public ResponseEntity<KeyReissueResponse> reissueKey(@RequestBody KeyReissueRequest request) {

		KeyDto keyDto = keyService.reissueKey(request.getKeyId());

		KeyReissueResponse keyReissueResponse = ModelMapperUtil.getModelMapper().map(keyDto, KeyReissueResponse.class);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		keyReissueResponse.setKeyCreatedDate(keyDto.getCreatedDate().format(formatter));
		keyReissueResponse.setKeyExpirationDate(keyDto.getExpirationDate().format(formatter));

		return ResponseEntity.ok().body(keyReissueResponse);     // 응답으로
	}

	@GetMapping("/user")
	public ResponseEntity<KeyReissueResponse> getKey(@MemberInfo Long memberId) {

		System.out.println(memberId);
		Key key = keyService.getKey(memberId);
		System.out.println(key.toString());
		KeyReissueResponse keyReissueResponse = ModelMapperUtil.getModelMapper().map(key, KeyReissueResponse.class);

		return ResponseEntity.ok().body(keyReissueResponse);
	}
}
