# (6) currency-exchange
`55_currency_architecture.PNG`

# 6.1 currency-exchange-service

- create new project `currency-exchange-service`
- configure name and port in `application.properties` 
```properties
spring.application.name=currency-exchange-service
server.port=8000
```

- CurrencyExchangeController.java
```java
@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;

    /** Hard coded service */
    @GetMapping("/currency-exchange1/from/{from}/to/{to}")
    public ExchangeValue retriveExchangeValue1(@PathVariable String from, @PathVariable String to){
        return new ExchangeValue(100L, from, to, BigDecimal.valueOf(65));
    }

    /** Add currently running port */
    @GetMapping("/currency-exchange2/from/{from}/to/{to}")
    public ExchangeValue retriveExchangeValue2(@PathVariable String from, @PathVariable String to){
        ExchangeValue exchangeValue = new ExchangeValue(100L, from, to, BigDecimal.valueOf(65));
        exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")))  ;
        return exchangeValue;
    }

}
```

- ExchangeValue.java
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeValue {
    private Long id;
    private String from;
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
```

output: `localhost:8000/currency-exchange1/from/USD/to/Rs` -> `56_currency-exchange_hardcorded.PNG`

# 6.2 configure more ports

- in `currency-exchange-service` -> edit run configuration -> and create duplicate profile and rename it.
- `58_edit_run_configuration.PNG` -> add `-Dserver.port=8001`
- run both profiles at a same time.

output: 
`localhost:8001/currency-exchange2/from/USD/to/Rs`, `localhost:8000/currency-exchange2/from/USD/to/Rs` ->
 `57_configure_different_port.PNG`