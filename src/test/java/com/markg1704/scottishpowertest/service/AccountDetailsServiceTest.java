package com.markg1704.scottishpowertest.service;

import com.markg1704.scottishpowertest.datamodel.Account;
import com.markg1704.scottishpowertest.datamodel.AccountDetailsDTO;
import com.markg1704.scottishpowertest.datamodel.MeterReading;
import com.markg1704.scottishpowertest.datamodel.MeterReadingDTO;
import com.markg1704.scottishpowertest.datamodel.MeterType;
import com.markg1704.scottishpowertest.repository.AccountRepository;
import com.markg1704.scottishpowertest.repository.MeterReadingRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountDetailsServiceTest {

    private static final long ACCOUNT_ID = 909L;
    private static final long ACCOUNT_NUMBER = 1234567890L;

    private static final long GAS_ID = 434L;
    private static final long GAS_METERID = 37865674L;
    private static final long GAS_READING = 56349012L;

    private static final long ELEC_ID = 276L;
    private static final long ELEC_METERID = 77766655L;
    private static final long ELEC_READING = 90009734L;

    private AccountRepository accountRepository;
    private MeterReadingRepository meterReadingRepository;

    private Account account;
    private MeterReading gasMeterReading;
    private MeterReading elecMeterReading;

    @BeforeAll
    public void init() {

        accountRepository = mock(AccountRepository.class);
        meterReadingRepository = mock(MeterReadingRepository.class);

        account = new Account();
        account.setId(ACCOUNT_ID);
        account.setAccountNumber(ACCOUNT_NUMBER);

        gasMeterReading = new MeterReading();
        gasMeterReading.setId(GAS_ID);
        gasMeterReading.setMeterType(MeterType.GAS);
        gasMeterReading.setMeterId(GAS_METERID);
        gasMeterReading.setReading(GAS_READING);
        gasMeterReading.setDate(LocalDate.now());
        gasMeterReading.setAccount(account);

        elecMeterReading = new MeterReading();
        elecMeterReading.setId(ELEC_ID);
        elecMeterReading.setMeterType(MeterType.ELECTRIC);
        elecMeterReading.setMeterId(ELEC_METERID);
        elecMeterReading.setReading(ELEC_READING);
        elecMeterReading.setDate(LocalDate.now());
        elecMeterReading.setAccount(account);

    }

    @Test
    public void shouldReturnAccountDetailsDTOWhenValidAccountNumberGiven() {

        List<MeterReading> meterReadings = Arrays.asList(gasMeterReading, elecMeterReading);

        AccountDetailsService sut = new AccountDetailsServiceImpl(accountRepository, meterReadingRepository);

        when(accountRepository.findByAccountNumber(ACCOUNT_NUMBER)).thenReturn(Optional.of(account));
        when(meterReadingRepository.findByAccount(account)).thenReturn(Optional.of(meterReadings));

        Optional<AccountDetailsDTO> optional = sut.getAccountDetails(ACCOUNT_NUMBER);

        assertTrue(optional.isPresent());

        AccountDetailsDTO response = optional.get();

        assertEquals(ACCOUNT_NUMBER, response.getAccountId());

        Iterator<MeterReadingDTO> gasIterator = response.getGasReadings().iterator();

        MeterReadingDTO gasReadingDTO = gasIterator.next();

        assertEquals(GAS_ID, gasReadingDTO.getId());
        assertEquals(GAS_METERID, gasReadingDTO.getMeterId());
        assertEquals(GAS_READING, gasReadingDTO.getReading());
        assertEquals(LocalDate.now(), gasReadingDTO.getDate());

        assertFalse(gasIterator.hasNext());

        Iterator<MeterReadingDTO> elecIterator = response.getElecReadings().iterator();

        MeterReadingDTO elecReadingDTO = elecIterator.next();

        assertEquals(ELEC_ID, elecReadingDTO.getId());
        assertEquals(ELEC_METERID, elecReadingDTO.getMeterId());
        assertEquals(ELEC_READING, elecReadingDTO.getReading());
        assertEquals(LocalDate.now(), elecReadingDTO.getDate());

        assertFalse(elecIterator.hasNext());

    }

    @Test
    public void shouldReturnEmptyOptionalWhenInvalidAccountNumber() {

        AccountDetailsService sut = new AccountDetailsServiceImpl(accountRepository, meterReadingRepository);

        when(accountRepository.findByAccountNumber(ACCOUNT_NUMBER)).thenReturn(Optional.empty());
        when(meterReadingRepository.findByAccount(account)).thenReturn(Optional.empty());

        Optional<AccountDetailsDTO> optional = sut.getAccountDetails(ACCOUNT_NUMBER);

        assertFalse(optional.isPresent());

    }

    @Test
    public void shouldReturnResponseWithEmptyReadingsArraysWhenNoReadings() {

        AccountDetailsService sut = new AccountDetailsServiceImpl(accountRepository, meterReadingRepository);

        when(accountRepository.findByAccountNumber(ACCOUNT_NUMBER)).thenReturn(Optional.of(account));
        when(meterReadingRepository.findByAccount(account)).thenReturn(Optional.empty());

        Optional<AccountDetailsDTO> optional = sut.getAccountDetails(ACCOUNT_NUMBER);

        assertTrue(optional.isPresent());

        AccountDetailsDTO response = optional.get();

        assertEquals(ACCOUNT_NUMBER, response.getAccountId());
        assertFalse(response.getGasReadings().iterator().hasNext());
        assertFalse(response.getElecReadings().iterator().hasNext());

    }
}
