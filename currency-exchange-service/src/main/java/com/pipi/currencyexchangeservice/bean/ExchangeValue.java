package com.pipi.currencyexchangeservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ExchangeValue {

    @Id
    private Long id;

    @Column(name = "currency_from")
    private String from;    // we can not have from column in SQL

    @Column(name = "currency_to")
    private String to;

    private BigDecimal conversionMultiple;
    private int port;

    public ExchangeValue(Long id, String from, String to, BigDecimal conversionMultiple) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.conversionMultiple = conversionMultiple;
    }
}
