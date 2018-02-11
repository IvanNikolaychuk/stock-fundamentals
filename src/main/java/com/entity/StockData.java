package com.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class StockData {
    @Id
    @GeneratedValue
    private int id;

    private String ticker;
    private Date date;
    private double close;
}
