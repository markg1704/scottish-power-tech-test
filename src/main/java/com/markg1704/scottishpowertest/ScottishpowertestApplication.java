package com.markg1704.scottishpowertest;

import com.markg1704.scottishpowertest.datamodel.Account;
import com.markg1704.scottishpowertest.datamodel.MeterReading;
import com.markg1704.scottishpowertest.datamodel.MeterType;
import com.markg1704.scottishpowertest.repository.AccountRepository;
import com.markg1704.scottishpowertest.repository.MeterReadingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@SpringBootApplication
public class ScottishpowertestApplication {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MeterReadingRepository meterReadingRepository;

	public static void main(String[] args) {
		SpringApplication.run(ScottishpowertestApplication.class, args);
	}

	@PostConstruct
	private void initDb() {
		log.info("In method initDb().  Creating dummy data.");

		Account account = new Account();

		account.setAccountNumber(1234567890);

		accountRepository.save(account);

		MeterReading meterReading = new MeterReading();

		meterReading.setMeterType(MeterType.GAS);
		meterReading.setMeterId(987654321);
		meterReading.setReading(444444);
		meterReading.setDate(LocalDate.now());

		MeterReading elecMeterReading = new MeterReading();

		elecMeterReading.setMeterType(MeterType.ELECTRIC);
		elecMeterReading.setMeterId(1020304050);
		elecMeterReading.setReading(923412);
		elecMeterReading.setDate(LocalDate.now());

		Optional<Account> optional = accountRepository.findByAccountNumber(1234567890);

		if (optional.isPresent()) {
			meterReading.setAccount(optional.get());
			elecMeterReading.setAccount(optional.get());
			meterReadingRepository.save(meterReading);
			meterReadingRepository.save(elecMeterReading);
		}

		log.info("Dummy data created.");

	}

}
