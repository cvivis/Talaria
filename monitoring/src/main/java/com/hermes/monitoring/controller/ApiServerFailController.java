package com.hermes.monitoring.controller;

import com.hermes.monitoring.dto.ApiDailyServerFailRequestCountDto;
import com.hermes.monitoring.service.api.ApiServerFailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiServerFailController {
    private final ApiServerFailService apiServerFailService;
    @GetMapping("/api-detail/server-fail")
    public ResponseEntity<List<ApiDailyServerFailRequestCountDto>> apiServerFail(@RequestParam("apiurl") String apiUrl,
                                                                         @RequestParam("method") String method,
                                                                         HttpServletRequest req) {
        // 5xx 요청에 대한 통계 정보를 가져온다.
        List<ApiDailyServerFailRequestCountDto> serverFailTime =apiServerFailService.getServerFailTime(apiUrl,method);
        return new ResponseEntity<>(serverFailTime,HttpStatus.OK);
    }

}
