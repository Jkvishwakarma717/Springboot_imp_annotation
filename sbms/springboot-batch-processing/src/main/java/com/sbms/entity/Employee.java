package com.sbms.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BQ_Result_05122022")
public class Employee  {
    @Id
    @GeneratedValue
    private int id;
    private String date;
    private int appAccountId;
    private int appId;
    private String appVersion;
    private String appChannel;
    private int campaignChannelId;
    private String model;
    private String brand;
    private String network;
    private String mcc;
    private String mnc;
    private String country;
    private String city;
    private String state;
    private String language;
    private int campaignId;
    private int userIsFraud;
    private String publisher;


}
