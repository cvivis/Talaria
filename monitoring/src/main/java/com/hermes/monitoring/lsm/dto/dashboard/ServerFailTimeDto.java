package com.hermes.monitoring.lsm.dto.dashboard;

import lombok.Data;

import java.util.Date;

@Data
public class ServerFailTimeDto {
    Date date; // 모니터링 시간
    Integer time; // 요청 횟수

    public ServerFailTimeDto(Date date, Integer time) {
        this.date = date;
        this.time = time;
    }
}


