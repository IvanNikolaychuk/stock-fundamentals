package com.entity;

import javax.persistence.*;

@Entity
public class AnalyzeSummary {
    @Id
    @GeneratedValue
    private int id;

    private Result result;
    private String ticker;
    @Enumerated(EnumType.STRING)
    private CompanyProperty property;

    public enum Result {
        VERY_POSITIVE
    }
}
