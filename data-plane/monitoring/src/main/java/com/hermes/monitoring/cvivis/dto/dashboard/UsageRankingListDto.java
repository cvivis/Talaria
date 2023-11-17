package com.hermes.monitoring.cvivis.dto.dashboard;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UsageRankingListDto {
    private List<UsageRankingDto> data;
}
