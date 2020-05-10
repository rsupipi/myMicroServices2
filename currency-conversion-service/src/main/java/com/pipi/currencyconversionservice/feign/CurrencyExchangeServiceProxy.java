package com.pipi.currencyconversionservice.feign;

import com.pipi.currencyconversionservice.bean.CurrencyConversionBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Need to create faign proxy to be able to talk to external microservices
 */
//@FeignClient(name = "currency-exchange-service", url = "localhost:8000")
public interface CurrencyExchangeServiceProxy {

    @GetMapping("/currency-exchange3/from/{from}/to/{to}")
    /** now this is not needed */
//    public CurrencyConversionBean retriveExchangeValue3(@PathVariable("from") String from, @PathVariable("to") String to);
    public CurrencyConversionBean retriveExchangeValue3(@PathVariable String from, @PathVariable String to);
}
