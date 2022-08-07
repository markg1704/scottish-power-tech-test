package com.markg1704.scottishpowertest.service;

import com.markg1704.scottishpowertest.datamodel.Account;
import com.markg1704.scottishpowertest.datamodel.AccountDetailsDTO;
import com.markg1704.scottishpowertest.datamodel.MeterReading;
import com.markg1704.scottishpowertest.datamodel.MeterReadingDTO;
import com.markg1704.scottishpowertest.datamodel.MeterType;
import com.markg1704.scottishpowertest.repository.AccountRepository;
import com.markg1704.scottishpowertest.repository.MeterReadingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class AccountDetailsServiceImpl implements AccountDetailsService {

    private AccountRepository accountRepository;
    private MeterReadingRepository meterReadingRepository;

    @Override
    public Optional<AccountDetailsDTO> getAccountDetails(long accountNumber) {

        log.info("Retrieving account number {}", accountNumber);

        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);

        if (!optionalAccount.isPresent()) {
            log.error("Could not retrieve account number {}, account not found.", accountNumber);
            return Optional.empty();
        }

        log.info("Retrieving meter readings for account number {}", accountNumber);

        Optional<Iterable<MeterReading>> meterReadings = meterReadingRepository.findByAccount(optionalAccount.get());

        return Optional.of(buildAccountDetailsDTO(optionalAccount, meterReadings));
    }

    private AccountDetailsDTO buildAccountDetailsDTO(Optional<Account> account, Optional<Iterable<MeterReading>> meterReadings) {

        log.info("Building AccountDetailsDTO for account number {}", account.get().getAccountNumber());

        long accountNumber = account.get().getAccountNumber();

        List<MeterReadingDTO> gasReadings = new ArrayList<>();
        List<MeterReadingDTO> elecReadings = new ArrayList<>();

        if (meterReadings.isPresent()) {
            Iterable<MeterReading> readings = meterReadings.get();

            readings.forEach((reading) -> {
                if (reading.getMeterType().equals(MeterType.GAS)) {
                    gasReadings.add(buildMeterReadingDTO(reading));
                } else {
                    elecReadings.add(buildMeterReadingDTO(reading));
                }
            });

        }

        return new AccountDetailsDTO(accountNumber, gasReadings, elecReadings);
    }

    private MeterReadingDTO buildMeterReadingDTO(MeterReading meterReading) {

        long id = meterReading.getId();
        long meterId = meterReading.getMeterId();
        long reading = meterReading.getReading();
        LocalDate date = meterReading.getDate();

        return new MeterReadingDTO(id, meterId, reading, date);
    }

}
