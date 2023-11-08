package com.hermes.monitoring.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientFail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ClientFailId;

    @NotNull
    private String date;
    @NotNull
    private Integer hour;
    @NotNull
    private Integer hourlyCount;
    @NotNull
    private String url;
    @NotNull
    private Integer statusCode;

    @NotNull
    private String method;

}
