package com.hermes.monitoring.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class LogDto {
    String ipAddress;
    Date dateTime;
    String httpMethod;
    String path;
    String statusCode;
    String httpVersion;
    // 요청 시간
    Double requestTime;
    // 서버 응답 시간
    Double responseTime;


    public LogDto(String ipAddress, Date dateTime, String httpMethod, String path, String httpVersion, String statusCode, double requestTime, double responseTime) {
        this.ipAddress = ipAddress;
        this.dateTime = dateTime;
        this.httpMethod = httpMethod;
        this.path = path;
        this.statusCode = statusCode;
        this.httpVersion = httpVersion;
        this.requestTime = requestTime;
        this.responseTime = responseTime;
    }

    public LogDto(String ipAddress, Date dateTime, String httpMethod, String path, String httpVersion, String statusCode, double requestTime) {
        this.ipAddress = ipAddress;
        this.dateTime = dateTime;
        this.httpMethod = httpMethod;
        this.path = path;
        this.statusCode = statusCode;
        this.httpVersion = httpVersion;
        this.requestTime = requestTime;
    }
}
