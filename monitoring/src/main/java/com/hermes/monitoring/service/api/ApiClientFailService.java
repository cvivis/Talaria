package com.hermes.monitoring.service.api;


import com.hermes.monitoring.dto.api.ApiClientFailHourlyCountDto;
import com.hermes.monitoring.dto.api.ApiClientFailRankingDto;
import com.hermes.monitoring.repository.ClientFailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiClientFailService {
    private final ClientFailRepository clientFailRepository;

    public List<ApiClientFailHourlyCountDto> getApiClientHourlyCount(String url, String method){
        List<ApiClientFailHourlyCountDto> result = clientFailRepository.findHourAndCountByUrlAndMethod(url,method).stream().limit(7).collect(Collectors.toList());
        return result;
    }

    public List<ApiClientFailRankingDto> getApiClientFailRanking(String url, String method){
        List<ClientFailRepository.ApiClientFailRankingVo> list = clientFailRepository.findTodayAndAvgCount(url,method);
        List<ApiClientFailRankingDto> result = new ArrayList<>();
        for(ClientFailRepository.ApiClientFailRankingVo item : list){
            result.add(ApiClientFailRankingDto.builder()
                            .statusCode(item.getStatusCode())
                            .count(item.getCount())
                            .avgCount(item.getAvg())
                    .build());
        }
        return result;
    }
}
