package com.hermes.talaria.domain.apis.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hermes.talaria.domain.apis.dto.ApisDto;
import com.hermes.talaria.domain.apis.dto.ApisRequest;
import com.hermes.talaria.domain.apis.dto.ApisResponse;
import com.hermes.talaria.domain.apis.dto.OasRequest;
import com.hermes.talaria.domain.apis.service.ApisService;
import com.hermes.talaria.global.memberinfo.MemberInfo;
import com.hermes.talaria.global.util.ModelMapperUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/apis")
@RequiredArgsConstructor
public class ApisController {

	private final ApisService apisService;

	@PostMapping("/developer")
	public ResponseEntity<ApisIdResponse> create(@MemberInfo Long memberId, @RequestBody ApisRequest request) {
		ApisDto apisDto = ModelMapperUtil.getModelMapper().map(request, ApisDto.class);
		apisDto.setDeveloperId(memberId);
		ApisIdResponse response = ApisIdResponse.ofApisId(apisService.create(apisDto));

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/developer")
	public ResponseEntity<List<ApisResponse>> findApis(@MemberInfo Long memberId) {
		List<ApisResponse> response = apisService.findApisByDeveloperId(memberId).stream()
			.map(apisDto -> ModelMapperUtil.getModelMapper().map(apisDto, ApisResponse.class))
			.collect(Collectors.toList());

		return ResponseEntity.ok().body(response);
	}

	@PatchMapping("/developer/{apisId}")
	public ResponseEntity<ApisIdResponse> update(@MemberInfo Long memberId, @PathVariable Long apisId,
		@RequestBody ApisRequest request) {
		ApisDto apisDto = ModelMapperUtil.getModelMapper().map(request, ApisDto.class);
		apisDto.setApisId(apisId);
		apisDto.setDeveloperId(memberId);
		ApisIdResponse response = ApisIdResponse.ofApisId(apisService.update(apisDto));

		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("/developer/{apisId}")
	public ResponseEntity<ApisIdResponse> delete(@MemberInfo Long memberId, @PathVariable Long apisId) {
		ApisIdResponse response = ApisIdResponse.ofApisId(apisService.delete(memberId, apisId));

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/developer/{apisId}")
	public ResponseEntity<OasResponse> findApis(@MemberInfo Long memberId, @PathVariable Long apisId) {
		OasResponse response = ModelMapperUtil.getModelMapper()
			.map(apisService.findApisByApisId(memberId, apisId), OasResponse.class);

		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/developer/oas/{apisId}")
	public ResponseEntity<ApisIdResponse> registerOas(@MemberInfo Long memberId, @PathVariable Long apisId,
		@RequestBody OasRequest request) {
		ApisDto apisDto = ModelMapperUtil.getModelMapper().map(request, ApisDto.class);
		apisDto.setApisId(apisId);
		apisDto.setDeveloperId(memberId);
		ApisResponse response = ApisResponse.ofApisId(apisService.registerOas(apisDto));

		return ResponseEntity.ok().body(response);
	}

}
