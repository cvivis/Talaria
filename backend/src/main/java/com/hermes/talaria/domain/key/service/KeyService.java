package com.hermes.talaria.domain.key.service;

import java.time.LocalDate;

import com.hermes.talaria.domain.member.entity.Member;
import com.hermes.talaria.domain.member.repository.MemberRepository;
import com.hermes.talaria.global.error.exception.MemberExeption;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.talaria.domain.key.dto.KeyDto;
import com.hermes.talaria.domain.key.entity.Key;
import com.hermes.talaria.domain.key.repository.KeyRepository;
import com.hermes.talaria.global.error.ErrorCode;
import com.hermes.talaria.global.error.exception.KeyException;
import com.hermes.talaria.global.util.ModelMapperUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class KeyService {
	private final KeyRepository keyRepository;
	private final MemberRepository memberRepository;

	public KeyDto reissueKey(Long keyId) {
		// key value, created_time, expiration_time 업데이트
		String keyValue = RandomStringUtils.random(32, true, true);

		while (keyRepository.findByKeyValue(keyValue).isPresent()) {
			keyValue = RandomStringUtils.random(32, true, true);
		}

		LocalDate now = LocalDate.now();

		Key key = keyRepository.findByKeyId(keyId).orElseThrow(() -> new KeyException(ErrorCode.NOT_EXIST_KEY));

		key.updateKey(keyValue, now, now.plusYears(1L));

		KeyDto newKey = ModelMapperUtil.getModelMapper().map(key, KeyDto.class);
		newKey.setKey(keyValue);

		return newKey;
	}

	public Key getKey(Long memberId) {

		Member member = memberRepository.findByMemberId(memberId).orElseThrow(() -> new MemberExeption(ErrorCode.NOT_EXIST_MEMBER));
		Key key = keyRepository.findByKeyId(member.getKeyId()).orElseThrow(() -> new KeyException(ErrorCode.NOT_EXIST_KEY));

		return key;
	}
}
