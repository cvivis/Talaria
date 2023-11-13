package com.hermes.monitoring.cvivis.service.api.group;

import com.hermes.monitoring.dto.api.ApiCountDto;
import com.hermes.monitoring.entity.ServerFail;
import com.hermes.monitoring.entity.Success;
import com.hermes.monitoring.repository.ApiServerFailRepository;
import com.hermes.monitoring.repository.ApiSuccessRepository;
import java.util.Calendar;
import java.util.Date;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiGroupDbInsertService {
    private final ApiSuccessRepository apiSuccessRepository;
    private final ApiServerFailRepository apiServerFailRepository;

    public void insert(Long httpStatusCode, ApiCountDto apiCountDto) {
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

        switch (httpStatusCode.intValue()){
            case 200:
                insertSuccess(yearMonthDay, currentHour, apiCountDto);
                break;
            case 400:
                insertClientFail(yearMonthDay, currentHour, apiCountDto);
                break;
            case 500:
                insertServerFail(yearMonthDay, currentHour, apiCountDto);
                break;
            default:
                break;
        }
    }

    @Transactional
    public void insertClientFail(String yearMonthDay, int currentHour, ApiCountDto apiCountDto) {
        // TODO: 400일때 처리
    }


    @Transactional
    public void insertSuccess(String yearMonthDay, int currentHour, ApiCountDto apiCountDto) {
        Success success = Success.builder()
                .hour(currentHour)
                .date(yearMonthDay)
                .hourlyCount(apiCountDto.getCount())
                .url(apiCountDto.getUrl())
                .statusCode(Integer.parseInt(apiCountDto.getStatusCode()))
                .method(apiCountDto.getMethod())
                .groupName(apiCountDto.getGroup())
                .build();
        apiSuccessRepository.save(success);
    }

    @Transactional
    public void insertServerFail(String yearMonthDay, int currentHour, ApiCountDto apiCountDto) {
        ServerFail serverFail = ServerFail.builder()
                .hour(currentHour)
                .date(yearMonthDay)
                .hourlyCount(apiCountDto.getCount())
                .url(apiCountDto.getUrl())
                .statusCode(Integer.parseInt(apiCountDto.getStatusCode()))
                .method(apiCountDto.getMethod())
                .groupName(apiCountDto.getGroup())
                .build();
        apiServerFailRepository.save(serverFail);
    }


}
