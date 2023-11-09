package com.hermes.monitoring.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client_fail", uniqueConstraints = {
        @UniqueConstraint(name = "uk_fail_count", columnNames = {"date", "hour","hourly_count","url","method","status_code"}),
})
public class ClientFail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ClientFailId;

    @NotNull
    private String date;
    @NotNull
    private Integer hour;
    @NotNull
    @Column(name = "hourly_count")
    private Integer hourlyCount;
    @NotNull
    private String url;
    @NotNull
    @Column(name = "status_code")
    private Integer statusCode;

    @NotNull
    private String method;

}
