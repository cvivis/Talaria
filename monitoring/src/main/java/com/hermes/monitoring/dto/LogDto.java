package com.hermes.monitoring.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class LogDto {
    String ip;
    Date date;
    String method;
    String status;
    String protocol;
    String url;
    // 시간 값

    public LogDto(String ip, Date date, String method, String url, String status) {
        this.ip = ip;
        this.date = date;
        this.method = method;
        this.url = url;
        this.status = status;
    }
}
