package com.hermes.monitoring.lsm.service.dashboard;

import com.hermes.monitoring.lsm.job.dashboard.AverageTimeCheckConfig;
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

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AverageTimeCheckService {
    private final JobLauncher jobLauncher;

    private final AverageTimeCheckConfig averageTimeCheckConfig;

    @Scheduled(cron = "0/5 * * * * *") // cron 표기법
    public void checkAverageTime() {
        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter("AverageTimeCheckConfig_"+System.currentTimeMillis())); // 시스템의 현재 시간을 넣음으로써 실행 시점에 충돌을 피함
        JobParameters jobParameters = new JobParameters(confMap);
        try {
            jobLauncher.run(averageTimeCheckConfig.checkAverageTime(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {
            log.error(e.getMessage());
        }
    }
}
