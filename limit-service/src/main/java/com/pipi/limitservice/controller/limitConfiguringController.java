package com.pipi.limitservice.controller;

import com.pipi.limitservice.bean.LimistConfiguration;
import com.pipi.limitservice.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class limitConfiguringController {

    @Autowired
    private Configuration configuration;

    @GetMapping("/limits0")
    public String retriveLimitsFromConfigurations0(){
        return "hi pipi";
    }

    @GetMapping("/limits1")
    public LimistConfiguration retriveLimitsFromConfigurations1(){
        return new LimistConfiguration(100,1);
    }

    @GetMapping("/limits2")
    public LimistConfiguration retriveLimitsFromConfigurations2(){
        return new LimistConfiguration(configuration.getMaximum(), configuration.getMinimum());
    }
}
