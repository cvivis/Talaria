package com.hermes.monitoring.repository;

import com.hermes.monitoring.dto.api.ApiClientFailHourlyCountDto;
import com.hermes.monitoring.entity.ClientFail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientFailRepository extends JpaRepository<ClientFail,Long> {
    @Query(value = "select new com.hermes.monitoring.dto.api.ApiClientFailHourlyCountDto(c.date,c.hour,c.hourlyCount) " +
            "from ClientFail as c  where c.url = :url and c.method = :method " +
            "group by c.date,c.url,c.hour,c.method,c.hourlyCount order by c.date desc, c.hour desc")
    List<ApiClientFailHourlyCountDto> findHourAndCountByUrlAndMethod(@Param("url")String url, @Param("method")String method);

}
