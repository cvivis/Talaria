package com.hermes.monitoring.lsm.service.api;

import com.hermes.monitoring.lsm.dto.api.ApiHourlyCountDto;
import com.hermes.monitoring.repository.ApiSuccessRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiSuccessService {
    private final ApiSuccessRepository apiSuccessRepository;

    public List<ApiHourlyCountDto> getApiGroupSuccessHourlyCount(String groupName) {
        return apiSuccessRepository.findDateAndCountByGroupName(groupName);
    }
}
