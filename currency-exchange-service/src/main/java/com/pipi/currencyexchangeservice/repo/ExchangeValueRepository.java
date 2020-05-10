package com.pipi.currencyexchangeservice.repo;

import com.pipi.currencyexchangeservice.bean.ExchangeValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long> {

    ExchangeValue findByFromAndTo(String from, String to);
}
