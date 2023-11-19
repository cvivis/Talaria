package com.hermes.monitoring.lsm.service.dashboard;

import com.hermes.monitoring.lsm.dto.dashboard.CpuMemoryUsageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ListContainersCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.InvocationBuilder.AsyncResultCallback;
import java.io.IOException;

import java.io.*;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class CpuMemoryService {

    @Value("${talaria.nginx-container}")
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

    public static String getContainerIdByContainerName(DockerClient dockerClient, String containerName) {
        ListContainersCmd listContainersCmd = dockerClient.listContainersCmd().withShowAll(true);
        for (Container container : listContainersCmd.exec()) {
            for (String name : container.getNames()) {
                if (name.equals("/" + containerName)) {
                    return container.getId();
                }
            }
        }
        return null; // Container not found
    }

    public Statistics getNextStatistics(DockerClient client, String containerId) {
        AsyncResultCallback<Statistics> callback = new AsyncResultCallback<>();
        client.statsCmd(containerId).exec(callback);
        Statistics stats = new Statistics();
        try {
            stats = callback.awaitResult();
            callback.close();
        } catch (RuntimeException | IOException e) {
            // you may want to throw an exception here
        }
        return stats; // this may be null or invalid if the container has terminated
    }

    // Docker container의 Cpu, Memory 사용량 확인
    public CpuMemoryUsageDto getCpuMemoryUsage() throws IOException, InterruptedException {
        String dockerApiEndpoint = "unix:///var/run/docker.sock"; // or your Docker API endpoint
        DockerClient dockerClient = DockerClientBuilder.getInstance(dockerApiEndpoint).build();
        String containerId = getContainerIdByContainerName(dockerClient, containerName);
        if (containerId != null) {
            System.out.println("Container ID for " + containerName + ": " + containerId);
            Statistics statistics = getNextStatistics(dockerClient, containerId);
            System.out.println(statistics);
            long containerCPUUsage = statistics.getCpuStats().getCpuUsage().getTotalUsage();
            System.out.println("컨테이너의 CPU 사용량: "+containerCPUUsage);
            long totalHostCPU = statistics.getCpuStats().getSystemCpuUsage();
            System.out.println("HOST의 전체 CPU 자원: "+totalHostCPU);
            double cpuUsagePercentage = (double) containerCPUUsage / totalHostCPU * 100;
            System.out.println("Cpu 사용률: "+ cpuUsagePercentage);
            // 1. 현재 메모리 사용량
            long currentMemoryUsage = statistics.getMemoryStats().getUsage();

            // 2. 할당된 최대 메모리
            long maxMemoryLimit = statistics.getMemoryStats().getLimit();

            // 3. 백분율 계산
            double memoryUsagePercentage = (double) currentMemoryUsage / maxMemoryLimit * 100;
            return new CpuMemoryUsageDto(new Date(), cpuUsagePercentage, memoryUsagePercentage);
        } else {
            System.out.println("Container not found with name: " + containerName);
        }
        return processBuilder();
    }

}

