package com.hermes.monitoring.service.api;


import com.hermes.monitoring.dto.api.ApiClientFailHourlyCountDto;
import com.hermes.monitoring.repository.ClientFailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
