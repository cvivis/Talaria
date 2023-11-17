package com.hermes.talaria.global.error.exception;

import com.hermes.talaria.global.error.ErrorCode;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
	private ErrorCode errorCode;

	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}