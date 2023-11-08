package com.hermes.monitoring.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiServerFailCountDto {
    private String api;
    private String httpStatusCode;
    private int count;

    public ApiServerFailCountDto(String api, String httpStatusCode, int count) {
        this.api = api;
        this.httpStatusCode = httpStatusCode;
        this.count = count;
    }

    public void up() {
        this.count += 1;
    }
}
