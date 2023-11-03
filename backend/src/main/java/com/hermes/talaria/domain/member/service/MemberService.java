package com.hermes.talaria.domain.member.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.talaria.domain.key.entity.Key;
import com.hermes.talaria.domain.key.repository.KeyRepository;
import com.hermes.talaria.domain.member.dto.MemberDto;
import com.hermes.talaria.domain.member.entity.Member;
import com.hermes.talaria.domain.member.repository.MemberRepository;
import com.hermes.talaria.global.error.ErrorCode;
import com.hermes.talaria.global.error.exception.AuthenticationException;
import com.hermes.talaria.global.util.ModelMapperUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final KeyRepository keyRepository;

	public void signup(MemberDto memberDto, String keyExpirationDate) {

		// 이메일로 유저 가져오기
		if(memberRepository.findByEmail(memberDto.getEmail()).isPresent()) {
			throw new AuthenticationException(ErrorCode.ALREADY_REGISTERED_MEMBER);
		}

		Member member = ModelMapperUtil.getModelMapper().map(memberDto, Member.class);

		String keyValue = RandomStringUtils.random(32, true, true);

		while (keyRepository.findByKeyValue(keyValue).isPresent()) {
			keyValue = RandomStringUtils.random(32, true, true);
		}

		LocalDate expirationDate = LocalDate.parse(keyExpirationDate, DateTimeFormatter.ISO_DATE);

		Key key = Key.of(keyValue, expirationDate);

		Long keyId = keyRepository.save(key).getKeyId();

		memberRepository.save(member);

		member.updateKeyId(keyId);
	}

	public MemberDto getMemberByMemberId(Long memberId) {
		Member member = memberRepository.findByMemberId(memberId).orElseThrow(() -> new AuthenticationException(ErrorCode.NOT_EXIST_MEMBER));
		if (member.getDeletedTime() != null) {
			throw new AuthenticationException(ErrorCode.DELETED_MEMBER);
		}

		return ModelMapperUtil.getModelMapper().map(member, MemberDto.class);
	}

	public MemberDto getMemberByEmail(String email) {
		Member member = memberRepository.findByEmail(email).orElseThrow(() -> new AuthenticationException(ErrorCode.NOT_EXIST_MEMBER));
		if (member.getDeletedTime() != null) {
			throw new AuthenticationException(ErrorCode.DELETED_MEMBER);
		}

		return ModelMapperUtil.getModelMapper().map(member, MemberDto.class);
	}

	public void deleteMember(Long memberId) {
		Member member = memberRepository.findByMemberId(memberId).orElseThrow(() -> new AuthenticationException(ErrorCode.NOT_EXIST_MEMBER));
		member.updateDeletedTime(LocalDateTime.now());
	}
}
