package com.hermes.monitoring.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "success", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"hour", "date", "statusCode", "url", "method", "groupName"})
})
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Success {
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
