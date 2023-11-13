package com.hermes.monitoring.lsm.dto.api;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiServerFailCountDto {
    private String api;
    private String method;
    private String httpStatusCode;
    private int count;

    public ApiServerFailCountDto(String api, String method, String httpStatusCode, int count) {
        this.api = api;
        this.method = method;
        this.httpStatusCode = httpStatusCode;
        this.count = count;
    }

    public void up() {
        this.count += 1;
    }
}
