package com.hermes.monitoring.cvivis.service.dashboard;


import com.hermes.monitoring.cvivis.job.dashboard.UsageRankingConfig;
import com.hermes.monitoring.global.CreateLogFile;
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
public class UsageRankingService {

    @Value("${log.access.shared.current}")
    String url;

    @Value("${log.access.base}")
    String baseUrl;
    private final JobLauncher jobLauncher;

    private final UsageRankingConfig usageRankingConfig;
    private final CreateLogFile createLogFile;
    @Scheduled(cron = "0/5 * * * * *") // cron 표기법
    public void runJob() throws IOException {
//        createLogFile.createLogFile(url,baseUrl);
        // job parameter 설정
        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter("UsageRankingConfig_"+System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(confMap);
        // log.info("UsageRankingConfig_스케줄링 중");
        try {
            jobLauncher.run(usageRankingConfig.UsageRankingJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {
//            log.error();
            e.printStackTrace();
        }
    }
}
