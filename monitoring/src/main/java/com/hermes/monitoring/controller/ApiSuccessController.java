package com.hermes.monitoring.controller;

import com.hermes.monitoring.dto.ApiDailySuccessRequestCountDto;
import com.hermes.monitoring.service.api.ApiSuccessService;
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
public class ApiSuccessController {
    private final ApiSuccessService apiSuccessService;
    @GetMapping("/api-detail/success")
    public ResponseEntity<List<ApiDailySuccessRequestCountDto>> apiSuccess(@RequestParam("apiurl") String apiUrl,
                                                     @RequestParam("method") String method,
                                                     HttpServletRequest req) {
        // 2xx 요청에 대한 통계 정보를 가져온다.
        List<ApiDailySuccessRequestCountDto> successTime = apiSuccessService.getSuccess(apiUrl,method);
        return new ResponseEntity<>(successTime,HttpStatus.OK);
    }

}
