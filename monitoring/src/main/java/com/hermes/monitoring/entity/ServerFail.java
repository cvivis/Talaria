package com.hermes.monitoring.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "server_fail", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"hour", "date", "statusCode", "url", "method"})
})
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerFail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int hour;

    private String date;

    private int hourlyCount;

    private String url;

    private int statusCode;

    private String method;

    private String groupName;

}
