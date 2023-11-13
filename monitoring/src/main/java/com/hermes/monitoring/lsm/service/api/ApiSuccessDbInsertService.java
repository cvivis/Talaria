package com.hermes.monitoring.lsm.service.api;

import com.hermes.monitoring.lsm.dto.api.ApiSuccessCountDto;
import com.hermes.monitoring.entity.Success;
import com.hermes.monitoring.repository.ApiSuccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ApiSuccessDbInsertService {

    private final ApiSuccessRepository apiSuccessRepository;

    @Transactional
    public void insert(ApiSuccessCountDto apiSuccessCountDto) {
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

        Success success = Success.builder()
                .hour(currentHour)
                .date(yearMonthDay)
                .hourlyCount(apiSuccessCountDto.getCount())
                .url(apiSuccessCountDto.getApi())
                .statusCode(Integer.parseInt(apiSuccessCountDto.getHttpStatusCode()))
                .method(apiSuccessCountDto.getMethod())
                .build();
        apiSuccessRepository.save(success);
    }
}
