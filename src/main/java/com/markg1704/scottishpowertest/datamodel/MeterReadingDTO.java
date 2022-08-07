package com.markg1704.scottishpowertest.datamodel;

import lombok.Value;

import java.time.LocalDate;

@Value
public class MeterReadingDTO {

    private final long id;
    private final long meterId;
    private final long reading;
    private final LocalDate date;

}
