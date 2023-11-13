package com.hermes.monitoring.service;

import com.hermes.monitoring.job.FailTimeCheckConfig;
import com.hermes.monitoring.job.SuccessTimeCheckConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FailTimeCheckService {

    private final JobLauncher jobLauncher;
    private final FailTimeCheckConfig failTimeCheckConfig;

    private final CreateLogFile createLogFile;

    @Value("${fail.baseLog.url}")
    String baseUrl;
//    @Scheduled(cron = "0/5 * * * * *")
    public void checkSuccessTime() throws IOException {
        createLogFile.createLogFile(baseUrl+".txt", baseUrl);
        // job parameter 설정
        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter("successTimeCheck_"+System.currentTimeMillis())); // 시스템의 현재 시간을 넣음으로써 실행 시점에 충돌을 피함
        JobParameters jobParameters = new JobParameters(confMap);
        log.info("성공 응답 시간 확인");
        try {
            jobLauncher.run(failTimeCheckConfig.failTimeCheckJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {

            log.error(e.getMessage());
        }
    }

}
