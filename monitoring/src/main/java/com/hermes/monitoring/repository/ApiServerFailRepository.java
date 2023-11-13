package com.hermes.monitoring.repository;

import com.hermes.monitoring.dto.ApiDailyServerFailRequestCountDto;
import com.hermes.monitoring.entity.ServerFail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiServerFailRepository extends JpaRepository<ServerFail, Integer> {
    @Query(
            "select new com.hermes.monitoring.dto.ApiDailyServerFailRequestCountDto(sf.date, sf.hour, sum(sf.hourlyCount)) " +
                    "from ServerFail sf where sf.url = :apiUrl and sf.method = :method " +
                    "group by sf.date, sf.hour, sf.url, sf.method " +
                    "order by sf.date desc"
    )
    List<ApiDailyServerFailRequestCountDto> findServerFail(@Param("apiUrl") String apiUrl, @Param("method") String method);
}
