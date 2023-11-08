package com.hermes.monitoring.service;

import com.hermes.monitoring.dto.ApiServerFailCountDto;
import com.hermes.monitoring.entity.ServerFail;
import com.hermes.monitoring.repository.ApiServerFailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ApiServerFailDbInsertService {
    private final ApiServerFailRepository apiServerFailRepository;

    @Transactional
    public void insert(ApiServerFailCountDto apiServerFailCountDto) {
        Date currentDate = new Date();
        // 1시간 전의 시간을 얻습니다
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.HOUR, -1);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        String yearMonthDay = currentYear + "-" + currentMonth + "-" + currentDay;

        ServerFail serverFail = ServerFail.builder()
                .hour(currentHour)
                .date(yearMonthDay)
                .hourlyCount(apiServerFailCountDto.getCount())
                .url(apiServerFailCountDto.getApi())
                .statusCode(Integer.parseInt(apiServerFailCountDto.getHttpStatusCode()))
                .build();
        apiServerFailRepository.save(serverFail);
    }
}
