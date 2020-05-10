package com.pipi.currencyexchangeservice.controller;

import com.pipi.currencyexchangeservice.bean.ExchangeValue;
import com.pipi.currencyexchangeservice.repo.ExchangeValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;

    @Autowired
    private ExchangeValueRepository exchangeValueRepository;

    /**
     * Hard coded service
     */
    @GetMapping("/currency-exchange1/from/{from}/to/{to}")
    public ExchangeValue retriveExchangeValue1(@PathVariable String from, @PathVariable String to) {
        return new ExchangeValue(100L, from, to, BigDecimal.valueOf(65));
    }

    /**
     * Add currently running port
     */
    @GetMapping("/currency-exchange2/from/{from}/to/{to}")
    public ExchangeValue retriveExchangeValue2(@PathVariable String from, @PathVariable String to) {
        ExchangeValue exchangeValue = new ExchangeValue(100L, from, to, BigDecimal.valueOf(65));
        exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
        return exchangeValue;
    }

    /**
     * Connect to db
     */
    @GetMapping("/currency-exchange3/from/{from}/to/{to}")
    public ExchangeValue retriveExchangeValue3(@PathVariable String from, @PathVariable String to) {

        ExchangeValue exchangeValue = exchangeValueRepository.findByFromAndTo(from, to);
        exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
        return exchangeValue;
    }

}
