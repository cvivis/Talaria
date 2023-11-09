package com.hermes.monitoring.service.api;

import com.hermes.monitoring.dto.ApiDailyServerFailRequestCountDto;
import com.hermes.monitoring.repository.ApiServerFailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiServerFailService {
    private final ApiServerFailRepository apiServerFailRepository;

    public List<ApiDailyServerFailRequestCountDto> getServerFailTime(String apiUrl, String method) {
        return apiServerFailRepository.findServerFail(apiUrl,method);
    }
}
