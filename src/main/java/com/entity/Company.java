package com.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Company {
    @Id
    private String ticker;
    private String sector;
    private String industry;
    private String exchange;
    private Date firstAdded;
    private Date lastUpdated;
}
