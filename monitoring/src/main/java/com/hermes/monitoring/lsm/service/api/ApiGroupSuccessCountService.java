package com.hermes.monitoring.lsm.service.api;

import com.hermes.monitoring.lsm.job.api.ApiGroupCountConfig;
import java.util.HashMap;
import java.util.Map;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiGroupSuccessCountService {
    private final JobLauncher jobLauncher;
    private final ApiGroupCountConfig apiGroupCountConfig;

    // @Scheduled(cron = "0/10 * * * * *")
    public void runJob() {
        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter("ApiGroupCountConfig_"+System.currentTimeMillis()));
        confMap.put("statusCode", new JobParameter(200L));
        JobParameters jobParameters = new JobParameters(confMap);
        try{
            jobLauncher.run(apiGroupCountConfig.apiGroupCountJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {
            e.printStackTrace();
        }
    }
}
