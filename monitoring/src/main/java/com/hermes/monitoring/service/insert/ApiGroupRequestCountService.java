package com.hermes.monitoring.service.insert;

import com.hermes.monitoring.job.apiDetail.ApiGroupRequestCountConfig;
import com.hermes.monitoring.job.apiDetail.ApiRequestCountConfig;
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
public class ApiGroupRequestCountService {
    private final JobLauncher jobLauncher;

    private final ApiGroupRequestCountConfig apiGroupRequestCountConfig;

    //    @Scheduled(cron = "0/10 * * * * *") // cron 표기법
    @Scheduled(cron = "0 0 0/1 * * *")
    public void runJob() throws IOException {
        // job parameter 설정
//        createErrorFile.createErrorFile();
        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter("ApiGroupRequestCountConfig_"+System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(confMap);
        log.info("ApiGroupRequestCountConfig_스케줄링 중");
        try {
            jobLauncher.run(apiGroupRequestCountConfig.apiGroupRequestCountJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {
            e.printStackTrace();
        }
    }
}
