package com.markg1704.scottishpowertest.service;

import com.markg1704.scottishpowertest.datamodel.AccountDetailsDTO;

import java.util.Optional;

public interface AccountDetailsService {

    Optional<AccountDetailsDTO> getAccountDetails(long accountNumber);

}
