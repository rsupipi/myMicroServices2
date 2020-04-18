package com.pipi.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringContorller {

    // === static filtering ======================
    @GetMapping("/filtering")
    public SomeBean retriveSomeBean1() {
        return new SomeBean("C001", "Sama", "sama@123");
    }

    @GetMapping("/filtering-list")
    public List<SomeBean> retriveSomeBeanList1() {
        return Arrays.asList(
                new SomeBean("C001", "Sama", "sama@123"),
                new SomeBean("C002", "Mala", "mala@123"));
    }

    /**
     * Filter the response according to the method
     */

    // send only id , name
    @GetMapping("/filtering2")
    public MappingJacksonValue retriveSomeBean2() {
        SomeBean2 someBean2 = new SomeBean2("C001", "Sama", "sama@123");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);

        MappingJacksonValue mappingValue = new MappingJacksonValue(someBean2);
        mappingValue.setFilters(filterProvider);

        return mappingValue;
    }

    // send only name, password
    @GetMapping("/filtering-list2")
    public MappingJacksonValue retriveSomeBeanList2() {

        List<SomeBean2> someBeanList = Arrays.asList(
                new SomeBean2("C001", "Sama", "sama@123"),
                new SomeBean2("C002", "Mala", "mala@123"));

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "password");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);

        MappingJacksonValue mappingValue = new MappingJacksonValue(someBeanList);
        mappingValue.setFilters(filterProvider);

        return mappingValue;
    }

}
