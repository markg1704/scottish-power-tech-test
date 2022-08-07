package com.markg1704.scottishpowertest.repository;

import com.markg1704.scottishpowertest.datamodel.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByAccountNumber(long accountNumber);

}
