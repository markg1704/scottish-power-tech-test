package com.markg1704.scottishpowertest.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Data
public class MeterReading {

    @Id
    @GeneratedValue
    private long id;

    private MeterType meterType;
    private long meterId;
    private long reading;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable=false)
    private Account account;

}
