package com.hermes.talaria.global.error.exception;

import com.hermes.talaria.global.error.ErrorCode;

public class AuthenticationException extends BusinessException{
	public AuthenticationException(ErrorCode errorCode){
		super(errorCode);
	}
}