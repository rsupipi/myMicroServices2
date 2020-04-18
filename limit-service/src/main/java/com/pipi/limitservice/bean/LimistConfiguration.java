package com.pipi.limitservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LimistConfiguration {
    int maximum;
    int minimum;
}
