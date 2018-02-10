package com.entity;

import com.api.Constants;
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
    @Enumerated(value = EnumType.STRING)
    private PropertyType propertyType;

    private int year;
    private BigDecimal property;

    public CompanyProperty(String ticker, int year, PropertyType propertyType, String value) {
        this.ticker = ticker;
        this.year = year;
        this.propertyType = propertyType;
        this.property = value == null ? null : new BigDecimal(value);
    }

    public int getPropertyAsInt() {
        return property.intValue();
    }
}
