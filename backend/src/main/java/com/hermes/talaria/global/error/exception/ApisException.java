package com.hermes.talaria.global.error.exception;

import com.hermes.talaria.global.error.ErrorCode;

public class ApisException extends BusinessException {
	public ApisException(ErrorCode errorCode) {
		super(errorCode);
	}
}