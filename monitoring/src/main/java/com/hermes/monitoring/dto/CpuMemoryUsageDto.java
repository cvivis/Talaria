package com.hermes.monitoring.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CpuMemoryUsageDto {
    private Date date;
    private double cpuUsage;
    private double memoryUsage;

    public CpuMemoryUsageDto(Date date, double cpuUsage, double memoryUsage) {
        this.date = date;
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
    }
}
