package com.pipi.limitservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("limits-service-properties") // take this from
@Getter
@Setter
public class Configuration {
    private int minimum;
    private int maximum;
}
