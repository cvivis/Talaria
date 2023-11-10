package com.hermes.monitoring.controller;


import com.hermes.monitoring.dto.api.ApiClientFailHourlyCountDto;
import com.hermes.monitoring.dto.api.ApiClientFailRankingDto;
import com.hermes.monitoring.dto.api.ApiDailyRequestCountDto;
import com.hermes.monitoring.service.api.ApiClientFailService;
import com.hermes.monitoring.service.api.ApiDailyRequestCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-detail")
@RequiredArgsConstructor
public class ApiController {

    private final ApiDailyRequestCountService apiDailyRequestCountService;
    private final ApiClientFailService apiClientFailService;


    @GetMapping("/request-count")
    public ResponseEntity<List<ApiDailyRequestCountDto>> getDailyRequestCount(@RequestParam("api-url")String url, @RequestParam("method") String method){
        List<ApiDailyRequestCountDto> result = apiDailyRequestCountService.getDailyRequestCount(url, method);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/client-fail")
    public ResponseEntity<List<ApiClientFailHourlyCountDto>> getClientFailHourlCount(@RequestParam("api-url")String url, @RequestParam("method") String method){
        List<ApiClientFailHourlyCountDto> result = apiClientFailService.getApiClientHourlyCount(url,method);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/client-fail-ranking")
    public ResponseEntity<List<ApiClientFailRankingDto>> getClientFailRanking(@RequestParam("api-url")String url, @RequestParam("method") String method){
        List<ApiClientFailRankingDto> result = apiClientFailService.getApiClientFailRanking(url,method);
        return ResponseEntity.ok(result);
    }
}
