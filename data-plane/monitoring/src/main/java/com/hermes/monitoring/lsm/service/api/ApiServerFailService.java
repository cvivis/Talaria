package com.hermes.monitoring.lsm.service.api;

import com.hermes.monitoring.cvivis.dto.api.ApiClientFailRankingDto;
import com.hermes.monitoring.lsm.dto.api.ApiHourlyCountDto;
import com.hermes.monitoring.lsm.dto.api.ApiRankingDto;
import com.hermes.monitoring.repository.ApiServerFailRepository;
import com.hermes.monitoring.repository.ApiServerFailRepository.ApiRankingVo;
import java.util.ArrayList;
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
        List<ApiRankingDto> result = new ArrayList<>();
        List<ApiRankingVo> list = apiServerFailRepository.findStatusCodeAndCountAndAvgCount(groupName);
        for(ApiRankingVo item : list){
            result.add(ApiRankingDto.builder()
                    .statusCode(item.getStatusCode())
                    .count(item.getCount())
                    .avgCount(item.getAvgCount())
                    .build());
        }
        return result;
    }
}
