package com.hermes.monitoring.service;

import com.hermes.monitoring.job.ErrorCountConfig;
import com.hermes.monitoring.job.UsageRankingConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ErrorCountService {
    private final JobLauncher jobLauncher;
    private final ErrorCountConfig errorCountConfig;
    private final CreateErrorFile createErrorFile;
//    @Scheduled(cron = "0/5 * * * * *") // cron 표기법
    public void runJob() throws IOException {
        // job parameter 설정
        createErrorFile.createErrorFile();
        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter("ErrorCountConfig_"+System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(confMap);
        log.info("ErrorCountConfig_스케줄링 중");
        try {
            jobLauncher.run(errorCountConfig.ErrorCountJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {
            e.printStackTrace();
        }
    }
}
