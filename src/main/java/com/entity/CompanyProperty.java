package com.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
public class CompanyProperty {
    @Id
    @GeneratedValue
    private int id;

    private String ticker;

    @Column(name = "property_type")
    private Type propertyType;

    @ElementCollection
    private Map<Integer, BigDecimal> yearToValue = new HashMap<>();

    public void add(Integer year, BigDecimal value) {
        yearToValue.put(year, value);
    }

    public enum Type {
        NUMBER_OF_SHARES
    }
}
