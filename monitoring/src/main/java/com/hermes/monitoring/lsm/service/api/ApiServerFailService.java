package com.hermes.monitoring.lsm.service.api;

import com.hermes.monitoring.lsm.dto.api.ApiHourlyCountDto;
import com.hermes.monitoring.lsm.dto.api.ApiRankingDto;
import com.hermes.monitoring.repository.ApiServerFailRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiServerFailService {

    private final ApiServerFailRepository apiServerFailRepository;

    public List<ApiHourlyCountDto> getApiGroupServerFailHourlyCount(String groupName) {
        return apiServerFailRepository.findDateAndCountByGroupName(groupName);
    }

    public List<ApiRankingDto> getApiGroupServerFailRanking(String groupName) {
        return apiServerFailRepository.findStatusCodeAndCountAndAvgCount(groupName);
    }
}
