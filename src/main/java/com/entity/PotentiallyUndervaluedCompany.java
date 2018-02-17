package com.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class PotentiallyUndervaluedCompany {
    @Id
    @GeneratedValue
    private int id;
    private String ticker;

    public PotentiallyUndervaluedCompany(String ticker) {
        this.ticker = ticker;
    }
}
