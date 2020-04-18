package com.pipi.filtering;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/** This is not a good approach since we are hardcoding values */
@JsonIgnoreProperties(value = {"name"})
public class SomeBean {
    private String id;
    private String name;

    @JsonIgnore // since this is a password we shouldn't send this with the response.
    private String password;

}
