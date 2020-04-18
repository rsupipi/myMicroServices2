//package com.pipi.bean;
//
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//
//import javax.validation.constraints.Past;
//import javax.validation.constraints.Size;
//import java.util.Date;
//
///**
// * This call contain validations and swagger configurations
// */
//@Data   //  lombok
//@AllArgsConstructor //  lombok
//@ApiModel(description = "All details about user")   // swagger api-doc
////@Entity     //  data jpa
//public class User {
//
//    private int id;
//
//    @Size(min = 2 , message = "Name should have at least 2 char") // validate the size for 2 letters
//    @ApiModelProperty(notes = "Name should have 2 characters")  // swagger api-doc
//    private String name;
//
//    @Past // validate this should be a past date
//    @ApiModelProperty(notes = "Birth date should be in past date")  // swagger api-doc
//    private Date birthDate;
//}
