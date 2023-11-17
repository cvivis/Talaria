package com.hermes.talaria.global.error.exception;

import com.hermes.talaria.global.error.ErrorCode;

public class MemberExeption extends BusinessException{
    public MemberExeption(ErrorCode errorCode) {super(errorCode);}
}
