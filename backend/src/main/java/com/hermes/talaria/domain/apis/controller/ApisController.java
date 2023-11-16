package com.hermes.talaria.domain.apis.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.hermes.talaria.domain.apis.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hermes.talaria.domain.apis.constant.ApisStatus;
import com.hermes.talaria.domain.apis.service.ApisService;
import com.hermes.talaria.global.memberinfo.MemberInfo;
import com.hermes.talaria.global.util.JsonParserUtil;
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
	public ResponseEntity<List<ApisResponse>> findAllApis(@MemberInfo Long memberId) {
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

		ApisIdResponse response = ApisIdResponse.ofApisId(apisService.registerOas(apisDto));

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/user")
	public ResponseEntity<List<ApisResponse>> findApprovedOn(@RequestParam String status) {
		List<ApisStatus> statuses = new ArrayList<>();
		if ("approved_on".equals(status))
			statuses.add(ApisStatus.APPROVED_ON);
		List<ApisResponse> response = apisService.getApisByStatus(statuses)
			.stream().map(apisDto -> ModelMapperUtil.getModelMapper().map(apisDto, ApisResponse.class))
			.collect(Collectors.toList());

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/admin")
	public ResponseEntity<List<ApisManagementResponse>> getApisManagementListByStatus(@RequestParam String status) {
		List<ApisStatus> statuses = new ArrayList<>();
		if ("pending".equals(status)) {
			statuses.add(ApisStatus.PENDING);
		} else if ("approved".equals(status)) {
			statuses.add(ApisStatus.APPROVED_ON);
			statuses.add(ApisStatus.APPROVED_OFF);
		}
		List<ApisDto> apisDtoList = apisService.getApisByStatus(statuses);

		List<ApisManagementResponse> response = apisDtoList.stream()
			.map(a -> ModelMapperUtil.getModelMapper().map(a, ApisManagementResponse.class))
			.collect(
				Collectors.toList());

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/admin/{apisId}")
	public ResponseEntity<ApisManagementResponse> getApisManagementByApisId(@PathVariable Long apisId) {

		ApisDto apisDto = apisService.findApisByApisId(apisId);

		ApisManagementResponse response = ModelMapperUtil.getModelMapper().map(apisDto, ApisManagementResponse.class);

		response.setSwaggerContent(JsonParserUtil.parser(apisDto.getSwaggerContent()));

		return ResponseEntity.ok().body(response);
	}

	@PatchMapping("/admin")
	public ResponseEntity<Void> updateApis(@RequestBody ApisManagementRequest request) {
		ApisDto apisDto = ModelMapperUtil.getModelMapper().map(request, ApisDto.class);
		apisService.updateApisManagement(apisDto);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/admin/{apisId}")
	public ResponseEntity<Void> deleteApis(@PathVariable Long apisId) {
		apisService.deleteByAdmin(apisId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/user/me")
	public ResponseEntity<List<ApisSubResponse>> findSubsByStatus(@MemberInfo Long memberId,
		@RequestParam String status) {

		List<ApisSubResponse> response = apisService.findApisSubsByStatus(memberId, status)
			.stream()
			.map(subscriptionDto -> ModelMapperUtil.getModelMapper().map(subscriptionDto, ApisSubResponse.class))
			.collect(Collectors.toList());

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/user/product")
	public ResponseEntity<ProductResponse> getProductDetail(@RequestParam String apisName) {
		ApisDto apisDto = apisService.findApisByApisName(apisName);
		ProductResponse response = ModelMapperUtil.getModelMapper().map(apisDto, ProductResponse.class);
		response.setSwaggerContent(JsonParserUtil.parser(apisDto.getSwaggerContent()));
		return ResponseEntity.ok().body(response);
	}

}
