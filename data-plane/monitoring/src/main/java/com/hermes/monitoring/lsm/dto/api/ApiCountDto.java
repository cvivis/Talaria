package com.hermes.monitoring.lsm.dto.api;

import lombok.Data;

@Data
public class ApiCountDto {
    String group;
    String statusCode;
    String url;
    String method;
    int count;

    public ApiCountDto(String group, String statusCode, String url, String method) {
        this.group = group;
        this.statusCode = statusCode;
        this.url = url;
        this.method = method;
        this.count = 1;
    }

    public void up() {
        this.count += 1;
    }
}
