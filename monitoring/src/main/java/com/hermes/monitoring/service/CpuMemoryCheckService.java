package com.hermes.monitoring.service;

import com.hermes.monitoring.job.CpuMemoryCheckConfig;
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
public class CpuMemoryCheckService {

    private final JobLauncher jobLauncher;
    private final CpuMemoryCheckConfig cpuMemoryCheckConfig;

    @Scheduled(cron = "0/1 * * * * *")
    public void checkCpuMemory() {
        // job parameter 설정
        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter("cpuMemoryCheck"+System.currentTimeMillis())); // 시스템의 현재 시간을 넣음으로써 실행 시점에 충돌을 피함
        JobParameters jobParameters = new JobParameters(confMap);
        log.info("CPU MEMORY 시간 확인 스케줄 시작");
        try {
            jobLauncher.run(cpuMemoryCheckConfig.cpuMemoryCheckJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {

            log.error(e.getMessage());
        }
    }
}
