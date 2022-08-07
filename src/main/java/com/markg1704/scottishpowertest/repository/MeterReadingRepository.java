package com.markg1704.scottishpowertest.repository;

import com.markg1704.scottishpowertest.datamodel.Account;
import com.markg1704.scottishpowertest.datamodel.MeterReading;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeterReadingRepository extends CrudRepository<MeterReading, Long> {

    Optional<Iterable<MeterReading>> findByAccount(Account account);

}
