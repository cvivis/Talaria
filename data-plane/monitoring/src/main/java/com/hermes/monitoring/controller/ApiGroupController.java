package com.hermes.monitoring.controller;

import com.hermes.monitoring.cvivis.dto.api.ApiClientFailHourlyCountDto;
import com.hermes.monitoring.cvivis.dto.api.ApiClientFailRankingDto;
import com.hermes.monitoring.cvivis.dto.api.ApiDailyRequestCountDto;
import com.hermes.monitoring.cvivis.service.api.detail.ApiClientFailService;
import com.hermes.monitoring.cvivis.service.api.detail.ApiDailyRequestCountService;
import com.hermes.monitoring.lsm.dto.api.ApiHourlyCountDto;
import com.hermes.monitoring.lsm.dto.api.ApiRankingDto;
import com.hermes.monitoring.lsm.service.api.ApiServerFailService;
import com.hermes.monitoring.lsm.service.api.ApiSuccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/group-detail")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ApiGroupController {

    private final ApiClientFailService apiClientFailService;
    private final ApiDailyRequestCountService apiDailyRequestCountService;
    private final ApiSuccessService apiSuccessService;
    private final ApiServerFailService apiServerFailService;


    @GetMapping("/check")
    public ResponseEntity<String> getCheck(){
        return ResponseEntity.ok("Check");
    }

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

    @GetMapping("/success")
    public ResponseEntity<List<ApiHourlyCountDto>> getApiGroupSuccessHourlyCount(@RequestParam("group-name")String groupName){
        List<ApiHourlyCountDto> result = apiSuccessService.getApiGroupSuccessHourlyCount(groupName);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/server-fail")
    public ResponseEntity<List<ApiHourlyCountDto>> getApiGroupServerFailHourlyCount(@RequestParam("group-name")String groupName){
        List<ApiHourlyCountDto> result = apiServerFailService.getApiGroupServerFailHourlyCount(groupName);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/server-fail-ranking")
    public ResponseEntity<List<ApiRankingDto>> getApiGroupServerFailRanking(@RequestParam("group-name")String groupName){
        List<ApiRankingDto> result = apiServerFailService.getApiGroupServerFailRanking(groupName);
        return ResponseEntity.ok(result);
    }

}
