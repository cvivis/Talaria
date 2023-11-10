package com.hermes.talaria.domain.apis.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.talaria.domain.apis.dto.ApisDto;
import com.hermes.talaria.domain.apis.entity.Apis;
import com.hermes.talaria.domain.apis.repository.ApisRepository;
import com.hermes.talaria.global.error.ErrorCode;
import com.hermes.talaria.global.error.exception.BusinessException;
import com.hermes.talaria.global.util.ModelMapperUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ApisService {

	private final ApisRepository apisRepository;

	public Long create(ApisDto apisDto) {
		Apis apis = apisRepository.save(ModelMapperUtil.getModelMapper().map(apisDto, Apis.class));

		return apis.getApisId();
	}

	public List<ApisDto> findApisByDeveloperId(Long memberId) {
		List<Apis> apisList = apisRepository.findApisByDeveloperId(memberId);

		return apisList.stream()
			.map(apis -> ModelMapperUtil.getModelMapper().map(apis, ApisDto.class))
			.collect(Collectors.toList());
	}

	public Long update(ApisDto apisDto) {
		Apis apis = apisRepository.findApisByApisId(apisDto.getApisId()).orElseThrow(() -> new BusinessException(
			ErrorCode.NOT_EXIST_APIS));

		if (!apis.getDeveloperId().equals(apisDto.getDeveloperId())) {
			throw new BusinessException(ErrorCode.WRONG_AUTHORITY);
		}

		apis.update(apisDto);

		return apis.getApisId();
	}

	public Long delete(Long memberId, Long apisId) {
		Apis apis = apisRepository.findApisByApisId(apisId).orElseThrow(() -> new BusinessException(
			ErrorCode.NOT_EXIST_APIS));

		if (!apis.getDeveloperId().equals(apisId)) {
			throw new BusinessException(ErrorCode.WRONG_AUTHORITY);
		}

		apisRepository.deleteApisByApisId(apisId);

		return apisId;
	}

	public Long registerOas(ApisDto apisDto) {
		Apis apis = apisRepository.findApisByApisId(apisDto.getApisId()).orElseThrow(() -> new BusinessException(
			ErrorCode.NOT_EXIST_APIS));

		if (!apis.getDeveloperId().equals(apisDto.getDeveloperId())) {
			throw new BusinessException(ErrorCode.WRONG_AUTHORITY);
		}

		apis.registerOas(apisDto);

		return apis.getApisId();
	}
}
