package com.hermes.talaria.domain.member.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.talaria.domain.key.entity.Key;
import com.hermes.talaria.domain.key.repository.KeyRepository;
import com.hermes.talaria.domain.member.dto.MemberDto;
import com.hermes.talaria.domain.member.dto.MemberResponse;
import com.hermes.talaria.domain.member.entity.Member;
import com.hermes.talaria.domain.member.repository.MemberRepository;
import com.hermes.talaria.global.error.ErrorCode;
import com.hermes.talaria.global.error.exception.AuthenticationException;
import com.hermes.talaria.global.error.exception.KeyException;
import com.hermes.talaria.global.util.ModelMapperUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final KeyRepository keyRepository;

	public MemberResponse signup(MemberDto memberDto, String keyExpirationDate) {

		// 이메일로 유저 가져오기
		if (memberRepository.findByEmail(memberDto.getEmail()).isPresent()) {
			throw new AuthenticationException(ErrorCode.ALREADY_REGISTERED_MEMBER);
		}

		Member member = ModelMapperUtil.getModelMapper().map(memberDto, Member.class);

		String keyValue = RandomStringUtils.random(32, true, true);

		while (keyRepository.findByKeyValue(keyValue).isPresent()) {
			keyValue = RandomStringUtils.random(32, true, true);
		}

		LocalDate expirationDate = LocalDate.parse(keyExpirationDate, DateTimeFormatter.ISO_DATE);

		Key key = Key.of(keyValue, expirationDate);

		key = keyRepository.save(key);

		member = memberRepository.save(member);

		member.updateKeyId(key.getKeyId());

		MemberResponse response = ModelMapperUtil.getModelMapper().map(member, MemberResponse.class);

		response.setKey(key.getKeyValue());
		response.setKeyId(key.getKeyId());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		response.setKeyCreatedDate(key.getCreatedDate().format(formatter));
		response.setKeyExpirationDate(key.getExpirationDate().format(formatter));

		return response;
	}

	public void deleteMember(List<Long> memberIds) {
		for (Long memberId : memberIds) {
			Member member = memberRepository.findByMemberId(memberId)
				.orElseThrow(() -> new AuthenticationException(ErrorCode.NOT_EXIST_MEMBER));
			member.updateDeletedTime(LocalDateTime.now());
		}
	}

	public MemberDto getMemberByMemberId(Long memberId) {
		Member member = memberRepository.findByMemberId(memberId)
			.orElseThrow(() -> new AuthenticationException(ErrorCode.NOT_EXIST_MEMBER));
		if (member.getDeletedTime() != null) {
			throw new AuthenticationException(ErrorCode.DELETED_MEMBER);
		}

		return ModelMapperUtil.getModelMapper().map(member, MemberDto.class);
	}

	public MemberDto getMemberByEmail(String email) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new AuthenticationException(ErrorCode.NOT_EXIST_MEMBER));
		if (member.getDeletedTime() != null) {
			throw new AuthenticationException(ErrorCode.DELETED_MEMBER);
		}

		return ModelMapperUtil.getModelMapper().map(member, MemberDto.class);
	}

	public List<MemberResponse> getAllMember() {
		List<Member> memberList = memberRepository.findByDeletedTimeIsNull();
		List<MemberResponse> result = memberList.stream().map((m) -> {
			Key key = keyRepository.findByKeyId(m.getKeyId())
				.orElseThrow(() -> new KeyException(ErrorCode.NOT_EXIST_KEY));
			MemberResponse response = ModelMapperUtil.getModelMapper().map(m, MemberResponse.class);
			response.setKeyId(key.getKeyId());
			response.setKey(key.getKeyValue());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			response.setKeyCreatedDate(key.getCreatedDate().format(formatter));
			response.setKeyExpirationDate(key.getExpirationDate().format(formatter));

			return response;
		}).collect(Collectors.toList());

		return result;
	}
}
