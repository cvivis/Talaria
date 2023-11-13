package com.hermes.monitoring.lsm.dto.dashboard;

import lombok.Data;

import java.util.Date;

@Data
public class AverageResponseTimeDto {
    Date date; // 모니터링 시간
    double time; // 요청 횟수

    public AverageResponseTimeDto(Date date, double time) {
        this.date = date;
        this.time = time;
    }
}


