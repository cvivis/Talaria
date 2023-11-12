package com.hermes.monitoring.dto.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@Builder
public class LogGroupDto {
    String ip;
    Date dateTime;
    String httpMethod;
    String path;
    String routingPath;
    String statusCode;
    String httpProtocol;
    String requestTime;
    String responseTime;
}
