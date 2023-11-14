package com.hermes.monitoring.repository;

import com.hermes.monitoring.entity.ServerFail;
import com.hermes.monitoring.lsm.dto.api.ApiHourlyCountDto;
import com.hermes.monitoring.lsm.dto.api.ApiRankingDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ApiServerFailRepository extends JpaRepository<ServerFail, Integer> {

    @Query(value = "select new com.hermes.monitoring.lsm.dto.api.ApiHourlyCountDto(sf.date,sf.hour,sum(sf.hourlyCount)) " +
            "from ServerFail as sf  where sf.groupName = :groupName " +
            "group by sf.date,sf.hour,sf.groupName order by sf.date asc, sf.hour desc")
    List<ApiHourlyCountDto> findDateAndCountByGroupName(@Param("groupName") String groupName);

    @Query(value = "SELECT today.status_code as statusCode, today.count as count, round(cast(sum(hourly_count) as numeric) / (to_date(max(today),'YYYY-MM-DD') - cast(to_date(min(date),'YYYY-MM-DD') as date)),1) as avgCount" +
            " from server_fail as a JOIN " +
            "(select date as today, status_code, sum(hourly_count) as count "
            + "from server_fail as b "
            + "where b.group_name = ?1 and to_date(date,'YYYY-MM-DD') = current_date "
            + "group by b.status_code, b.date "
            + "order by count desc limit 3) as today " +
            "ON a.status_code = today.status_code and a.date != today.today " +
            "where a.group_name = ?1 " +
            "group by (today.status_code, today.count)" , nativeQuery = true)
    List<ApiRankingDto> findStatusCodeAndCountAndAvgCount(String groupName);
}
