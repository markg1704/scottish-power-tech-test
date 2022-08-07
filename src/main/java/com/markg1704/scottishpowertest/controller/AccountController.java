package com.markg1704.scottishpowertest.controller;

import com.markg1704.scottishpowertest.datamodel.AccountDetailsDTO;
import com.markg1704.scottishpowertest.service.AccountDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class AccountController {

    private AccountDetailsService accountDetailsService;

    @GetMapping("/api/smarts/reads")
    public ResponseEntity<AccountDetailsDTO> getAccount(@RequestParam long accountNumber) {
        log.info("Attempting to retrieve data for account {}", accountNumber);
        return ResponseEntity.of(accountDetailsService.getAccountDetails(accountNumber));
    }

}
