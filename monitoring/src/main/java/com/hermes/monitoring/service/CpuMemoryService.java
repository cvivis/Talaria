package com.hermes.monitoring.service;

import com.hermes.monitoring.dto.CpuMemoryUsageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpuMemoryService {

    @Value("${nginx.container.name}")
    String containerName;

    public CpuMemoryUsageDto processBuilder() throws IOException, InterruptedException {
        String osName = System.getProperty("os.name"); // os 환경 확인
        ProcessBuilder builder = new ProcessBuilder();
        if(osName.toLowerCase().startsWith("window")){
            builder.command("cmd.exe","/c","docker","container","stats", "--no-stream", "--format", "{{json .}}", containerName);
        } else if(osName.toLowerCase().startsWith("mac")){
            builder.command("docker","container","stats", "--no-stream", "--format", "{{json .}}", containerName);
        } else {
            builder.command("bash","-c","docker","container","stats", "--no-stream", "--format", "{{json .}}", containerName);
        }
        Process process = builder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        StringBuilder output = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            output.append(line);
        }
        int exitCode = process.waitFor(); // 프로세스의 종료를 기다림
        process.destroy();
        String statsJson = output.toString();
        // JSON 데이터를 파싱하고 CPU 사용량 및 메모리 사용량을 추출
        JSONObject json = new JSONObject(statsJson);
        String cpuStats = json.getString("CPUPerc").replace("%","");
        double cpuUsage = Double.parseDouble(cpuStats);
        String memoryStats = json.getString("MemPerc").replace("%","");
        double memoryUsage = Double.parseDouble(memoryStats);
        System.out.println("CPU 사용량: " + cpuUsage);
        System.out.println("메모리 사용량: " + memoryUsage);

        if (exitCode != 0) {
            System.err.println("프로세스가 오류로 종료되었습니다. 종료 코드: " + exitCode);
            System.err.println("오류 출력:\n" + output.toString());
        }
        return new CpuMemoryUsageDto(new Date(), cpuUsage, memoryUsage);
    }

    // Docker container의 Cpu, Memory 사용량 확인
    public CpuMemoryUsageDto getCpuMemoryUsage() throws IOException, InterruptedException {
        return processBuilder();
    }

}
