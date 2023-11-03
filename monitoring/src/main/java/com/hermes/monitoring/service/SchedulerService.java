package com.hermes.monitoring.service;

import com.hermes.monitoring.job.HttpStatusConfig;
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
public class SchedulerService {

    private final JobLauncher jobLauncher;

    private final HttpStatusConfig httpStatusConfig;

//    @Scheduled(cron = "0/5 * * * * *") // cron 표기법
    public void runJob() {

        // job parameter 설정
        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter("HttpStatusConfig_"+System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(confMap);
        log.info("HttpStatusConfig_스케줄링 중");
        try {
            jobLauncher.run(httpStatusConfig.HttpStatusJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {

            log.error(e.getMessage());
        }
    }

}
