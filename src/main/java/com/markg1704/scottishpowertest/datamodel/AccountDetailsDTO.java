package com.markg1704.scottishpowertest.datamodel;

import lombok.Value;

@Value
public class AccountDetailsDTO {

    private final long accountId;
    private final Iterable<MeterReadingDTO> gasReadings;
    private final Iterable<MeterReadingDTO> elecReadings;

}
