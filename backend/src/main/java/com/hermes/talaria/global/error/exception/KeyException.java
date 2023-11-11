package com.hermes.talaria.global.error.exception;

import com.hermes.talaria.global.error.ErrorCode;

public class KeyException extends BusinessException {
	public KeyException(ErrorCode errorCode) {
		super(errorCode);
	}
}