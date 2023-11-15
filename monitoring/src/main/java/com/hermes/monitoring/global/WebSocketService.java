package com.hermes.monitoring.global;


import com.hermes.monitoring.lsm.dto.dashboard.CpuMemoryUsageDto;
import com.hermes.monitoring.lsm.dto.dashboard.MonitorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    public void sendMessageToClient(String destination, Object message) {
        // log.info("message : {}" , message);
        simpMessagingTemplate.convertAndSend(destination, message);
    }

    public void sendMonitorDtoToClient(String destination, MonitorDto monitorDto) {
        simpMessagingTemplate.convertAndSend(destination, monitorDto);
    }

    // 평균 응답 시간을 클라이언트에게 보내는 메소드
    public void sendAverageResponseTimeToClient(String destination, double time) {
        // log.info("averageResponseTime : {}" , time);
        simpMessagingTemplate.convertAndSend(destination, time);
    }


    public void sendCpuMemoryUsageToClient(String destination, CpuMemoryUsageDto cpuMemoryUsageDto) {
        //log.info("CpuMemoryUsage : {}", cpuMemoryUsageDto);
        simpMessagingTemplate.convertAndSend(destination,cpuMemoryUsageDto);
    }
}
