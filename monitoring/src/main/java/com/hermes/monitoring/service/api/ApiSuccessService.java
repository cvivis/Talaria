package com.hermes.monitoring.service.api;

import com.hermes.monitoring.dto.ApiDailySuccessRequestCountDto;
import com.hermes.monitoring.repository.ApiSuccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiSuccessService {
    private final ApiSuccessRepository apiSuccessRepository;

    public List<ApiDailySuccessRequestCountDto> getSuccess(String apiUrl, String method){
        return apiSuccessRepository.findSuccess(apiUrl,method);
    }
}
