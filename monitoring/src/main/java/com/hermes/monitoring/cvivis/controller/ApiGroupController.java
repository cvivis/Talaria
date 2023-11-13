package com.hermes.monitoring.cvivis.controller;


import com.hermes.monitoring.cvivis.dto.api.ApiClientFailHourlyCountDto;
import com.hermes.monitoring.cvivis.dto.api.ApiClientFailRankingDto;
import com.hermes.monitoring.cvivis.dto.api.ApiDailyRequestCountDto;
import com.hermes.monitoring.cvivis.service.api.detail.ApiClientFailService;
import com.hermes.monitoring.cvivis.service.api.detail.ApiDailyRequestCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/group-detail")
@RequiredArgsConstructor
public class ApiGroupController {

    private final ApiClientFailService apiClientFailService;
    private final ApiDailyRequestCountService apiDailyRequestCountService;


    @GetMapping("/client-fail")
    public ResponseEntity<List<ApiClientFailHourlyCountDto>> getApiGroupClientFailHourlyCountDto(@RequestParam("group-name")String routingUrl){
       List<ApiClientFailHourlyCountDto> result = apiClientFailService.getApiClientGroupHourlyCount(routingUrl);
       return ResponseEntity.ok(result);
    }

    @GetMapping("/request-count")
    public ResponseEntity<List<ApiDailyRequestCountDto>> getDailyRequestCount(@RequestParam("group-name")String groupUrl){
        List<ApiDailyRequestCountDto> result = apiDailyRequestCountService.getDailyRequestGroupCount(groupUrl);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/client-fail-ranking")
    public ResponseEntity<List<ApiClientFailRankingDto>> getClientFailRanking(@RequestParam("group-name")String groupUrl){
        List<ApiClientFailRankingDto> result = apiClientFailService.getApiClientFailGroupRanking(groupUrl);
        return ResponseEntity.ok(result);
    }
}
