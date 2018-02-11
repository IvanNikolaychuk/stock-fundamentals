package com.entity;

import com.jobs.analyzer.trend.TrendAnalyzer;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class AnalyzeSummary {
    @Id
    @GeneratedValue
    private int id;

    private String ticker;

    @Enumerated(EnumType.STRING)
    private PropertyType property;
    @Enumerated(EnumType.STRING)
    private Trend trend;

    public AnalyzeSummary() {}

    public AnalyzeSummary(String ticker, PropertyType property, Trend trend) {
        this.ticker = ticker;
        this.property = property;
        this.trend = trend;
    }
}
