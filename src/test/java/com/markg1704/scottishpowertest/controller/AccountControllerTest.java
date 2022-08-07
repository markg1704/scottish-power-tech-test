package com.markg1704.scottishpowertest.controller;

import com.markg1704.scottishpowertest.datamodel.AccountDetailsDTO;
import com.markg1704.scottishpowertest.datamodel.MeterReadingDTO;
import com.markg1704.scottishpowertest.service.AccountDetailsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountControllerTest {

    private AccountController sut;
    private AccountDetailsService service;
    private MockMvc mockMvc;

    @BeforeAll
    public void init() {
        service = mock(AccountDetailsService.class);
        sut = new AccountController(service);
        this.mockMvc = MockMvcBuilders.standaloneSetup(sut)
                .build();
    }

    @Test
    public void shouldReturnAccountDetailsWhenValidAccountNumberRequested() throws Exception {

        long accountNumber = 1234567;

        MeterReadingDTO elecMeterDTO = new MeterReadingDTO(1, 123, 456, LocalDate.now());
        MeterReadingDTO gasMeterDTO = new MeterReadingDTO(2, 321, 654, LocalDate.now());

        List<MeterReadingDTO> gasMeters = Arrays.asList(gasMeterDTO);
        List<MeterReadingDTO> elecMeters = Arrays.asList(elecMeterDTO);

        AccountDetailsDTO accountDetailsDTO = new AccountDetailsDTO(accountNumber, gasMeters, elecMeters);

        String url = "/api/smarts/reads?accountNumber=" + accountNumber;

        doReturn(Optional.of(accountDetailsDTO)).when(service).getAccountDetails(accountNumber);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)).andReturn();

        int status = result.getResponse().getStatus();
        String responseBody = result.getResponse().getContentAsString();

        assertTrue(status == HttpStatus.OK.value());
        assertTrue(responseBody.contains("\"accountId\":" + accountNumber));

    }

    @Test
    public void shouldReturn404WhenAccountNumberNotFound() throws Exception {

        long accountNumber = 1234567;

        String url = "/api/smarts/reads?accountNumber=" + accountNumber;

        doReturn(Optional.empty()).when(service).getAccountDetails(accountNumber);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)).andReturn();

        int status = result.getResponse().getStatus();

        assertTrue(status == HttpStatus.NOT_FOUND.value());

    }


}
