package com.hermes.monitoring.service.api;

import com.hermes.monitoring.dto.api.ApiDailyRequestCountDto;
import com.hermes.monitoring.repository.RequestCountRepository;
import com.hermes.monitoring.repository.RequestGroupCountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiDailyRequestCountService {
    private final RequestCountRepository requestCountRepository;

    private final RequestGroupCountRepository requestGroupCountRepository;

    public List<ApiDailyRequestCountDto> getDailyRequestCount(String url, String method){
        List<ApiDailyRequestCountDto> result = requestCountRepository.findDateAndCountByUrlAndMethod(url,method).stream().limit(12).collect(Collectors.toList());
//        for(ApiDailyRequestCountDto item : list){
//            log.info("listItem : {}",item.getDate());
//            log.info("listItem : {}",item.getCount());
//        }
        return result;
    }

    public List<ApiDailyRequestCountDto> getDailyRequestGroupCount(String groupUrl){
        List<ApiDailyRequestCountDto> result = requestGroupCountRepository.findDateAndCountByUrlAndMethod(groupUrl);
        return result;
    }
}
