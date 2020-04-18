package com.pipi.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@JsonFilter("SomeBeanFilter")// add filter to the bean.
public class SomeBean2 {
    private String id;
    private String name;
    private String password;

}
