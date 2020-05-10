package com.pipi.currencyconversionservice.controller;

import com.pipi.currencyconversionservice.bean.CurrencyConversionBean;
import com.pipi.currencyconversionservice.feign.CurrencyExchangeServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeServiceProxy serviceProxy;

    /**
     * Hard code service
     **/
    @GetMapping("/currency-converter1/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency1(@PathVariable String from, @PathVariable String to,
                                                   @PathVariable BigDecimal quantity) {
        return new CurrencyConversionBean(1L, from, to, BigDecimal.ONE, quantity, quantity, 0);
    }

    /**
     * connect to currency-exchange service without Feing
     **/
    @GetMapping("/currency-converter2/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency2(@PathVariable String from, @PathVariable String to,
                                                   @PathVariable BigDecimal quantity) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange3/from/{from}/to/{to}",    // calling other service
                CurrencyConversionBean.class,
                uriVariables    // pass variable in the URL as a map
        );

        CurrencyConversionBean responseBody = responseEntity.getBody();

        return new CurrencyConversionBean(
                responseBody.getId(),
                from,
                to,
                responseBody.getConversionMultiple(),
                quantity,
                quantity.multiply(responseBody.getConversionMultiple()),
                responseBody.getPort()
        );
    }

    /**
     * using Feign
     **/
    @GetMapping("/currency-converter3/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency3(@PathVariable String from, @PathVariable String to,
                                                   @PathVariable BigDecimal quantity) {

        CurrencyConversionBean responseBody = serviceProxy.retriveExchangeValue3(from, to);

        return new CurrencyConversionBean(
                responseBody.getId(),
                from,
                to,
                responseBody.getConversionMultiple(),
                quantity,
                quantity.multiply(responseBody.getConversionMultiple()),
                responseBody.getPort()
        );
    }

}
